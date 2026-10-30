package fr.neamar.kiss.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.neamar.kiss.KissApplication;

public class TagsSelectPreference extends MultiSelectListPreference {

    private final AtomicBoolean entriesLoaded = new AtomicBoolean(false);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public TagsSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadEntriesAsync();
    }

    public TagsSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadEntriesAsync();
    }

    public TagsSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadEntriesAsync();
    }

    public TagsSelectPreference(@NonNull Context context) {
        super(context);
        loadEntriesAsync();
    }

    @Override
    protected void onClick() {
        if (!entriesLoaded.get()) {
            // Tapped before the background load finished (rare): load synchronously now.
            Context appContext = getContext().getApplicationContext();
            applyEntries(computeEntries(appContext));
            entriesLoaded.set(true);
        }
        super.onClick();
    }

    /**
     * The tags DB query runs off the UI thread so settings inflation never blocks on it.
     */
    private void loadEntriesAsync() {
        final Context appContext = getContext().getApplicationContext();
        new Thread(() -> {
            final String[] tagArray = computeEntries(appContext);
            mainHandler.post(() -> {
                applyEntries(tagArray);
                entriesLoaded.set(true);
            });
        }).start();
    }

    private String[] computeEntries(Context context) {
        // get all possible tags, hiding tags only attached to records that no longer exist
        Set<String> tagsSet = KissApplication.getApplication(context)
                .getDataHandler()
                .getTagsHandler()
                .getValidTagsAsSet();

        // make sure we can toggle off the tags that are in the favs now
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> selectedTags = prefs.getStringSet(getKey(), Collections.emptySet());
        tagsSet.addAll(selectedTags);

        String[] tagArray = tagsSet.toArray(new String[0]);
        Arrays.sort(tagArray);
        return tagArray;
    }

    private void applyEntries(String[] tagArray) {
        setEntries(tagArray);
        setEntryValues(tagArray);
        setEnabled(tagArray.length > 0);
        notifyChanged();
    }
}

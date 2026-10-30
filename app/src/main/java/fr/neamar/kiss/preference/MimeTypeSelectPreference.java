package fr.neamar.kiss.preference;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.MultiSelectListPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.neamar.kiss.KissApplication;
import fr.neamar.kiss.MimeTypeCache;
import fr.neamar.kiss.utils.MimeTypeUtils;

public class MimeTypeSelectPreference extends MultiSelectListPreference {

    private final AtomicBoolean entriesLoaded = new AtomicBoolean(false);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public MimeTypeSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadEntriesAsync();
    }

    public MimeTypeSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadEntriesAsync();
    }

    public MimeTypeSelectPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadEntriesAsync();
    }

    public MimeTypeSelectPreference(@NonNull Context context) {
        super(context);
        loadEntriesAsync();
    }

    @Override
    protected void onClick() {
        if (!entriesLoaded.get()) {
            // Tapped before the background load finished (rare): load synchronously now.
            applyEntries(computeEntries(getContext().getApplicationContext()));
            entriesLoaded.set(true);
        }
        super.onClick();
    }

    /**
     * The contacts-provider + PackageManager queries behind the entries are expensive
     * (hundreds of ms with many contacts). Never run them on the UI thread during
     * settings inflation; fill the entries once loaded.
     */
    private void loadEntriesAsync() {
        final Context appContext = getContext().getApplicationContext();
        new Thread(() -> {
            final EntriesData data = computeEntries(appContext);
            mainHandler.post(() -> {
                applyEntries(data);
                entriesLoaded.set(true);
            });
        }).start();
    }

    private static final class EntriesData {
        final String[] entries;
        final String[] entryValues;

        EntriesData(String[] entries, String[] entryValues) {
            this.entries = entries;
            this.entryValues = entryValues;
        }
    }

    private EntriesData computeEntries(Context context) {
        // get all supported mime types
        Set<String> supportedMimeTypes = MimeTypeUtils.getSupportedMimeTypes(context);

        // get all labels
        MimeTypeCache mimeTypeCache = KissApplication.getMimeTypeCache(context);
        Map<String, String> uniqueLabels = mimeTypeCache.getUniqueLabels(context, supportedMimeTypes);

        // get entries and values for sorted mime types
        List<String> sortedMimeTypes = new ArrayList<>(supportedMimeTypes);
        Collections.sort(sortedMimeTypes);

        String[] mimeTypeEntries = new String[supportedMimeTypes.size()];
        String[] mimeTypeEntryValues = new String[supportedMimeTypes.size()];
        int pos = 0;
        for (String mimeType : sortedMimeTypes) {
            mimeTypeEntries[pos] = uniqueLabels.get(mimeType);
            mimeTypeEntryValues[pos] = mimeType;
            pos++;
        }

        return new EntriesData(mimeTypeEntries, mimeTypeEntryValues);
    }

    private void applyEntries(EntriesData data) {
        setEntries(data.entries);
        setEntryValues(data.entryValues);
        setEnabled(data.entries.length > 0);
        notifyChanged();
    }
}

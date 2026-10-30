KISS
======
An Android launcher not spending time and memory on stuff you'd rather do.

## KissLC — what's different from upstream

This fork ([treximaru/kisslc](https://github.com/treximaru/kisslc)) builds on KISS 3.26.0 with:

- **Favorites grid** — a new *Icons per row* setting (Settings → History & Favorites).
  Set how many favorite icons appear per row; the bar wraps into as many rows
  as needed and shows every favorite without scrolling. `0` keeps the upstream
  single-row behavior. Icon size follows from the per-row count.
- **Drag favorites across rows** — favorites can be reordered by dragging in any
  direction, not just left/right within one row.
- **Faster Settings screen** — preference entries backed by expensive queries
  (contact mime types, tags) now load on a background thread instead of blocking
  the UI while the Settings screen opens.
- Renamed to **KissLC launcher** so it installs side-by-side with the original KISS
  (debug builds use `fr.neamar.kiss.debug`).

---

[Copylefted](https://en.wikipedia.org/wiki/Copyleft) libre software, licensed [GPLv3+](https://github.com/Neamar/KISS/blob/master/LICENSE):

Use, see, [change](CONTRIBUTING.md) and share at will; with all.

From _your_ background, type the first letters of apps, contact names, or settings—and click.  
Results clicked more often are promoted.

_Browsing for apps is and should be secondary_.

[<img src="https://img.shields.io/f-droid/v/fr.neamar.kiss.svg?logo=f-droid&label=F-Droid&style=flat-square"
      alt="F-Droid Release"/>](https://f-droid.org/packages/fr.neamar.kiss)
[<img src="https://img.shields.io/endpoint?color=blue&logo=google-play&style=flat-square&url=https%3A%2F%2Fplay.cuzi.workers.dev%2Fplay%3Fi%3Dfr.neamar.kiss%26l%3DGoogle%2520Play%26m%3D%24version"
      alt="Playstore Release"/>](https://play.google.com/store/apps/details?id=fr.neamar.kiss)
[<img src="https://img.shields.io/github/v/release/Neamar/KISS.svg?logo=github&label=GitHub&style=flat-square"
      alt="GitHub Release"/>](https://github.com/Neamar/KISS/releases)
[<img src="https://hosted.weblate.org/widget/kiss/svg-badge.svg"
      alt="Translation status"/>](https://hosted.weblate.org/engage/kiss/)

Join the [beta program](https://play.google.com/apps/testing/fr.neamar.kiss/) to test the latest version.

Join the [Hosted Weblate](https://hosted.weblate.org/engage/kiss/) translation platform to help translate the app.

Public Telegram chat: https://t.me/joinchat/_eDeAIQJU1FlNjM0

| ![Less interface](https://raw.githubusercontent.com/Neamar/KISS/master/fastlane/metadata/android/en-US/images/phoneScreenshots/1.png) | ![Search anything](https://raw.githubusercontent.com/Neamar/KISS/master/fastlane/metadata/android/en-US/images/phoneScreenshots/2.png) | ![Customize everything](https://raw.githubusercontent.com/Neamar/KISS/master/fastlane/metadata/android/en-US/images/phoneScreenshots/3.png) | ![Settings](https://raw.githubusercontent.com/Neamar/KISS/master/fastlane/metadata/android/en-US/images/phoneScreenshots/4.png) |
|:-------------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------:|

# 📖 Namma Kathey — Karnataka Regional Hero Storybook

> "Regional Hero Storybook" — Learn about Karnataka's brave local heroes through illustrated stories, quizzes, and AI guidance!

---

## Features (v1 — all PRD features implemented)

| PRD Feature | Status | Details |
|---|---|---|
| District filter | ✅ | Filter heroes by Karnataka district |
| Illustrated Story | ✅ | Swipe-based HorizontalPager storybook |
| Hero Quiz | ✅ | 3 questions per hero, earn Heritage Badge |
| Statue Finder | ✅ | Tap to open Google Maps location |
| Language toggle | ✅ | Kannada / English, persisted via DataStore |
| Heritage Badge | ✅ | Saved to Room DB, shown in Badge profile |
| GenAI Guide | ✅ | Gemini 1.5 Flash chat about heroes |
| Search heroes | ✅ | Real-time search across names + districts |

---

## Heroes Included (10 Karnataka Heroes)

| # | Hero | District | Category |
|---|---|---|---|
| 1 | Kittur Chennamma | Belagavi | Freedom Fighter |
| 2 | Sangolli Rayanna | Belagavi | Freedom Fighter |
| 3 | Kuvempu | Shivamogga | Poet & Author |
| 4 | Basavanna | Bidar | Social Reformer |
| 5 | Sir M. Visvesvaraya | Mandya | Engineer & Statesman |
| 6 | Nadaprabhu Kempegowda | Bengaluru Urban | Visionary Ruler |
| 7 | Rani Abbakka | Dakshina Kannada | Freedom Fighter |
| 8 | Akkamahadevi | Shivamogga | Poet & Mystic |
| 9 | Tippu Sultan | Mysuru | Freedom Fighter |
| 10 | Narayana Guru | Dakshina Kannada | Social Reformer |

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navigation | Compose Navigation |
| Database | Room (offline-first, auto-seeded from JSON) |
| DI | Hilt |
| AI | Google Gemini 1.5 Flash |
| Story Pager | ExperimentalFoundationApi HorizontalPager |
| Preferences | DataStore |
| Build | Gradle Version Catalog |

---

## Setup Instructions

### 1. Open in Android Studio
File → Open → Select the `NammaKathey` folder

### 2. Add Gemini API Key
Get a **free** key at: https://aistudio.google.com

Open `local.properties` and set:
```
GEMINI_API_KEY=AIza...your-key-here
```
> The app works fully offline without the key — only the AI Guide tab needs it.

### 3. SDK Path
Android Studio fills this automatically in `local.properties`:
```
sdk.dir=/Users/yourname/Library/Android/sdk
```

### 4. Sync & Run
- Click **Sync Now**
- Run on emulator API 21+ or physical device
- App auto-seeds 10 heroes from bundled `heroes.json`

---

## App Flow

```
Home Screen (District filter + Search)
    └── Tap Hero Card
        └── Story Screen (Swipe through 6 story pages)
            └── "Take Quiz!" button
                └── Quiz Screen (3 questions)
                    └── Score 3/3 → Heritage Badge awarded!
                        └── Badge Profile Screen

Bottom Nav: Home | Badges | AI Guide
Settings: Language toggle (EN/KN) | Dark Mode
```

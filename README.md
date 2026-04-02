# AndroiNotes

A simple, clean Android Notes app built with **Java**, **XML layouts**, and **Gradle CLI**.
No Android Studio required — works entirely from the command line / VS Code.

---

## 📱 Features

- ✅ Add notes with a single tap
- ✅ View all notes in a scrollable list (newest first)
- ✅ Delete a note with long-press → confirm dialog
- ✅ Notes persist after app restart (SharedPreferences)
- ✅ Clean minimal UI with card-based design
- ✅ Works on Android 5.0+ (API 21+)

---

## 🗂️ Project Structure

```
NotesApp/
├── gradlew                          # Unix/Mac build script
├── gradlew.bat                      # Windows build script
├── gradle.properties                # AndroidX + JVM settings
├── build.gradle                     # Root Gradle (AGP plugin)
├── settings.gradle                  # Project name + module
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar       # Gradle bootstrapper
│       └── gradle-wrapper.properties
└── app/
    ├── build.gradle                 # App dependencies & SDK config
    └── src/main/
        ├── AndroidManifest.xml
        ├── java/com/example/notes/
        │   ├── Note.java            # Data model (text + timestamp)
        │   ├── NotesAdapter.java    # RecyclerView adapter
        │   └── MainActivity.java   # Main screen logic
        └── res/
            ├── layout/
            │   ├── activity_main.xml  # Main screen layout
            │   └── item_note.xml      # Note card layout
            ├── values/
            │   ├── strings.xml
            │   ├── colors.xml
            │   └── themes.xml
            ├── drawable/
            │   └── input_bg.xml       # Rounded input shape
            └── mipmap-*/
                └── ic_launcher*.png   # App icons (all densities)
```

---

## ⚙️ Prerequisites

Make sure you have the following installed before building:

| Tool | Version | Download |
|------|---------|----------|
| Java JDK | 11 or higher | https://adoptium.net |
| Android SDK | API 34 | https://developer.android.com/studio#command-tools |
| ADB (Android Debug Bridge) | Any | Included with Android SDK |

### Set environment variables (Windows example):
```powershell
# Add these to your System Environment Variables
ANDROID_HOME = C:\Android
JAVA_HOME    = C:\Program Files\Eclipse Adoptium\jdk-17.x.x
PATH         += %ANDROID_HOME%\platform-tools
PATH         += %ANDROID_HOME%\tools
PATH         += %JAVA_HOME%\bin
```

---

## 🔨 Build Instructions

### Step 1 — Create gradle.properties (REQUIRED)
Create a file named `gradle.properties` in the project root with:
```properties
android.useAndroidX=true
android.enableJetifier=true
org.gradle.jvmargs=-Xmx2048m
```

### Step 2 — Build the APK

**Windows (PowerShell):**
```powershell
.\gradlew.bat assembleDebug
```

**Windows (CMD):**
```cmd
gradlew.bat assembleDebug
```

**Linux / Mac:**
```bash
chmod +x gradlew
./gradlew assembleDebug
```

> ⏳ First build downloads Gradle 8.4 (~130 MB) automatically. Be patient!

### Step 3 — Find the APK
```
app\build\outputs\apk\debug\app-debug.apk
```

---

## 📲 Install on Android Phone

### Enable USB Debugging on your phone:
1. Go to **Settings → About Phone**
2. Tap **Build Number** 7 times to unlock Developer Options
3. Go to **Settings → Developer Options**
4. Enable **USB Debugging** ✅
5. Connect phone via USB and tap **"Allow"** on the popup

### Install via ADB:

**Windows:**
```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

**Linux / Mac:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Verify your phone is detected:
```bash
adb devices
# Should show something like:
# List of devices attached
# R58Nxxxxxxx    device
```

### After install:
- Open your **App Drawer** on the phone
- Look for the **"Notes"** app icon
- Tap to launch 🎉

---

## 🧩 File Descriptions

| File | Description |
|------|-------------|
| `Note.java` | Data model — stores note text and creation timestamp |
| `NotesAdapter.java` | RecyclerView adapter — binds notes to card views, handles long-press delete |
| `MainActivity.java` | Main screen — handles add button, loads/saves notes via SharedPreferences |
| `activity_main.xml` | Main screen layout — title, EditText input, Add button, RecyclerView |
| `item_note.xml` | Single note card layout — blue accent bar + note text |
| `colors.xml` | App color palette (primary blue, backgrounds, text colors) |
| `themes.xml` | App theme extending MaterialComponents |
| `strings.xml` | App name and string resources |
| `input_bg.xml` | Rounded rectangle drawable for the EditText background |
| `app/build.gradle` | App-level Gradle — minSdk 21, targetSdk 34, dependencies |
| `build.gradle` | Root Gradle — Android Gradle Plugin 8.2.2 |
| `settings.gradle` | Project name and module registration |
| `gradle.properties` | Enables AndroidX, Jetifier, sets JVM heap size |
| `gradle-wrapper.properties` | Specifies Gradle 8.4 download URL |

---

## 🐛 Common Errors & Fixes

| Error | Fix |
|-------|-----|
| `android.useAndroidX` not enabled | Create `gradle.properties` with `android.useAndroidX=true` |
| `gradlew.bat` not recognized | Use `.\gradlew.bat` in PowerShell (note the `.\` prefix) |
| `adb: device not found` | Enable USB Debugging, reconnect USB, run `adb devices` |
| `ANDROID_HOME not set` | Set `ANDROID_HOME` environment variable to your SDK path |
| `JAVA_HOME not set` | Set `JAVA_HOME` to your JDK installation folder |
| Build fails first time | Wait — Gradle 8.4 + SDK components download on first run |
| `SDK XML version 4` warning | Safe to ignore — just an informational warning, not an error |

---

## 🔧 Tech Stack

- **Language:** Java (no Kotlin)
- **UI:** XML Layouts
- **Architecture:** Single Activity
- **Storage:** SharedPreferences (JSON serialization)
- **UI Components:** RecyclerView, CardView, Material Button
- **Build Tool:** Gradle 8.4 with Android Gradle Plugin 8.2.2
- **Min SDK:** 21 (Android 5.0 Lollipop)
- **Target SDK:** 34 (Android 14)

---

## 📦 Dependencies

```gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.11.0'
implementation 'androidx.recyclerview:recyclerview:1.3.2'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
```

---

## 🚀 Quick Start (TL;DR)

```powershell
# 1. Create gradle.properties
echo "android.useAndroidX=true`nandroid.enableJetifier=true" > gradle.properties

# 2. Build
.\gradlew.bat assembleDebug

# 3. Install
adb install app\build\outputs\apk\debug\app-debug.apk

# 4. Open "Notes" app on your phone 🎉
```

---

*Built with ❤️ using Java + Android SDK — no Android Studio needed!*

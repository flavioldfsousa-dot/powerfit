# Zentrox Fit - Releases

## 📱 Build Instructions

### Android (APK)

```bash
# From the project root
cd releases
./build-android.sh
```

The APK will be available in `releases/android/`.

**Requirements:**
- Android SDK
- Java 17+
- Gradle 8.2+

### iOS (IPA)

```bash
# From the project root
cd releases
./build-ios.sh
```

**Requirements:**
- Xcode 15.0+
- macOS with Apple Developer account (for device installation)

---

## 📂 Directory Structure

```
releases/
├── android/          # Android APK files
│   └── app-release.apk
├── ios/              # iOS IPA files
│   └── ZentroxFit.ipa
├── build-android.sh  # Android build script
├── build-ios.sh      # iOS build script
└── README.md         # This file
```

---

## 🚀 Installation

### Android
1. Download `app-release.apk`
2. Enable "Install from unknown sources" in your device settings
3. Open the APK file to install

### iOS
**Option 1: TestFlight (Recommended)**
1. Upload the IPA to App Store Connect
2. Invite testers via TestFlight
3. Install through TestFlight app

**Option 2: Direct Install (Development)**
1. Open the project in Xcode
2. Connect your iOS device
3. Select your device as the build target
4. Click "Run" (▶️)

---

## 🌍 Supported Languages

- 🇧🇷 Português (BR) - Default
- 🇺🇸 English (US)
- 🇪🇸 Español
- 🇫🇷 Français
- 🇩🇪 Deutsch
- 🇯🇵 日本語

---

## 🎯 Features

- ✅ Workout tracking by muscle group
- ✅ Exercise customization & reordering
- ✅ Multi-language support (6 languages)
- ✅ BMI calculator
- ✅ Weight tracking
- ✅ Workout history
- ✅ Timer with presets
- ✅ AI Assistant
- ✅ Dark theme iOS-style UI

---

## 📋 Project Structure

```
projeto 001/
├── app/                    # Android native app (Kotlin)
├── ios/                    # iOS native app (Swift)
├── web/                    # Web app (Flask + HTML/CSS/JS)
├── releases/               # Build outputs & scripts
│   ├── android/
│   ├── ios/
│   ├── build-android.sh
│   ├── build-ios.sh
│   └── README.md
└── data/                   # Shared data
```

---

## 🔧 Development

### Web App (Local Testing)
```bash
cd web
python3 app.py
# Open http://localhost:5001
```

### Android
```bash
cd app
./gradlew assembleDebug
```

### iOS
```bash
cd ios
open ZentroxFit.xcodeproj
# Build and run from Xcode
```

---

## 📄 License

PowerFit App - All rights reserved.

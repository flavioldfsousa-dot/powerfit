#!/bin/bash
# Build script for Android APK
# Usage: ./build-android.sh

set -e

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
APP_DIR="$PROJECT_DIR/app"
RELEASE_DIR="$PROJECT_DIR/releases/android"

echo "=== Zentrox Fit - Android Build ==="
echo "Project: $PROJECT_DIR"
echo ""

# Check if Gradle wrapper exists
if [ ! -f "$APP_DIR/gradlew" ]; then
    echo "Creating Gradle wrapper..."
    cd "$APP_DIR"
    gradle wrapper --gradle-version 8.2
fi

# Make gradlew executable
chmod +x "$APP_DIR/gradlew"

echo "Building APK..."
cd "$APP_DIR"
./gradlew assembleRelease --no-daemon

# Copy APK to release directory
APK_PATH="$APP_DIR/build/outputs/apk/release"
if [ -d "$APK_PATH" ]; then
    cp "$APK_PATH"/*.apk "$RELEASE_DIR/" 2>/dev/null || true
    echo ""
    echo "APK files copied to: $RELEASE_DIR"
    ls -la "$RELEASE_DIR"/*.apk 2>/dev/null || echo "No APK found"
else
    # Try debug build
    ./gradlew assembleDebug --no-daemon
    DEBUG_PATH="$APP_DIR/build/outputs/apk/debug"
    if [ -d "$DEBUG_PATH" ]; then
        cp "$DEBUG_PATH"/*.apk "$RELEASE_DIR/" 2>/dev/null || true
        echo ""
        echo "Debug APK files copied to: $RELEASE_DIR"
        ls -la "$RELEASE_DIR"/*.apk 2>/dev/null || echo "No APK found"
    fi
fi

echo ""
echo "=== Build Complete ==="

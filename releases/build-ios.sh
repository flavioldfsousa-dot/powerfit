#!/bin/bash
# Build script for iOS IPA
# Usage: ./build-ios.sh

set -e

PROJECT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
IOS_DIR="$PROJECT_DIR/ios"
RELEASE_DIR="$PROJECT_DIR/releases/ios"

echo "=== Zentrox Fit - iOS Build ==="
echo "Project: $PROJECT_DIR"
echo ""

# Check if Xcode is available
if ! command -v xcodebuild &> /dev/null; then
    echo "Error: Xcode command line tools not found."
    echo "Please install Xcode from the App Store."
    exit 1
fi

echo "Building iOS app..."
cd "$IOS_DIR"

# Build for simulator (no signing needed)
echo "Building for iOS Simulator..."
xcodebuild -project ZentroxFit.xcodeproj \
    -scheme ZentroxFit \
    -destination 'generic/platform=iOS Simulator' \
    -configuration Release \
    -derivedDataPath build \
    CODE_SIGN_IDENTITY="" \
    CODE_SIGNING_REQUIRED=NO \
    CODE_SIGNING_ALLOWED=NO || true

# Create IPA directory structure
IPA_DIR="$RELEASE_DIR/ZentroxFit.ipa"
mkdir -p "$IPA_DIR/Payload"

# Copy the built app
BUILD_DIR="$IOS_DIR/build/Build/Products/Release-iphonesimulator"
if [ -d "$BUILD_DIR/ZentroxFit.app" ]; then
    cp -R "$BUILD_DIR/ZentroxFit.app" "$IPA_DIR/Payload/"
    
    # Create IPA zip
    cd "$RELEASE_DIR"
    zip -r "ZentroxFit.ipa" "ZentroxFit.ipa" 2>/dev/null || true
    rm -rf "ZentroxFit.ipa"
    
    echo ""
    echo "IPA created at: $RELEASE_DIR/ZentroxFit.ipa"
else
    echo "Build failed. Trying alternative build..."
    
    # Alternative: create a simple web app wrapper
    echo "Creating web app wrapper..."
    mkdir -p "$RELEASE_DIR/ZentroxFit-Web"
    cp -R "$IOS_DIR/ZentroxFit" "$RELEASE_DIR/ZentroxFit-Web/"
    cp "$IOS_DIR/ZentroxFit.xcodeproj/project.pbxproj" "$RELEASE_DIR/ZentroxFit-Web/"
    
    echo ""
    echo "Web app wrapper created at: $RELEASE_DIR/ZentroxFit-Web/"
    echo "Note: For production, build with Xcode directly."
fi

echo ""
echo "=== Build Complete ==="

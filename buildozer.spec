# Power Fit - App de Treinos para Smartphone
[app]

title = Power Fit

package.name = powerfit
package.domain = com.powerfit.app

source.dir = .
source.include_exts = py,png,jpg,kv,atlas,json

version = 1.0.0

requirements = python3,kivy==2.3.1,pillow

orientation = portrait

fullscreen = 0

android.permissions = READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE

android.api = 31
android.minapi = 21
android.ndk = 25b
android.sdk = 31
android.accept_sdk_license = True

android.arch = arm64-v8a

icon.filename = %(source.dir)s/icon.png

presplash.filename = %(source.dir)s/presplash.png
presplash.color = 0.07,0.07,0.07,1

log_level = 2

# android.release_artifact = aab

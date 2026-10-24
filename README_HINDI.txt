IDWP Android App - Starter Project
==================================

Is project mein IDWP ki existing live website WebView mein khulti hai:
https://idwp-nxwk.vercel.app/
Isliye website ke login, OTP, profile, referral, package aur payment features ko HTML mein dobara likha nahi gaya.

ZAROORI:
- Yeh Android Studio source project hai, ready-to-upload APK/AAB nahi.
- Abhi Google ke OFFICIAL TEST AdMob App ID aur TEST banner Ad Unit ID lage hue hain.
- Test ads se earning nahi hoti. Apna AdMob account banne ke baad apni App ID aur Banner Ad Unit ID se replace karna zaroori hai.
- Production se pehle Google UMP consent/privacy messaging, Play Console data safety, privacy policy, app icon, screenshots, testing aur policy review complete karein.
- Payment/OTP flows, file upload, login persistence aur website URL ko real device par test karna hoga.
- Website URL badle to app/src/main/java/com/idwp/app/MainActivity.java mein HOME_URL update karein.

Android Studio mein kholna:
1. ZIP extract karein.
2. Android Studio > Open > extracted IDWPAndroid folder.
3. Gradle sync hone dein.
4. Android SDK 35 install karein agar prompt aaye.
5. Test phone par Run karein.
6. Production build se pehle test AdMob IDs ko apni IDs se replace karein.

AdMob test IDs (sirf testing ke liye):
App ID: ca-app-pub-3940256099942544~3347511713
Banner: ca-app-pub-3940256099942544/6300978111

Package name: com.idwp.app


PHONE-ONLY CLOUD BUILD (GitHub Actions)
======================================
1. Phone browser mein GitHub par sign in karein aur New repository banayein, jaise IDWPAndroid.
2. Is ZIP ko phone mein extract karein.
3. Extracted IDWPAndroid folder ke ANDAR ke files/folders repository ke root mein upload karein. Repository ke root par settings.gradle, build.gradle, app/ aur .github/ dikhne chahiye.
4. Repository mein .github/workflows/android.yml file banayein aur ZIP mein diye gaye android.yml ka content paste karein.
5. Commit changes karein. GitHub > Actions mein "Build IDWP Android app" workflow kholein. Zaroorat ho to "Run workflow" dabayein.
6. Build complete hone par workflow run ke neeche Artifacts mein IDWP-Android-builds download karein.
IMPORTANT: Debug APK testing ke liye hai. AAB output unsigned ho sakta hai aur Play Store par upload karne se pehle signed AAB/keystore setup zaroori hai. Apni AdMob IDs, privacy/consent, app testing aur Play Console requirements bhi baaki hain.

# Codes App - Simple Search Engine

A simple Android search engine application with local storage for search history and favorites.

## Features

✅ **Search Functionality** - Search using Google Search  
✅ **Search History** - Keep track of your recent searches  
✅ **Favorites** - Save your favorite search queries  
✅ **Local Storage** - All data is stored locally using SharedPreferences  
✅ **Simple & Clean UI** - User-friendly interface  
✅ **WebView Integration** - Display search results directly in the app  

## How to Build and Generate APK

### Prerequisites
- Android Studio (latest version)
- Java Development Kit (JDK) 8 or higher
- Android SDK (API Level 21+)

### Steps to Generate APK

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sandrinebens/codes-app.git
   cd codes-app
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Select the `codes-app` folder
   - Wait for Gradle to sync

3. **Build the APK**
   - Go to **Build** → **Build Bundle(s)/APK(s)** → **Build APK(s)**
   - Wait for the build to complete
   - A notification will appear when done

4. **Locate the APK**
   - The APK file is located at: `app/build/outputs/apk/debug/app-debug.apk`

5. **Install on Your Phone**
   - Connect your Android phone via USB
   - Enable Developer Mode on your phone
   - Drag and drop the APK file onto your phone, or
   - Use ADB command: `adb install app/build/outputs/apk/debug/app-debug.apk`

### Alternative: Generate Signed APK (for Distribution)

1. Go to **Build** → **Generate Signed Bundle/APK**
2. Select **APK** option
3. Create or select a keystore
4. Fill in the keystore details
5. Click **Finish**
6. The signed APK will be in `app/release/` folder

## Usage

1. **Search**: Enter a query in the search box and click "Search"
2. **Add to Favorites**: Click the ⊕ button to add current query to favorites
3. **View History**: Click the history button to see recent searches
4. **View Favorites**: Click the favorites button to see saved searches
5. **Clear History**: Click the trash icon to clear search history

## App Permissions

- **INTERNET** - Required to fetch search results from Google

## Project Structure

```
codes-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/codesapp/searchengine/
│   │   │   │   └── MainActivity.java
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   └── activity_main.xml
│   │   │   │   ├── drawable/
│   │   │   │   ├── values/
│   │   │   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
└── README.md
```

## Technologies Used

- **Android SDK** - Android development framework
- **Java** - Programming language
- **SharedPreferences** - Local storage
- **WebView** - Display web content (search results)
- **Gradle** - Build system

## System Requirements

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)
- **Compile SDK**: API 34

## License

This project is open-source and available for personal use.

## Support

For issues or questions, please open an issue on GitHub.

---

**Happy Searching! 🔍**
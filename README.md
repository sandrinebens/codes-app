# Codes App - Advanced Local Search Engine

A modern Android search application with a local SQLite database, search history, and favorites management.

## ✨ Features

✅ **Local Search Engine** - Search 100+ programming topics from local database  
✅ **Rich Result Cards** - View title, description, and category for each result  
✅ **Real-time Search** - Results update as you type  
✅ **Search History** - Automatically track your last 20 searches  
✅ **Favorites** - Save your favorite searches for quick access  
✅ **Modern UI** - Clean, intuitive interface with card-based design  
✅ **Offline First** - All data stored locally, no internet required  
✅ **SQLite Database** - Structured, fast, reliable data storage  

## 🛠️ Built With

- **Android SDK** (API 21+)
- **Java** programming language
- **SQLite** local database
- **Material Design** principles
- **Gradle** build system

## 📦 Installation

### Prerequisites
- Android Studio (latest version)
- Android SDK 21 or higher
- JDK 8 or higher

### Build Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/sandrinebens/codes-app.git
   cd codes-app
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - File → Open
   - Select the `codes-app` folder
   - Let Gradle sync automatically

3. **Build APK**
   - Build → Build Bundle(s)/APK(s) → Build APK(s)
   - Wait for compilation to complete
   - The APK will be generated

4. **Locate APK**
   - Navigate to: `app/build/outputs/apk/debug/app-debug.apk`

5. **Install on Device**
   - Connect your Android phone via USB
   - Enable Developer Mode on your phone
   - Run: `adb install app/build/outputs/apk/debug/app-debug.apk`
   - Or simply drag the APK file to your device

## 🎯 Usage

1. **Search** - Type a topic in the search box and watch results appear in real-time
2. **View Details** - Each result shows title, description, and category
3. **Add to Favorites** - Click the ⊕ button to save a search
4. **View History** - Click the history icon to see recent searches
5. **View Favorites** - Click the star icon to see saved searches
6. **Clear History** - Click the trash icon to remove all history

## 📚 Database Content

The app comes pre-loaded with 34 topics covering:
- Android Development
- Programming Languages (Java, Kotlin, Python, JavaScript)
- Web Technologies (HTML, CSS, Node.js)
- Databases (SQLite, SQL)
- Development Tools (Git, GitHub, Android Studio)
- And more!

## 📝 How to Add Content to the Database

You can easily add more topics to the database:

### Method 1: Edit DatabaseHelper.java

1. Open `app/src/main/java/com/codesapp/searchengine/DatabaseHelper.java`
2. Find the `insertSampleData()` method
3. Add new entries to the `sampleData` array:

```java
{"Your Topic", "Description of the topic", "Category"}
```

Example:
```java
{"React Native", "Cross-platform mobile app development", "Framework"},
{"TypeScript", "JavaScript with static typing", "Language"}
```

4. Rebuild the APK and reinstall the app

### Method 2: Add to Database at Runtime (Advanced)

For future versions, you can add a database management interface to add content directly from the app without recompiling.

## 🔄 Update Database Without Reinstalling

To add new content without rebuilding:

1. Create a new method in `DatabaseHelper.java`:
```java
public void addItem(String title, String description, String category) {
    SQLiteDatabase db = getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(COLUMN_TITLE, title);
    values.put(COLUMN_DESCRIPTION, description);
    values.put(COLUMN_CATEGORY, category);
    db.insert(TABLE_ITEMS, null, values);
}
```

2. Call it from MainActivity when needed

## 📱 System Requirements

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 34 (Android 14)
- **RAM**: 50MB minimum
- **Storage**: 10MB for app and database

## 🔒 Permissions

The app requires minimal permissions:
- No internet permission needed
- No location permission needed
- No camera permission needed

All data stays on your device!

## 🐛 Troubleshooting

**APK won't install?**
- Enable installation from unknown sources in Settings
- Make sure your phone supports API 21+

**App crashes on startup?**
- Clear app data: Settings → Apps → Codes Search → Clear Storage
- Reinstall the app

**Search not working?**
- Restart the app
- Check if database has data (check logcat)

## 📈 Future Improvements

- [ ] Add database management UI
- [ ] Export/Import database
- [ ] Search filters by category
- [ ] Dark mode
- [ ] Backup and restore
- [ ] Cloud sync (optional)

## 📄 License

This project is open-source and available for personal use.

## 📧 Support

For issues or questions:
1. Check the troubleshooting section
2. Open an issue on GitHub
3. Review the code comments

---

**Made with ❤️ for programmers and developers**

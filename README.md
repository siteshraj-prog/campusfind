# ✨ CampusFind

<div align="center">

### 🔍 Lost and Found, Campus Connected

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge)

**Find what's lost. Report what's found. Keep campus connected.**

⭐ ⭐ ⭐ ⭐ ⭐

</div>

---

## 📋 About CampusFind

CampusFind is a **terminal-based lost-and-found management system** designed for campus communities. It helps students and staff efficiently report lost items, record found items, search the database, and discover possible matches between lost and found reports.

### The Problem It Solves

Campus life often means lost items—keys, ID cards, water bottles, and more. Traditional bulletin boards are slow and hard to search. CampusFind brings lost-and-found management into the digital age with a simple, fast, and reliable terminal application that stores all data locally using SQLite, ensuring privacy and instant access.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 📝 **Report Lost Items** | Quickly file a report for items you've lost with details like description, location, and date |
| 📌 **Report Found Items** | Document discovered items and help reunite them with their owners |
| 🔎 **Search Records** | Query the database by item type, description, or other criteria |
| 🎯 **View Possible Matches** | See potential matches between lost and found reports |
| ✅ **Input Validation** | Ensures data quality with built-in validation for all entries |
| 💾 **Local SQLite Storage** | Persistent, secure storage of all records on your machine |
| 🖥️ **Terminal Navigation** | Clean, intuitive command-line interface |
| ⚡ **Auto Database Init** | Automatic database setup on first run |

---

## 🛠️ Technology Stack

| Technology | Purpose | Version |
|-----------|---------|---------|
| **Java** | Core application language | JDK 25.0.1+ |
| **Maven** | Build and dependency management | 3.6.0+ |
| **SQLite** | Local data persistence | 3.x |
| **SQLite JDBC Driver** | Database connectivity | Latest stable |
| **JUnit** | Unit testing | 4.13+ |
| **PowerShell / Windows Terminal** | Command execution | Windows 10/11 |

---

## 📁 Project Structure

```
campusfind/
├── pom.xml                 # Maven configuration
├── README.md              # This file
├── run-campusfind.bat     # Convenient startup script
│
└── src/
    ├── main/
    │   └── java/          # Source code
    │       └── [Package structure]
    │
    └── test/
        └── java/          # Unit tests
            └── [Test files]
```

---

## ⚙️ Requirements

Before running CampusFind, ensure you have:

- **Operating System:** Windows 10/11
- **Java Development Kit:** JDK 25.0.1 or compatible JDK
- **Maven:** Available through Apache NetBeans or standalone installation
- **Git:** (Optional, for cloning or version control)

---

## 🚀 Installation & Setup

### Step 1: Navigate to Project Directory

Open **PowerShell** or **Command Prompt** and navigate to your CampusFind folder:

```powershell
cd "C:\Users\YOUR-USERNAME\Desktop\vityarthi\campusfind"
```

### Step 2: Add Maven to PATH (if needed)

If you see `mvn is not recognized`, add Maven to your system PATH:

```powershell
$env:Path += ";C:\Program Files\Apache NetBeans\java\maven\bin"
```

### Step 3: Verify Maven Installation

Confirm Maven is available:

```powershell
mvn -version
```

You should see output showing Maven version, Java version, and system details.

---

## ▶️ Running the Project

### Option 1: Using Maven Command

The simplest way to build and run CampusFind:

```powershell
mvn compile exec:java
```

This command:
- Compiles all Java source files
- Launches the CampusFind application
- Runs entirely in your terminal (no localhost, no web browser needed)

### Option 2: Using the BAT File (Recommended for Quick Launch)

For faster startup without typing commands each time:

1. **Double-click** `run-campusfind.bat` in your project folder
2. **Or**, run from PowerShell:

```powershell
.\run-campusfind.bat
```

#### BAT File Example

If you don't have `run-campusfind.bat`, create it with this content:

```batch
@echo off
title CampusFind
cd /d "%~dp0"
set "MAVEN_BIN=C:\Program Files\Apache NetBeans\java\maven\bin"

if not exist "%MAVEN_BIN%\mvn.cmd" (
    echo Maven was not found. Please install Maven or update the path above.
    pause
    exit /b 1
)

echo.
echo Starting CampusFind...
echo.
call "%MAVEN_BIN%\mvn.cmd" compile exec:java
pause
```

Save this as `run-campusfind.bat` in your project root (same folder as `pom.xml`).

---

## 🔨 Build & Test

### Run All Tests

Execute the test suite to verify the application:

```powershell
mvn clean test
```

Expected output:
```
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Build Project Package

Create a compiled package ready for distribution:

```powershell
mvn package
```

This generates a compiled JAR file in the `target/` directory.

### Clean Build

Remove all previous build artifacts and rebuild from scratch:

```powershell
mvn clean install
```

---

## 💻 Usage Flow

Once launched, CampusFind presents a terminal menu:

```
╔═══════════════════════════════════════════════╗
║          Welcome to CampusFind               ║
╠═══════════════════════════════════════════════╣
║  1. Report a Lost Item                        ║
║  2. Report a Found Item                       ║
║  3. Search Items                              ║
║  4. View Possible Matches                     ║
║  5. Exit                                      ║
╚═══════════════════════════════════════════════╝

Select an option (1-5):
```

**Example Workflow:**

1. Choose option **1** → Enter details about your lost item
2. Choose option **2** → Record an item you found
3. Choose option **3** → Search for your lost item or similar entries
4. Choose option **4** → Review matches that might help reunite items with owners

All data is saved automatically to the local SQLite database.

---

## 🗄️ Database Information

### Local Storage

CampusFind uses **SQLite** to store all records locally on your computer:

- **Encrypted Security:** Data never leaves your machine
- **Instant Access:** No internet connection required
- **Automatic Initialization:** Database tables are created automatically on first run
- **File Location:** Stored in the project directory (exact path depends on your configuration)

### Data Persistence

All reports—both lost and found—persist between sessions. Even after closing the application, your records remain safe in the SQLite database.

---

## 🆘 Troubleshooting

### ❌ `mvn is not recognized`

**Problem:** Maven command is not found in PowerShell.

**Solution:** Add Maven to your PATH:

```powershell
$env:Path += ";C:\Program Files\Apache NetBeans\java\maven\bin"
```

Then verify:

```powershell
mvn -version
```

---

### ❌ `No POM in this directory`

**Problem:** Maven can't find `pom.xml`.

**Solution:** Ensure you're in the correct directory:

```powershell
cd "C:\Users\YOUR-USERNAME\Desktop\vityarthi\campusfind"
ls -la pom.xml    # Verify pom.xml exists
```

---

### ❌ `The target cannot be deleted`

**Problem:** Files in `target/` are locked (often by IDE or running process).

**Solution:** 
1. Close all instances of your IDE
2. Manually delete the `target/` folder in File Explorer
3. Rebuild: `mvn clean install`

---

### ❌ `mainClass missing or invalid`

**Problem:** The exec plugin can't find the main application class.

**Solution:** Update your `pom.xml` `<mainClass>` to match your actual package structure:

```xml
<mainClass>com.campusfind.Main</mainClass>
```

---

### ⚠️ SQLite Native Access Warning

**Problem:** Warning message about SQLite native libraries.

**Solution:** This is normal and doesn't affect functionality. If needed, update your SQLite JDBC dependency in `pom.xml`:

```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.0.0</version>
</dependency>
```

---

### ❌ GitHub Repository Not Found

**Problem:** `git push` fails with "repository not found."

**Solution:** 
1. Verify your GitHub URL is correct
2. Ensure the repository exists on GitHub
3. Check that your local remote matches:

```powershell
git remote -v
```

---

## 🌐 Pushing to GitHub

To share your CampusFind project on GitHub:

### Step 1: Create Repository on GitHub

1. Go to [github.com](https://github.com)
2. Click **New Repository**
3. Name it `campusfind`
4. Do **not** initialize with README, .gitignore, or license (we already have these)

### Step 2: Push from Your Local Folder

```powershell
cd "C:\Users\YOUR-USERNAME\Desktop\vityarthi\campusfind"

git init
git add .
git commit -m "Initial CampusFind project"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/campusfind.git
git push -u origin main
```

**Replace `YOUR-USERNAME` with your actual GitHub username.**

---

## ✅ Testing Status

| Metric | Result |
|--------|--------|
| Tests Run | 7 |
| Failures | 0 |
| Errors | 0 |
| Skipped | 0 |
| Build Status | ✅ SUCCESS |

Run tests anytime with: `mvn clean test`

---

## 🚀 Future Improvements

These features are planned for future releases:

- 🌐 **Web Interface** – Access CampusFind from a browser
- 🔐 **User Authentication** – Secure user accounts and login
- 📧 **Email Notifications** – Alert users when matches are found
- 🖼️ **Image Upload** – Attach photos of lost/found items
- 📊 **Admin Dashboard** – Analytics and management tools
- ☁️ **Cloud Database** – Sync across multiple devices

---

## 📄 License

License information can be added here. If a LICENSE file exists in your project, reference it:

```
This project is licensed under the MIT License - see the LICENSE file for details.
```

---

<div align="center">

### 🏫 Built for Campus Communities

**CampusFind** – Making campus life easier, one lost-and-found at a time.

⭐ If this project helped you, consider giving it a star! ⭐

</div>

---

**Last Updated:** September 2026 | **Maintained By:** YOUR-USERNAME

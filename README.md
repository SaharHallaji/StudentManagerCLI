# **Student Management System**

A **Java-based** command-line application for managing student records, featuring:
- **CRUD Operations** (Create, Read, Update, Delete)
- **Input Validation** (UUID, names, semesters, degrees)
- **In-Memory Storage** (Thread-safe `ConcurrentHashMap`)
- **User-Friendly Menu** (Interactive CLI)

---

## **📦 Project Structure**
```
src/
├── com.studentManager.exceptions/
│   └── StudentValidationException.java   # Custom validation errors
├── com.studentManager.helpers/
│   └── MenuHelper.java                  # CLI menu and input handling
├── com.studentManager.repositories/
│   └── StudentRepository.java           # In-memory student database
├── com.studentManager.validators/
│   └── StudentValidators.java           # Validation logic
└── com.studentManager.Main.java                            # Entry point
```

---

## **⚡ Features**
### **1. Student Operations**
- **Add a Student**
    - Validates: Name (≤50 chars), Semester (e.g., `spring 2024`), Degree (`bachelor/master/phd`).
    - Auto-generates a **UUID** for each student.
- **List All Students**
    - Displays ID, full name, semester, and degree.
- **Update/Delete Students**
    - Uses UUID to identify records.

### **2. Validation Rules**
| Field      | Validation Rule                              |
|------------|---------------------------------------------|
| Name       | Non-empty, ≤50 characters                   |
| Semester   | Format: `<season> <year>` (e.g., `fall 2023`) |
| Degree     | `bachelor`, `master`, or `phd`              |
| UUID       | Auto-generated (or validated if provided)   |

### **3. Technical Highlights**
- **Thread-Safe Storage**: `ConcurrentHashMap` in `StudentRepository`.
- **Clean Error Handling**: Custom `StudentValidationException`.
- **Reusable Validators**: Centralized in `StudentValidators`.

---

## **🛠️ How to Run**
### **Prerequisites**
- Java 17+
- Maven/Gradle (optional)

### **Steps**
1. **Compile & Run**:
   ```bash
   javac src/com.studentManager.Main.java -d out/
   java -cp out/ com.studentManager.Main
   ```
2. **Use the Menu**:
   ```text
   1. Add a student
   2. List all students
   3. Update a student
   4. Remove a student
   0. Exit
   ```

---

## **📈 Possible Extensions**
1. **Persistence**: Save data to a file (JSON/CSV) or database (SQLite).
2. **Tests**: Add unit tests (JUnit) for com.studentManager.validators/repository.
3. **GUI**: Convert to a Swing/JavaFX app.

---

## **⚖️ License**
MIT License. Free to use and modify.

---

**🏗️ Key Design Choices**:
- **Enum Singleton**: `StudentRepository` ensures a single, thread-safe instance.
- **Separation of Concerns**: Validators, repository, and UI are decoupled.

Let me know if you'd like to expand this further! ✨
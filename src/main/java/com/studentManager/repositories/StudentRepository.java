package com.studentManager.repositories;

import com.studentManager.config.DatabaseConfig;
import java.sql.SQLException;
import java.util.*;

public class StudentRepository {
    private static final StudentRepository instance = new StudentRepository();

    private StudentRepository() {}

    public static StudentRepository getInstance() { return instance; }

    public String addStudent(Map<String, String> student) {
        String sql = "INSERT INTO students (first_name, last_name, semester, degree) VALUES (?, ?, ?, ?) RETURNING id";
        try {
            return DatabaseConfig.executeUpdate(sql,
                    student.get("first_name"),
                    student.get("last_name"),
                    student.get("semester"),
                    student.get("degree")
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student", e);
        }
    }

    public List<Map<String, String>> getAllStudents() {
        String sql = "SELECT id, first_name, last_name, semester, degree FROM students";
        try {
            return DatabaseConfig.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch students", e);
        }
    }

    public boolean studentExists(String id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM students WHERE id = ?::uuid)";
        try {
            List<Map<String, String>> result = DatabaseConfig.executeQuery(sql, id);
            return Boolean.parseBoolean(result.getFirst().get("exists"));
        } catch (SQLException | IndexOutOfBoundsException e) {
            throw new RuntimeException("Failed to check student existence", e);
        }
    }

    public boolean updateStudent(String id, Map<String, String> student) {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, semester = ?, degree = ? WHERE id = ?::uuid";
        try {
            DatabaseConfig.executeUpdate(sql,
                    student.get("first_name"),
                    student.get("last_name"),
                    student.get("semester"),
                    student.get("degree"),
                    id
            );
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update student", e);
        }
    }

    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM students WHERE id = ?::uuid RETURNING id";
        try {
            return DatabaseConfig.executeUpdate(sql, id) != null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student", e);
        }
    }
}
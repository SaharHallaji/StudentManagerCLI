package repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public enum StudentRepository {
    INSTANCE;

    private final Map<String, Map<String, String>> students = new ConcurrentHashMap<>();

    public String addStudent(Map<String, String> student) {
        String uuid = UUID.randomUUID().toString();
        students.put(uuid, new HashMap<>(student));
        return uuid;
    }

    public List<Map<String, String>> getAllStudents() {
        return students.entrySet().stream().map(entry -> {
           Map<String, String> student = new HashMap<>(entry.getValue());
           student.put("id", entry.getKey());
           return student;
        }).collect(Collectors.toList());
    }

}
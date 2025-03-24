package repositories;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public enum StudentRepository {
    INSTANCE;

    private final Map<String, Map<String, String>> students = new ConcurrentHashMap<>();

    public String addStudent(Map<String, String> student) {
        String uuid = UUID.randomUUID().toString();
        students.put(uuid, new HashMap<>(student));
        return uuid;
    }

}
package com.studentManager.dao;

import com.studentManager.models.DepartmentModel;
import java.util.List;
import java.util.Optional;

public interface DepartmentDao {

    List<DepartmentModel> getAllDepartments();
    Optional<DepartmentModel> updateDepartment(DepartmentModel department);
    Optional<DepartmentModel> saveDepartment(DepartmentModel department);
    Optional<DepartmentModel> deleteDepartment(int departmentId);
    Optional<DepartmentModel> getDepartment(int departmentId);

    Optional<DepartmentModel> addProfessorToDepartment(int departmentId, int professorId);
    Optional<DepartmentModel> removeProfessorFromDepartment(int departmentId, int professorId);
}

package com.studentManager.services;


import com.studentManager.dao.DepartmentDaoImp;
import com.studentManager.dao.ProfessorDaoImp;
import com.studentManager.helpers.FieldsHelper;
import com.studentManager.models.DepartmentModel;
import com.studentManager.models.ProfessorModel;

import java.util.*;

public class DepartmentService {


    static DepartmentDaoImp departmentDaoImp = new DepartmentDaoImp();
    static ProfessorDaoImp professorDaoImp = new ProfessorDaoImp();


    public static void addNewDepartment() {
        Map<String , String> departmentData = FieldsHelper.getDepartmentFields();

        DepartmentModel departmentModel = new DepartmentModel();

        departmentModel.setName(departmentData.get("name"));

        Optional<DepartmentModel> savedDepartment = departmentDaoImp.saveDepartment(departmentModel);

        savedDepartment.ifPresentOrElse( department -> {
                    System.out.println("\nDepartment added successfully : \n");
                    System.out.println("ID: " + department.getDepartmentId());
                },
                ()-> System.out.println("Something went wrong while saving the student."));
    }

    public static void getAllDepartments() {
        List<DepartmentModel> departments = departmentDaoImp.getAllDepartments();

        if (departments.isEmpty()) {
            System.out.println("\nNo department found!");
        } else {
            System.out.println("\nList of departments : ");

            departments.forEach(department -> {
                System.out.println("ID : " + department.getDepartmentId());
                System.out.println("Name : " + department.getName());
                if (department.getProfessors() != null) {
                    System.out.println("Professors : ");
                    department.getProfessors().forEach(professor ->
                            System.out.println(" + " +
                                    professor.getProfessorId() +
                                    " : " +
                                    professor.getFirstName() +
                                    " " +
                                    professor.getLastName()
                            )
                    );
                } else {
                    System.out.println("Department has no professors");
                }
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    public static void updateDepartment() {
        int id = FieldsHelper.getIdInput("Department");

        departmentDaoImp.getDepartment(id).ifPresentOrElse(input -> {
                    System.out.println("\nUpdating department with ID: " + id);

                    Map<String, String> updatedData = FieldsHelper.getDepartmentFields();

                    input.setName(updatedData.get("name"));

                    if (departmentDaoImp.updateDepartment(input).isPresent()) {
                        System.out.println("\nDepartment updated successfully with ID: " + id);
                    } else {
                        System.out.println("\nDepartment update failed!");
                    }
                },
                ()-> System.out.println("\nDepartment not found!"));
    }

    public static void removeDepartment() {
        int id = FieldsHelper.getIdInput("Department");
        departmentDaoImp.getDepartment(id).ifPresentOrElse(department -> {
            department.getProfessors().forEach(professor ->
                departmentDaoImp.removeProfessorFromDepartment( id, professor.getProfessorId())
                    .ifPresentOrElse(_ -> System.out.print("✔"),
                          ()-> System.out.println("Something went while removing professor : " +
                                  professor.getProfessorId())));

                    if ( departmentDaoImp.deleteDepartment(id).isPresent()) {
                        System.out.println("\nDepartment deleted successfully!");
                    } else {
                        System.out.println("\nDepartment delete failed!");
                    }
                },
                ()-> System.out.println("\nDepartment not found!"));
    }


    public static void addProfessorToDepartment() {
        int departmentId = FieldsHelper.getIdInput("Department");

        departmentDaoImp.getDepartment(departmentId).ifPresentOrElse(_ ->{

            List<ProfessorModel> professors = professorDaoImp.getAllProfessors();

            int professorId = FieldsHelper.getProfessorNumber(professors);

            departmentDaoImp.addProfessorToDepartment(departmentId, professorId)
               .ifPresentOrElse(_ -> System.out.println("\nProfessor added successfully.!"),
                  ()-> System.out.println("\nSomething went wrong while adding the professor.!"));
        }, ()->{
        });
    }


    public static void removeProfessorFromDepartment() {
        int departmentId = FieldsHelper.getIdInput("Department");

        departmentDaoImp.getDepartment(departmentId).ifPresentOrElse(department ->{
            Set<ProfessorModel> professorsSet = department.getProfessors();
            List<ProfessorModel> professors = new ArrayList<>(professorsSet);
            int professorId = FieldsHelper.getProfessorNumber(professors);

            departmentDaoImp.removeProfessorFromDepartment(departmentId, professorId)
                    .ifPresentOrElse(_ -> System.out.println("\nProfessor removed successfully!"),
                    ()-> System.out.println("\nSomething went wrong while removing the professor.!"));

        }, ()-> System.out.println("\nDepartment not found!"));

    }

}

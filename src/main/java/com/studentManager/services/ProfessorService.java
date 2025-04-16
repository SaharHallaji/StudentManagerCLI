package com.studentManager.services;

import com.studentManager.dao.ProfessorDaoImp;
import com.studentManager.helpers.FieldsHelper;
import com.studentManager.models.ProfessorModel;
import com.studentManager.utils.EducationEnum;
import com.studentManager.utils.LevelEnum;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProfessorService {

    static ProfessorDaoImp professorDaoImp = new ProfessorDaoImp();

    public static void addNewProfessor() {
        Map<String , String> professorFields = FieldsHelper.getProfessorFields();

        ProfessorModel professorModel = new ProfessorModel();

        professorModel.setFirstName(professorFields.get("firstName"));
        professorModel.setLastName(professorFields.get("lastName"));
        professorModel.setEmail(professorFields.get("email"));
        professorModel.setLevel(LevelEnum.valueOf(professorFields.get("level")));
        professorModel.setEducation(EducationEnum.valueOf(professorFields.get("education")));

        Optional<ProfessorModel> savedProfessor =  professorDaoImp.saveProfessor(professorModel);

        savedProfessor.ifPresentOrElse( professor -> {
                    System.out.println("\nProfessor added successfully with id: \n");
                    System.out.println("ID: " + professor.getProfessorId());
                },
                ()-> System.out.println("Something went wrong while saving the student."));
    }

    public static void getAllProfessors() {
        List<ProfessorModel> professors = professorDaoImp.getAllProfessors();

        if (professors.isEmpty()) {
            System.out.println("\nNo professor found!");
        } else {
            System.out.println("\nList of Professors: ");

            professors.forEach(professor -> {
                System.out.println("ID : " + professor.getProfessorId());
                System.out.println("First Name : " + professor.getFirstName());
                System.out.println("Last Name : " + professor.getLastName());
                System.out.println("Email : " + professor.getEmail());
                System.out.println("Level : " + professor.getLevel());
                System.out.println("Education : " + professor.getEducation());
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    public static void updateProfessor() {
        int id = FieldsHelper.getIdInput("Professor");

        professorDaoImp.getProfessor(id).ifPresentOrElse(input -> {
                    System.out.println("\nUpdating professor with ID: " + id);

                    Map<String , String> updatedData = FieldsHelper.getProfessorFields();
                    input.setFirstName(updatedData.get("firstName"));
                    input.setLastName(updatedData.get("lastName"));
                    input.setEmail(updatedData.get("email"));
                    input.setLevel(LevelEnum.valueOf(updatedData.get("level")));
                    input.setEducation(EducationEnum.valueOf(updatedData.get("education")));

                    if (professorDaoImp.updateProfessor(input).isPresent()) {
                        System.out.println("\nProfessor updated successfully with ID: " + id);
                    } else {
                        System.out.println("\nProfessor update failed!");
                    }
                },
                ()-> System.out.println("\nProfessor not found!"));
    }

    public static void removeProfessor() {
        int id = FieldsHelper.getIdInput("Professor");
        professorDaoImp.getProfessor(id).ifPresentOrElse(_ -> {
                    if ( professorDaoImp.deleteProfessor(id).isPresent()) {
                        System.out.println("\nProfessor deleted successfully!");
                    } else {
                        System.out.println("\nProfessor delete failed!");
                    }
                },
                ()-> System.out.println("\nProfessor not found!"));
    }


}

package com.studentManager.dao;

import com.studentManager.models.ProfessorModel;

import java.util.List;
import java.util.Optional;

public interface ProfessorDao {

    List<ProfessorModel> getAllProfessors();
    Optional<ProfessorModel> updateProfessor(ProfessorModel professor);
    Optional<ProfessorModel> saveProfessor(ProfessorModel professor);
    Optional<ProfessorModel> deleteProfessor(int professorId);
    Optional<ProfessorModel> getProfessor(int professorId);

}

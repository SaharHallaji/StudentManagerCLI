package com.studentManager.services;

import com.studentManager.dao.CourseDaoImp;
import com.studentManager.dao.ProfessorDaoImp;
import com.studentManager.helpers.FieldsHelper;
import com.studentManager.models.CourseModel;
import com.studentManager.models.ProfessorModel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseService {

    static CourseDaoImp courseDaoImp = new CourseDaoImp();
    static ProfessorDaoImp professorDaoImp = new ProfessorDaoImp();

    public static void addNewCourse() {
        Map<String , String> courseData = FieldsHelper.getCourseFields();

        CourseModel courseModel = new CourseModel();

        courseModel.setTitle(courseData.get("title"));
        courseModel.setDescription(courseData.get("description"));
        courseModel.setCapacity(Integer.parseInt(courseData.get("capacity")));
        courseModel.setPlace(courseData.get("place"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try {
            LocalDateTime start = LocalDateTime.parse(courseData.get("startTime"), formatter);
            LocalDateTime end = LocalDateTime.parse(courseData.get("endTime"), formatter);

            courseModel.setStartTime(start.atZone(ZoneId.systemDefault()).toInstant());
            courseModel.setEndTime(end.atZone(ZoneId.systemDefault()).toInstant());

        } catch (Exception e) {
            System.out.println("Error parsing dates: " + e.getMessage());
            return;
        }

        Optional<CourseModel> savedCourse =  courseDaoImp.saveCourse(courseModel);

        savedCourse.ifPresentOrElse( student -> {
                    System.out.println("\nCourse added successfully with id: \n");
                    System.out.println("ID: " + student.getCourseId());
                },
                ()-> System.out.println("Something went wrong while saving the student."));
    }

    public static void getAllCourses() {
        List<CourseModel> courses = courseDaoImp.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("\nNo course found!");
        } else {
            System.out.println("\nList of Courses: ");

            courses.forEach(course -> {
                System.out.println("ID : " + course.getCourseId());
                System.out.println("Title : " + course.getTitle());
                System.out.println("Description : " + course.getDescription());
                System.out.println("Capacity : " + course.getCapacity());
                System.out.println("Place : " + course.getPlace());
                System.out.println("EndTime : " + course.getEndTime());
                System.out.println("Start Time : " + course.getStartTime());
                if (course.getProfessor() != null) {

                    System.out.println("Professor : " +
                            course.getProfessor().getProfessorId() +
                            " " +
                            course.getProfessor().getFirstName() +
                            " " +
                            course.getProfessor().getLastName()
                    );

                } else {
                    System.out.println("Student has no courses! ");
                }
                System.out.println("---------------------------------------------");
            });

            System.out.println("\nEnd of the list.");
        }
    }

    public static void updateCourse() {
        int id = FieldsHelper.getIdInput("Course");

        courseDaoImp.getCourse(id).ifPresentOrElse(input -> {
                    System.out.println("\nUpdating course with ID: " + id);

                    Map<String , String> updatedData = FieldsHelper.getCourseFields();
                    input.setTitle(updatedData.get("title"));
                    input.setDescription(updatedData.get("description"));
                    input.setCapacity(Integer.parseInt(updatedData.get("capacity")));
                    input.setPlace(updatedData.get("place"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                    try {
                        LocalDateTime start = LocalDateTime.parse(updatedData.get("startTime"), formatter);
                        LocalDateTime end = LocalDateTime.parse(updatedData.get("endTime"), formatter);

                        input.setStartTime(start.atZone(ZoneId.systemDefault()).toInstant());
                        input.setEndTime(end.atZone(ZoneId.systemDefault()).toInstant());

                    } catch (Exception e) {
                        System.out.println("Error parsing dates: " + e.getMessage());
                        return;
                    }

                    if (courseDaoImp.updateCourse(input).isPresent()) {
                        System.out.println("\nCourse updated successfully with ID: " + id);
                    } else {
                        System.out.println("\nCourse update failed!");
                    }
                },
                ()-> System.out.println("\nCourse not found!"));
    }

    public static void removeCourse() {
        int id = FieldsHelper.getIdInput("Student");
        courseDaoImp.getCourse(id).ifPresentOrElse(course -> {
                   if (course.getProfessor() != null) {
                       courseDaoImp.removeProfessor(course.getCourseId())
                          .ifPresentOrElse(_ -> System.out.print("✔"),
                          ()-> System.out.println("Something went while removing course : "
                                  + course.getProfessor().getProfessorId()
                          )
                       );
                   }
                    if ( courseDaoImp.deleteCourse(id).isPresent()) {
                        System.out.println("\nCourse deleted successfully!");
                    } else {
                        System.out.println("\nCourse delete failed!");
                    }
                },
                ()-> System.out.println("\nCourse not found!"));
    }


    public static void defineProfessor() {
        int courseId = FieldsHelper.getIdInput("Course");
        courseDaoImp.getCourse(courseId).ifPresentOrElse(_ -> {
            List<ProfessorModel> professors = professorDaoImp.getAllProfessors();
            int professorId = FieldsHelper.getProfessorNumber(professors);

            courseDaoImp.defineProfessor(courseId, professorId).ifPresentOrElse(
                    _ -> System.out.println("\nProfessor defined successfully!"),
                    ()-> System.out.println("\nSomething went wrong while registering the course.!")
            );
        },()-> System.out.println("\nCourse not found!"));
    }

    public static void removeProfessorFromCourse() {
        int courseId = FieldsHelper.getIdInput("Course");
        courseDaoImp.getCourse(courseId).ifPresentOrElse(_ -> courseDaoImp.removeProfessor(courseId).ifPresentOrElse(_ ->
                        System.out.println("\nProfessor removed successfully!"),
                ()-> System.out.println("\nSomething went wrong while removing the professor.!")),()-> System.out.println("\nCourse not found!"));

    }

}
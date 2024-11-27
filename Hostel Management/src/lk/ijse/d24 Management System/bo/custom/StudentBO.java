package bo.custom;

import bo.SuperBO;
import dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StudentBO extends SuperBO {
    boolean saveStudent(StudentDTO dto) throws Exception;

    boolean updateStudent(StudentDTO dto) throws Exception;

    boolean deleteStudent(String id) throws Exception;

    StudentDTO getStudent(String id) throws Exception;

    ArrayList<StudentDTO> getAllStudents() throws Exception;

    ArrayList<StudentDTO> getMatchingStudents(String search) throws Exception;
}

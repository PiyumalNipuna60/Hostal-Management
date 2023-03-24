package lk.ijse.hostal_management.bo.custom;

import bo.SuperBO;
import dto.UserDTO;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    boolean saveUser(UserDTO dto) throws Exception;

    boolean updateUser(UserDTO dto) throws Exception;

    boolean deleteUser(String id) throws Exception;

    UserDTO getUser(String id) throws Exception;

    ArrayList<UserDTO> getAllUsers() throws Exception;

    ArrayList<UserDTO> getMatchingUsers(String search) throws Exception;
}

package bo.custom;

import bo.SuperBO;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReservationBO extends SuperBO {
    boolean saveReservation(ReservationDTO dto) throws Exception;

    boolean updateReservation(ReservationDTO dto) throws Exception;

    boolean deleteReservation(String id) throws Exception;

    ReservationDTO getReservation(String id) throws Exception;

    ArrayList<ReservationDTO> getAllReservations() throws Exception;

    ArrayList<ReservationDTO> getMatchingReservations(String search) throws Exception;

    String getReservationId() throws Exception;

    ArrayList<String> getRoomIds() throws Exception;

    ArrayList<String> getStudentIds() throws Exception;

    RoomDTO getRoomDetails(String id) throws Exception;

    StudentDTO getStudentDetails(String id) throws Exception;
}

package lk.ijse.hostal_management.bo.custom;

import bo.SuperBO;
import dto.RoomDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RoomBO extends SuperBO {
    boolean saveRoom(RoomDTO dto) throws Exception;

    boolean updateRoom(RoomDTO dto) throws Exception;

    boolean deleteRoom(String id) throws Exception;

    RoomDTO getRoom(String id) throws Exception;

    ArrayList<RoomDTO> getAllRooms() throws Exception;

    ArrayList<RoomDTO> getMatchingRooms(String search) throws Exception;
}

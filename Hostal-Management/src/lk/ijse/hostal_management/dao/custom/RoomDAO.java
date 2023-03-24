package lk.ijse.hostal_management.dao.custom;

import dao.CrudDAO;
import entity.Room;

import java.util.List;

public interface RoomDAO extends CrudDAO<Room,String> {
    List<Room> getMatchingResults(String search);
}

package lk.ijse.hostal_management.bo.custom.impl;

import bo.custom.RoomBO;
import dao.Custom.RoomDAO;
import dao.DAOFactory;
import dto.RoomDTO;
import entity.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomBOImpl implements RoomBO {
    private RoomDAO rDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);
    @Override
    public boolean saveRoom(RoomDTO dto) throws Exception {
       return rDAO.save(new Room(dto.getRoom_Type_id(),dto.getType(),dto.getKey_money(),dto.getQty()));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws Exception {
        return rDAO.update(new Room(dto.getRoom_Type_id(),dto.getType(),dto.getKey_money(),dto.getQty()));
    }

    @Override
    public boolean deleteRoom(String id) throws Exception {
        return rDAO.delete(id);
    }

    @Override
    public RoomDTO getRoom(String id) throws Exception {
        Room room = rDAO.get(id);
        return new RoomDTO(room.getRoom_Type_id(),room.getType(),room.getKey_money(),room.getQty());
    }

    @Override
    public ArrayList<RoomDTO> getAllRooms() throws Exception {
        ArrayList<RoomDTO> roomList = new ArrayList<>();
        roomList.addAll(rDAO.getAll().stream().map(room ->{return new RoomDTO(room.getRoom_Type_id(),room.getType(),room.getKey_money(),room.getQty());}).collect(Collectors.toList()));
        return roomList;
    }

    @Override
    public ArrayList<RoomDTO> getMatchingRooms(String search) throws Exception {
        ArrayList<RoomDTO> roomList = new ArrayList<>();
        roomList.addAll(rDAO.getMatchingResults("%"+search+"%").stream().map(room ->{return new RoomDTO(room.getRoom_Type_id(),room.getType(),room.getKey_money(),room.getQty());}).collect(Collectors.toList()));
        return roomList;
    }
}

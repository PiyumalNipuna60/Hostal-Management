package bo.custom.impl;

import bo.custom.ReservationBO;
import dao.Custom.ReservationDAO;
import dao.Custom.RoomDAO;
import dao.Custom.StudentDAO;
import dao.DAOFactory;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import entity.Reservation;
import entity.Room;
import entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReservationBOImpl implements ReservationBO {
    private final ReservationDAO rDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ROOM);
    private final StudentDAO sDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public boolean saveReservation(ReservationDTO dto) throws Exception {
        StudentDTO sDto = dto.getStudent();
        RoomDTO rDto = dto.getRoom();
        return rDAO.save(new Reservation(dto.getResId(), dto.getDate(),
                new Student(sDto.getStudentId(), sDto.getName(), sDto.getAddress(), sDto.getContactNo(), sDto.getDob(), sDto.getGender()),
                new Room(rDto.getRoom_Type_id(), rDto.getType(), rDto.getKey_money(), rDto.getQty())
                , dto.getStatus()));
    }

    @Override
    public boolean updateReservation(ReservationDTO dto) throws Exception {
        StudentDTO sDto = dto.getStudent();
        RoomDTO rDto = dto.getRoom();
        return rDAO.update(new Reservation(dto.getResId(), dto.getDate(),
                new Student(sDto.getStudentId(), sDto.getName(), sDto.getAddress(), sDto.getContactNo(), sDto.getDob(), sDto.getGender()),
                new Room(rDto.getRoom_Type_id(), rDto.getType(), rDto.getKey_money(), rDto.getQty()), dto.getStatus()));
    }

    @Override
    public boolean deleteReservation(String id) throws Exception {
        return rDAO.delete(id);
    }

    @Override
    public ReservationDTO getReservation(String id) throws Exception {
        Reservation reservation = rDAO.get(id);
        Student s = reservation.getStudent();
        Room r = reservation.getRoom();
        return new ReservationDTO(reservation.getResId(), reservation.getDate(),
                new StudentDTO(s.getStudentId(), s.getName(), s.getAddress(), s.getContactNo(), s.getDob(), s.getGender()),
                new RoomDTO(r.getRoom_Type_id(), r.getType(), r.getKey_money(), r.getQty()),
                reservation.getStatus());
    }

    @Override
    public ArrayList<ReservationDTO> getAllReservations() throws Exception {
        ArrayList<ReservationDTO> resList = new ArrayList<>();
        resList.addAll(rDAO.getAll().stream().map(reservation -> {
            Student s = reservation.getStudent();
            Room r = reservation.getRoom();
            return new ReservationDTO(reservation.getResId(), reservation.getDate(),
                    new StudentDTO(s.getStudentId(), s.getName(), s.getAddress(), s.getContactNo(), s.getDob(), s.getGender()),
                    new RoomDTO(r.getRoom_Type_id(), r.getType(), r.getKey_money(), r.getQty()),
                    reservation.getStatus());
        }).collect(Collectors.toList()));
        return resList;
    }

    @Override
    public ArrayList<ReservationDTO> getMatchingReservations(String search) throws Exception {
        ArrayList<ReservationDTO> resList = new ArrayList<>();
        resList.addAll(rDAO.getMatchingResults("%" + search + "%").stream().map(reservation -> {
            Student s = reservation.getStudent();
            Room r = reservation.getRoom();
            return new ReservationDTO(reservation.getResId(), reservation.getDate(),
                    new StudentDTO(s.getStudentId(), s.getName(), s.getAddress(), s.getContactNo(), s.getDob(), s.getGender()),
                    new RoomDTO(r.getRoom_Type_id(), r.getType(), r.getKey_money(), r.getQty()), reservation.getStatus());
        }).collect(Collectors.toList()));
        return resList;
    }

    @Override
    public String getReservationId() throws Exception {
        return rDAO.getReservationId();
    }

    @Override
    public ArrayList<String> getRoomIds() throws Exception {
        return new ArrayList<String>(roomDAO.getAll().stream().map(room -> {
            return room.getRoom_Type_id();
        }).collect(Collectors.toList()));
    }

    @Override
    public ArrayList<String> getStudentIds() throws Exception {
        return new ArrayList<String>(sDAO.getAll().stream().map(student -> {
            return student.getStudentId();
        }).collect(Collectors.toList()));
    }

    @Override
    public RoomDTO getRoomDetails(String id) throws Exception {
        Room room = roomDAO.get(id);
        return new RoomDTO(room.getRoom_Type_id(), room.getType(), room.getKey_money(), room.getQty());
    }

    @Override
    public StudentDTO getStudentDetails(String id) throws Exception {
        Student student = sDAO.get(id);
        return new StudentDTO(student.getStudentId(), student.getName(), student.getAddress(), student.getContactNo(), student.getDob(), student.getGender());
    }
}

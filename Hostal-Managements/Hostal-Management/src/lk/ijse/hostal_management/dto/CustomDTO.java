package lk.ijse.hostal_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomDTO {
    private String resId;
    private LocalDate date;
    private StudentDTO student;
    private RoomDTO room;
    private String status;
    private String room_Type_id;
    private String type;
    private String key_money;
    private int qty;
    private String StudentId;
    private String name;
    private String address;
    private String ContactNo;
    private LocalDate dob;
    private String gender;
    private String nic;
    private String userName;
    private String password;

    public CustomDTO(String resId, String status, String room_Type_id, String type, String studentId, String name) {
        this.resId = resId;
        this.status = status;
        this.room_Type_id = room_Type_id;
        this.type = type;
        StudentId = studentId;
        this.name = name;
    }
}

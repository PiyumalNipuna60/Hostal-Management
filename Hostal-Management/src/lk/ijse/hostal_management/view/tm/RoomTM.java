package lk.ijse.hostal_management.view.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTM {
    private String room_Type_id;
    private String type;
    private String key_money;
    private int qty;
    private JFXButton btn;
}

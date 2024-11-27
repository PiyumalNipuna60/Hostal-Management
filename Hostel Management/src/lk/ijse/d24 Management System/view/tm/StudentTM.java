package view.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentTM {
    private String StudentId;
    private String name;
    private String address;
    private String ContactNo;
    private LocalDate dob;
    private String gender;
    private JFXButton btn;
}

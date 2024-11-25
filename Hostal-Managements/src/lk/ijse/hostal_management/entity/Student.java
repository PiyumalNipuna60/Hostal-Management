package lk.ijse.hostal_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Student {
    @Id
    private String StudentId;
    private String name;
    private String address;
    private String ContactNo;
    private LocalDate dob;
    private String gender;

    public Student(String studentId, String name, String address, String contactNo, LocalDate dob, String gender) {
        StudentId = studentId;
        this.name = name;
        this.address = address;
        ContactNo = contactNo;
        this.dob = dob;
        this.gender = gender;
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> resList = new ArrayList<>();
}

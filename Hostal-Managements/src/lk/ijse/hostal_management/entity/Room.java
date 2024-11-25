package lk.ijse.hostal_management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
public class Room {
    @Id
    private String room_Type_id;
    private String type;
    private String key_money;

    public Room(String room_Type_id, String type, String key_money, int qty) {
        this.room_Type_id = room_Type_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }

    private int qty;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> resList = new ArrayList<>();
}

package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.databaseconfig.Config;

import javax.persistence.*;

@Entity
@Table(name = "rental_type", schema = Config.DATABASE_NAME)
@Data
public class RentalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
}

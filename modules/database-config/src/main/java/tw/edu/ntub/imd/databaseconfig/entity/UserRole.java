package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.databaseconfig.Config;

import javax.persistence.*;

@Entity
@Table(name = "user_role", schema = Config.DATABASE_NAME)
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, updatable = false)
    private Integer id;
    @Column(name = "name", length = 50, nullable = false, insertable = false, updatable = false)
    private String name;
}

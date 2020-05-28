package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "station_address", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class StationAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "enable", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    private Boolean enable = true;
    @Column(name = "longitude", length = 50, nullable = false)
    private String longitude;
    @Column(name = "latitude", length = 50, nullable = false)
    private String latitude;
    @Column(name = "address", length = 200, nullable = false)
    private String address;
    @Column(name = "total_count", nullable = false)
    private Integer totalCount;
    @Column(name = "create_id", length = 100, nullable = false)
    private String createId;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate;
}

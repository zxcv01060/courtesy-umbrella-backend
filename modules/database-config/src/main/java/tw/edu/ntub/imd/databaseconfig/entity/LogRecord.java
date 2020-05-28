package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.LogRecordDeviceConverter;
import tw.edu.ntub.imd.databaseconfig.converter.LogRecordDeviceTypeConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.LogRecordDevice;
import tw.edu.ntub.imd.databaseconfig.enumerated.LogRecordDeviceType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = "userByExecutor")
@Entity
@Table(name = "log_record", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class LogRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "url", length = 500, nullable = false)
    private String url;
    @Column(name = "ip", length = 19, nullable = false)
    private String ip;
    @Column(name = "executor", length = 100, nullable = false)
    private String executor;
    @Column(name = "execute_date", nullable = false)
    private LocalDateTime executeDate = LocalDateTime.now();
    @Column(name = "device", length = 1, nullable = false)
    @Convert(converter = LogRecordDeviceConverter.class)
    private LogRecordDevice device;
    @Column(name = "device_type", length = 1, nullable = false)
    @Convert(converter = LogRecordDeviceTypeConverter.class)
    private LogRecordDeviceType deviceType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByExecutor;

    public void setUserByExecutor(User userByExecutor) {
        this.userByExecutor = userByExecutor;
        if (userByExecutor != null) {
            setExecutor((userByExecutor.getAccount()));
        }
    }
}

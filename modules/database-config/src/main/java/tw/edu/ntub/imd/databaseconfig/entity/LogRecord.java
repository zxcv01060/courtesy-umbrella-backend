package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;
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
    @Column(name = "server_version", length = 20, nullable = false)
    private String serverVersion;
    @Column(name = "ip", length = 19, nullable = false)
    private String ip;
    @Column(name = "method", length = 6, nullable = false)
    private String method;
    @Column(name = "url", length = 500, nullable = false)
    private String url;
    @Column(name = "executor", length = 100, nullable = false)
    private String executor;
    @Column(name = "execute_date", nullable = false)
    private LocalDateTime executeDate = LocalDateTime.now();
    @Column(name = "device", length = 2, nullable = false)
    @Convert(converter = LogRecordDeviceConverter.class)
    private LogRecordDevice device;
    @Column(name = "device_type", length = 2, nullable = false)
    @Convert(converter = LogRecordDeviceTypeConverter.class)
    private LogRecordDeviceType deviceType;
    @Column(name = "device_version", length = 100, nullable = false)
    private String deviceVersion;
    @Column(name = "result", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    @Getter(AccessLevel.NONE)
    private Boolean success;
    @Column(name = "error_code", length = 50, nullable = false)
    private String errorCode;
    @Column(name = "message", length = 3000, nullable = false)
    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByExecutor;

    public void setMethod(String method) {
        if (method != null) {
            this.method = method.toUpperCase();
        }
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setUserByExecutor(User userByExecutor) {
        this.userByExecutor = userByExecutor;
        if (userByExecutor != null) {
            executor = userByExecutor.getAccount();
        }
    }
}

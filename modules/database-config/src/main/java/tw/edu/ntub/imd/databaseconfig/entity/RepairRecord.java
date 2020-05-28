package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.StatusConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {
        "userByReporter",
        "userByHandler"
})
@Entity
@Table(name = "repair_record", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class RepairRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "status", length = 1, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status;
    @Column(name = "reporter", length = 100, nullable = false)
    private String reporter;
    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;
    @Column(name = "handler", length = 100)
    private String handler;
    @Column(name = "handle_date")
    private LocalDateTime handleDate;
    @Column(name = "complete_date")
    private LocalDateTime completeDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByReporter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByHandler;

    public void setUserByReporter(User userByReporter) {
        this.userByReporter = userByReporter;
        if (userByReporter != null) {
            reporter = userByReporter.getAccount();
        }
    }

    public void setUserByHandler(User userByHandler) {
        this.userByHandler = userByHandler;
        if (userByHandler != null) {
            handler = userByHandler.getAccount();
        }
    }
}

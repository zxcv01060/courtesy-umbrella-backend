package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.StatusConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "repair_record", schema = Config.DATABASE_NAME)
@Data
@EqualsAndHashCode(exclude = {
        "userByReporter",
        "userByRepairer",
        "userByModifyId"
})
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
    @Column(name = "repairer", length = 100)
    private String repairer;
    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "complete_date")
    private LocalDateTime completeDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByReporter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repairer", referencedColumnName = "account", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByRepairer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_id", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByModifyId;

    public void setUserByReporter(User userByReporter) {
        this.userByReporter = userByReporter;
        if (userByReporter != null) {
            setReporter(userByReporter.getAccount());
        }
    }

    public void setUserByRepairer(User userByRepairer) {
        this.userByRepairer = userByRepairer;
        if (userByRepairer != null) {
            setRepairer(userByRepairer.getAccount());
        }
    }

    public void setUserByModifyId(User userByModifyId) {
        this.userByModifyId = userByModifyId;
        if (userByModifyId != null) {
            setModifyId(userByModifyId.getAccount());
        }
    }
}

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
@Table(name = "feedback", schema = Config.DATABASE_NAME)
@Data
@EqualsAndHashCode(exclude = {
        "repairRecordByRepairRecordId",
        "userByHandler",
        "userByModifyId"
})
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "repair_record_id")
    private Integer repairRecordId;
    @Column(name = "status", length = 1, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status;
    @Column(name = "email", length = 255, nullable = false)
    private String email;
    @Column(name = "content_message", length = 3000, nullable = false)
    private String contentMessage;
    @Column(name = "handler", length = 100)
    private String handler;
    @Column(name = "handle_date")
    private LocalDateTime handleDate;
    @Column(name = "complete_date")
    private LocalDateTime completeDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_record_id", referencedColumnName = "id", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private RepairRecord repairRecordByRepairRecordId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler", referencedColumnName = "account", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByHandler;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_id", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByModifyId;

    public void setRepairRecordByRepairRecordId(RepairRecord repairRecordByRepairRecordId) {
        this.repairRecordByRepairRecordId = repairRecordByRepairRecordId;
        if (repairRecordByRepairRecordId != null) {
            setRepairRecordId(repairRecordByRepairRecordId.getId());
        }
    }

    public void setUserByHandler(User userByHandler) {
        this.userByHandler = userByHandler;
        if (userByHandler != null) {
            setHandler(userByHandler.getAccount());
        }
    }

    public void setUserByModifyId(User userByModifyId) {
        this.userByModifyId = userByModifyId;
        if (userByModifyId != null) {
            setModifyId(userByModifyId.getAccount());
        }
    }
}

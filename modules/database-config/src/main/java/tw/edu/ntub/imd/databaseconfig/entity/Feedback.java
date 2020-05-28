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
        "repairRecordByRepairRecordId",
        "userByHandler"
})
@Entity
@Table(name = "feedback", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
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
    @Column(name = "feedback_message", length = 3000, nullable = false)
    private String feedbackMessage;
    @Column(name = "handler", length = 100)
    private String handler;
    @Column(name = "response_message", length = 3000)
    private String responseMessage;
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
    private RepairRecord repairRecordByRepairRecordId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handler", referencedColumnName = "account", insertable = false, updatable = false)
    private User userByHandler;

    public void setRepairRecordByRepairRecordId(RepairRecord repairRecordByRepairRecordId) {
        this.repairRecordByRepairRecordId = repairRecordByRepairRecordId;
        if (repairRecordByRepairRecordId != null) {
            repairRecordId = repairRecordByRepairRecordId.getId();
        }
    }

    public void setUserByHandler(User userByHandler) {
        this.userByHandler = userByHandler;
        if (userByHandler != null) {
            handler = userByHandler.getAccount();
        }
    }
}

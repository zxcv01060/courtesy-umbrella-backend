package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.MediaTypeConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.MediaType;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {
        "repairRecordByRecordId"
})
@Entity
@Table(name = "repair_attachment", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class RepairAttachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "record_id", nullable = false)
    private Integer recordId;
    @Column(name = "type", length = 1, nullable = false)
    @Convert(converter = MediaTypeConverter.class)
    private MediaType type;
    @Column(name = "url", length = 300, nullable = false)
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private RepairRecord repairRecordByRecordId;

    public void setRepairRecordByRecordId(RepairRecord repairRecordByRecordId) {
        this.repairRecordByRecordId = repairRecordByRecordId;
        if (repairRecordByRecordId != null) {
            recordId = repairRecordByRecordId.getId();
        }
    }
}

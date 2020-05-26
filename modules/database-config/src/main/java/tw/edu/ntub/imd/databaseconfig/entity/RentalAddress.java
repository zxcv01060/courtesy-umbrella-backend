package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_address", schema = Config.DATABASE_NAME)
@Data
@EqualsAndHashCode(exclude = {
        "itemTypeByItemTypeId",
        "userByCreateId",
        "userByModifyId"
})
public class RentalAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "longitude", length = 50, nullable = false)
    private String longitude;
    @Column(name = "latitude", length = 50, nullable = false)
    private String latitude;
    @Column(name = "address", length = 200, nullable = false)
    private String address;
    @Column(name = "enable", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    private Boolean enable = true;
    @Column(name = "item_type_id", nullable = false)
    private Integer itemTypeId;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ItemType itemTypeByItemTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_id", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByCreateId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modify_id", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByModifyId;

    public void setItemTypeByItemTypeId(ItemType itemTypeByItemTypeId) {
        this.itemTypeByItemTypeId = itemTypeByItemTypeId;
        if (itemTypeByItemTypeId != null) {
            setItemTypeId(itemTypeByItemTypeId.getId());
        }
    }

    public void setUserByCreateId(User userByCreateId) {
        this.userByCreateId = userByCreateId;
        if (userByCreateId != null) {
            setCreateId(userByCreateId.getAccount());
        }
    }

    public void setUserByModifyId(User userByModifyId) {
        this.userByModifyId = userByModifyId;
        if (userByModifyId != null) {
            setModifyId(userByModifyId.getAccount());
        }
    }
}

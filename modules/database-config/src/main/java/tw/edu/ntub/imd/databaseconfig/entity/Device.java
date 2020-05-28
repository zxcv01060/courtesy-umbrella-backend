package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.databaseconfig.Config;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {
        "stationAddressByAddressId",
        "itemTypeByItemTypeId"
})
@Entity
@Table(name = "device", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class Device {
    @Id
    @Column(name = "id", length = 40, nullable = false)
    private String id;
    @Column(name = "address_id")
    private Integer addressId;
    @Column(name = "provider", length = 150, nullable = false)
    private String provider;
    @Column(name = "item_type_id")
    private Integer itemTypeId;
    @Column(name = "create_id", length = 100, nullable = false)
    private String createId;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", insertable = false, updatable = false)
    private StationAddress stationAddressByAddressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ItemType itemTypeByItemTypeId;

    public void setStationAddressByAddressId(StationAddress stationAddressByAddressId) {
        this.stationAddressByAddressId = stationAddressByAddressId;
        if (stationAddressByAddressId != null) {
            addressId = stationAddressByAddressId.getId();
        }
    }

    public void setItemTypeByItemTypeId(ItemType itemTypeByItemTypeId) {
        this.itemTypeByItemTypeId = itemTypeByItemTypeId;
        if (itemTypeByItemTypeId != null) {
            itemTypeId = itemTypeByItemTypeId.getId();
        }
    }
}

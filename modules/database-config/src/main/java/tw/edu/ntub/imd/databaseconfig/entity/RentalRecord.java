package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.databaseconfig.converter.RentalRecordStatusConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.RentalRecordStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {
        "deviceByDeviceId",
        "itemTypeByItemTypeId",
        "stationAddressByAddressId",
        "userByRenter",
        "stationAddressByRestorationAddressId"
})
@Entity
@Table(name = "rental_record", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class RentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "device_id", length = 50, nullable = false)
    private String deviceId;
    @Column(name = "address_id", nullable = false)
    private Integer addressId;
    @Column(name = "item_type_id", nullable = false)
    private Integer itemTypeId;
    @Column(name = "renter", length = 100, nullable = false)
    private String renter;
    @Column(name = "status", length = 1, nullable = false)
    @Convert(converter = RentalRecordStatusConverter.class)
    private RentalRecordStatus status = RentalRecordStatus.LOAN;
    @Column(name = "enable", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    private Boolean enable = true;
    @Column(name = "restoration_address_id")
    private Integer restorationAddressId;
    @Column(name = "restoration_date")
    private LocalDateTime restorationDate;
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Device deviceByDeviceId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private StationAddress stationAddressByAddressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private ItemType itemTypeByItemTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    private User userByRenter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restoration_address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private StationAddress stationAddressByRestorationAddressId;

    public void setDeviceByDeviceId(Device deviceByDeviceId) {
        this.deviceByDeviceId = deviceByDeviceId;
        if (deviceByDeviceId != null) {
            deviceId = deviceByDeviceId.getId();
        }
    }

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

    public void setUserByRenter(User userByRenter) {
        this.userByRenter = userByRenter;
        if (userByRenter != null) {
            renter = userByRenter.getAccount();
        }
    }

    public void setStationAddressByRestorationAddressId(StationAddress stationAddressByRestorationAddressId) {
        this.stationAddressByRestorationAddressId = stationAddressByRestorationAddressId;
        if (stationAddressByRestorationAddressId != null) {
            restorationAddressId = stationAddressByRestorationAddressId.getId();
        }
    }
}

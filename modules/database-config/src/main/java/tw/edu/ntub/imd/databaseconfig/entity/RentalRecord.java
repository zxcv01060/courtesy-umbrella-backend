package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;
import tw.edu.ntub.imd.databaseconfig.converter.RentalRecordStatusConverter;
import tw.edu.ntub.imd.databaseconfig.enumerated.RentalRecordStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rental_record", schema = Config.DATABASE_NAME)
@Data
@EqualsAndHashCode(exclude = {
        "itemTypeByItemTypeId",
        "rentalAddressByAddressId",
        "rentalTypeByType",
        "userByRenter",
        "userByRecipient",
        "userByThirdParty",
        "rentalAddressByRestorationAddressId"
})
public class RentalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "item_type_id", nullable = false)
    private Integer itemTypeId;
    @Column(name = "address_id", nullable = false)
    private Integer addressId;
    @Column(name = "type", nullable = false)
    private Integer type;
    @Column(name = "renter", length = 100, nullable = false)
    private String renter;
    @Column(name = "recipient", length = 100, nullable = false)
    private String recipient;
    @Column(name = "third_party", length = 100)
    private String thirdParty;
    @Column(name = "restoration_address_id")
    private Integer restorationAddressId;
    @Column(name = "expected_date", nullable = false)
    private LocalDateTime expectedDate;
    @Column(name = "actual_date")
    private LocalDateTime actualDate;
    @Column(name = "status", length = 1, nullable = false)
    @Convert(converter = RentalRecordStatusConverter.class)
    private RentalRecordStatus status = RentalRecordStatus.LOAN;
    @Column(name = "enable", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    private Boolean enable;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_type_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private ItemType itemTypeByItemTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private RentalAddress rentalAddressByAddressId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private RentalType rentalTypeByType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByRenter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByRecipient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_party", referencedColumnName = "account", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private User userByThirdParty;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restoration_address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private RentalAddress rentalAddressByRestorationAddressId;

    public void setItemTypeByItemTypeId(ItemType itemTypeByItemTypeId) {
        this.itemTypeByItemTypeId = itemTypeByItemTypeId;
        if (itemTypeByItemTypeId != null) {
            setItemTypeId(itemTypeByItemTypeId.getId());
        }
    }

    public void setRentalAddressByAddressId(RentalAddress rentalAddressByAddressId) {
        this.rentalAddressByAddressId = rentalAddressByAddressId;
        if (rentalAddressByAddressId != null) {
            setAddressId(rentalAddressByAddressId.getId());
        }
    }

    public void setRentalTypeByType(RentalType rentalTypeByType) {
        this.rentalTypeByType = rentalTypeByType;
        if (rentalTypeByType != null) {
            setType(rentalTypeByType.getId());
        }
    }

    public void setUserByRenter(User userByRenter) {
        this.userByRenter = userByRenter;
        if (userByRenter != null) {
            setRenter(userByRenter.getAccount());
        }
    }

    public void setUserByRecipient(User userByRecipient) {
        this.userByRecipient = userByRecipient;
        if (userByRecipient != null) {
            setRecipient(userByRecipient.getAccount());
        }
    }

    public void setUserByThirdParty(User userByThirdParty) {
        this.userByThirdParty = userByThirdParty;
        if (userByThirdParty != null) {
            setThirdParty(userByThirdParty.getAccount());
        }
    }

    public void setRentalAddressByRestorationAddressId(RentalAddress rentalAddressByRestorationAddressId) {
        this.rentalAddressByRestorationAddressId = rentalAddressByRestorationAddressId;
        if (rentalAddressByRestorationAddressId != null) {
            setRestorationAddressId(rentalAddressByRestorationAddressId.getId());
        }
    }
}

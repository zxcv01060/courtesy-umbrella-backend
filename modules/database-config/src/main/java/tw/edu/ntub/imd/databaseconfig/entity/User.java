package tw.edu.ntub.imd.databaseconfig.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.domain.Persistable;
import tw.edu.ntub.imd.birc.common.annotation.CopyIgnore;
import tw.edu.ntub.imd.databaseconfig.Config;
import tw.edu.ntub.imd.databaseconfig.converter.BooleanTo1And0Converter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = "userRoleByRoleId")
@Entity
@Table(name = "user", schema = Config.DATABASE_NAME)
@SuppressWarnings("unused")
public class User implements Persistable<String> {
    @Transient
    @CopyIgnore
    @Getter(AccessLevel.NONE)
    private boolean save;
    @Id
    @Column(name = "account", length = 100, nullable = false)
    private String account;
    @Column(name = "password", length = 60, nullable = false)
    private String password;
    @Column(name = "role_id", nullable = false)
    private Integer roleId = 3;
    @Column(name = "enable", nullable = false)
    @Convert(converter = BooleanTo1And0Converter.class)
    private Boolean enable = true;
    @Column(name = "email", length = 255, nullable = false)
    private String email;
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();
    @Column(name = "modify_id", length = 100, nullable = false)
    private String modifyId;
    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private UserRole userRoleByRoleId;

    public String getRoleName() {
        return userRoleByRoleId != null ? userRoleByRoleId.getName() : null;
    }

    public void setUserRoleByRoleId(UserRole userRoleByRoleId) {
        this.userRoleByRoleId = userRoleByRoleId;
        if (userRoleByRoleId != null) {
            roleId = userRoleByRoleId.getId();
        }
    }

    @Override
    public String getId() {
        return account;
    }

    @Override
    public boolean isNew() {
        return save;
    }
}

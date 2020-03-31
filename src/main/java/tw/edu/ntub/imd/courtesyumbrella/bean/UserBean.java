package tw.edu.ntub.imd.courtesyumbrella.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBean extends BaseBean {
    private String account;
    private String password;
    private Integer roleId;
    private Boolean enable;
    private String email;
    private LocalDateTime birthday;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}

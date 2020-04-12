package tw.edu.ntub.imd.courtesyumbrella.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "User", description = "使用者相關資訊")
public class UserBean extends BaseBean {
    @Schema(description = "帳號", type = "String", required = true)
    private String account;
    @Schema(description = "密碼", type = "String", required = true)
    private String password;
    @Schema(hidden = true)
    private Integer roleId;
    @Schema(hidden = true)
    private Boolean enable;
    @Schema(description = "信箱", type = "String", example = "email@ntub.edu.tw", required = true)
    private String email;
    @Schema(description = "生日", type = "Date", pattern = "yyyy/MM/dd", example = "2020/04/05", required = true)
    private LocalDate birthday;
    @Schema(hidden = true)
    private LocalDateTime createDate;
    @Schema(hidden = true)
    private String modifyId;
    @Schema(hidden = true)
    private LocalDateTime modifyDate;
}

package tw.edu.ntub.imd.courtesyumbrella.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tw.edu.ntub.imd.courtesyumbrella.util.http.BindingResultUtils;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "User", description = "使用者相關資訊")
public class UserBean extends BaseBean {
    @Schema(description = "帳號")
    @NotBlank(message = "帳號 - " + BindingResultUtils.DEFAULT_NOT_BLANK_MESSAGE_TEMPLATE)
    @Size(max = 100, message = "帳號 - " + BindingResultUtils.DEFAULT_MAX_SIZE_MESSAGE_TEMPLATE)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "帳號 - 請輸入純英文與數字")
    private String account;
    @Schema(description = "密碼")
    @NotBlank(message = "密碼 - " + BindingResultUtils.DEFAULT_NOT_BLANK_MESSAGE_TEMPLATE)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "密碼 - 請輸入純英文與數字")
    private String password;
    @Schema(hidden = true)
    private Integer roleId;
    @Schema(hidden = true)
    private Boolean enable;
    @Schema(description = "信箱", example = "email@ntub.edu.tw")
    @NotBlank(message = "信箱 - " + BindingResultUtils.DEFAULT_NOT_BLANK_MESSAGE_TEMPLATE)
    @Email(message = "信箱 - " + BindingResultUtils.DEFAULT_EMAIL_MESSAGE_TEMPLATE)
    @Size(max = 255, message = "信箱 - " + BindingResultUtils.DEFAULT_MAX_SIZE_MESSAGE_TEMPLATE)
    private String email;
    @Schema(description = "生日", type = "date", pattern = "yyyy/MM/dd", example = "2020/04/05")
    @NotNull(message = "生日 - " + BindingResultUtils.DEFAULT_NOT_NULL_MESSAGE_TEMPLATE)
    @Past(message = "生日 - " + BindingResultUtils.DEFAULT_PAST_MESSAGE_TEMPLATE)
    private LocalDate birthday;
    @Schema(hidden = true)
    private LocalDateTime createDate;
    @Schema(hidden = true)
    private String modifyId;
    @Schema(hidden = true)
    private LocalDateTime modifyDate;
}

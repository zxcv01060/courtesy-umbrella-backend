package tw.edu.ntub.imd.courtesyumbrella.util.http;

import lombok.experimental.UtilityClass;
import org.springframework.validation.BindingResult;
import tw.edu.ntub.imd.courtesyumbrella.exception.form.InvalidFormException;

@UtilityClass
public class BindingResultUtils {
    public final String DEFAULT_NOT_NULL_MESSAGE_TEMPLATE = "未填寫";
    public final String DEFAULT_NOT_BLANK_MESSAGE_TEMPLATE = DEFAULT_NOT_NULL_MESSAGE_TEMPLATE;
    public final String DEFAULT_MAX_SIZE_MESSAGE_TEMPLATE = "輸入字數大於 {max} 個字";
    public final String DEFAULT_EMAIL_MESSAGE_TEMPLATE = "信箱格式錯誤";
    public final String DEFAULT_PAST_MESSAGE_TEMPLATE = "應小於現在日期";

    public void validate(BindingResult bindingResult) throws InvalidFormException {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            throw new InvalidFormException(bindingResult.getFieldError());
        }
    }
}

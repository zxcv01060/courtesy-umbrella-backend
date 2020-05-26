package tw.edu.ntub.imd.courtesyumbrella.exception.form;

import tw.edu.ntub.imd.birc.common.exception.DateParseException;

public class InvalidFormDateFormatException extends InvalidRequestFormatException {
    public InvalidFormDateFormatException(DateParseException cause) {
        super(cause.getDateString() + "日期轉換失敗" + (cause.getDatePattern() != null ? ", 格式化字串為: " + cause.getDatePattern() : ""));
    }
}

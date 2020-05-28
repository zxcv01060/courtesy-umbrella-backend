package tw.edu.ntub.imd.birc.common.exception;

import tw.edu.ntub.imd.birc.common.util.date.LocalDateTimeUtils;

public class UnknownException extends ProjectException {
    public UnknownException(Throwable cause) {
        super("未知錯誤，請聯繫我們處理(" + LocalDateTimeUtils.nowString() + ")", cause);
    }

    @Override
    public String getErrorCode() {
        return "Unknown";
    }
}

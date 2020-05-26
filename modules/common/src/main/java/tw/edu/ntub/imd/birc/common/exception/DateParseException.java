package tw.edu.ntub.imd.birc.common.exception;

public class DateParseException extends ProjectException {
    private final String dateString;
    private final String datePattern;

    public DateParseException(String dateString) {
        super("日期轉換失敗，字串為：" + dateString);
        this.dateString = dateString;
        this.datePattern = null;
    }

    public DateParseException(String dateString, String pattern) {
        super("日期轉換失敗，字串為：" + dateString + "，日期格式為：" + pattern);
        this.dateString = dateString;
        this.datePattern = pattern;
    }

    @Override
    public String getErrorCode() {
        return "Date - ParseError";
    }

    public String getDateString() {
        return dateString;
    }

    public String getDatePattern() {
        return datePattern;
    }
}

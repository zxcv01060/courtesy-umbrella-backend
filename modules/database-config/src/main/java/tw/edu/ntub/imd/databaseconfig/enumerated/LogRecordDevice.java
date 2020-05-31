package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum LogRecordDevice {
    UNKNOWN("-1"),
    POSTMAN("0"),
    BROWSER("1"),
    APP_BROWSER("2"),
    APP("3");

    private final String device;

    LogRecordDevice(String device) {
        this.device = device;
    }

    public static LogRecordDevice getInstance(String device) {
        for (LogRecordDevice value : values()) {
            if (value.device.equals(device)) {
                return value;
            }
        }
        throw new IllegalStateException("未知的設備");
    }

    public String getDevice() {
        return device;
    }
}

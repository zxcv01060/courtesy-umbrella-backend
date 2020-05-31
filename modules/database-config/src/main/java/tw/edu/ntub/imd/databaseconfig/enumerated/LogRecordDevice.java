package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum LogRecordDevice {
    UNKNOWN("-1"),
    POSTMAN("00"),
    BROWSER("01"),
    APP_BROWSER("02"),
    APP("03");

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

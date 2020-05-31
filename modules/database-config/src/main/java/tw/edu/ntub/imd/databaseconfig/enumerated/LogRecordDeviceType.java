package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum LogRecordDeviceType {
    UNKNOWN("-1"),
    POSTMAN("0"),
    IE("1"),
    EDGE("2"),
    CHROME("3"),
    FIRE_FOX("4"),
    SAFARI("5"),
    OPERA("6"),
    ANDROID("7"),
    IOS("8");

    private final String deviceType;

    LogRecordDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public static LogRecordDeviceType getInstance(String deviceType) {
        for (LogRecordDeviceType value : values()) {
            if (value.deviceType.equals(deviceType)) {
                return value;
            }
        }
        throw new IllegalStateException("未知的設備類型");
    }

    public String getDeviceType() {
        return deviceType;
    }
}

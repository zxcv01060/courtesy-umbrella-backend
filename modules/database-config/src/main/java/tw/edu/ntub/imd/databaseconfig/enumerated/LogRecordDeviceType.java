package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum LogRecordDeviceType {
    IE("0"),
    EDGE("1"),
    CHROME("2"),
    FIRE_FOX("3"),
    SAFARI("4"),
    ANDROID("5"),
    IOS("6");

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

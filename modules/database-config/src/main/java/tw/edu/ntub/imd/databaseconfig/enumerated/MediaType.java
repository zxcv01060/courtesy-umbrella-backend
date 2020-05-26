package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum MediaType {
    IMAGE("0"),
    VIDEO("1");

    private final String type;

    MediaType(String type) {
        this.type = type;
    }

    public static MediaType getInstance(String type) {
        for (MediaType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        throw new IllegalStateException("找不到此影像類型");
    }

    public String getType() {
        return type;
    }
}

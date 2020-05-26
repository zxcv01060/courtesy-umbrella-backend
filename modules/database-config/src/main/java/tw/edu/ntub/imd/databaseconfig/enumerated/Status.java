package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum Status {
    TO_DO_PROCESS("0"),
    PROCESSING("1"),
    DONE("2");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public static Status getInstance(String status) {
        for (Status value : values()) {
            if (value.status.equals(status)) {
                return value;
            }
        }
        throw new IllegalStateException("找不到此狀態");
    }

    public String getStatus() {
        return status;
    }
}

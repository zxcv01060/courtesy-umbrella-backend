package tw.edu.ntub.imd.databaseconfig.enumerated;

public enum RentalRecordStatus {
    LOAN("0"),
    BACK("1"),
    BROKEN("2"),
    LOST("3"),
    ROBBED("4");

    private final String status;

    RentalRecordStatus(String status) {
        this.status = status;
    }

    public static RentalRecordStatus getInstance(String status) {
        for (RentalRecordStatus value : values()) {
            if (value.status.equals(status)) {
                return value;
            }
        }
        throw new IllegalStateException("並無此對應的Status");
    }

    public String getStatus() {
        return status;
    }
}

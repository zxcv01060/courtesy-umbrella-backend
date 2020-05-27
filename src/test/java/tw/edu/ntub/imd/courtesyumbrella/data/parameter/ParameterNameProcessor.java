package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

public class ParameterNameProcessor {
    private final static int INDEX_NOT_FOUND = -1;
    private final static int NEXT_WORD_INDEX_STEP = 1;
    private final String name;

    public ParameterNameProcessor(String name) {
        this.name = name;
    }

    public String getFullName() {
        return name;
    }

    public String getPath() {
        if (isNotHavePath()) {
            return name;
        } else {
            int lastDotIndex = getLastDotIndex();
            return name.substring(0, lastDotIndex);
        }
    }

    public boolean isNotHavePath() {
        return getLastDotIndex() == INDEX_NOT_FOUND;
    }

    public int getLastDotIndex() {
        return name.lastIndexOf(".");
    }

    public String getName() {
        if (isNotHavePath()) {
            return name;
        } else {
            int lastDotIndex = getLastDotIndex();
            return name.substring(lastDotIndex + NEXT_WORD_INDEX_STEP);
        }
    }
}

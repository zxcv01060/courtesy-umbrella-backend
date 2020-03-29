package tw.edu.ntub.imd.databaseconfig.dto;

import lombok.Getter;

public class Pager {
    @Getter
    private boolean infinity;

    public int page;
    public int count;

    public static Pager infinity() {
        Pager pager = new Pager();
        pager.infinity = true;
        return pager;
    }

    public int getFirstResultIndex() {
        return (page - 1) * count;
    }
}

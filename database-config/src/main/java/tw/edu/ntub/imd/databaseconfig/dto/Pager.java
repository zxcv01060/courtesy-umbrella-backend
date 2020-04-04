package tw.edu.ntub.imd.databaseconfig.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Pager {
    private int page;
    private int count;
}

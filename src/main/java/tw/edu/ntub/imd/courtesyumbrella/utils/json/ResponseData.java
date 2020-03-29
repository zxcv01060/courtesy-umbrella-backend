package tw.edu.ntub.imd.courtesyumbrella.utils.json;

import com.fasterxml.jackson.databind.JsonNode;

@FunctionalInterface
public interface ResponseData {
    JsonNode getData();
}

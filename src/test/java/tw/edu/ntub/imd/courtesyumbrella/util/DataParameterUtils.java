package tw.edu.ntub.imd.courtesyumbrella.util;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;

import java.util.List;

public class DataParameterUtils {

    public static String transferToJsonString(List<DataParameter> dataParameterList) {
        ObjectNode result = transferToObjectNode(dataParameterList);
        return result.toString();
    }

    public static ObjectNode transferToObjectNode(List<DataParameter> dataParameterList) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        for (DataParameter dataParameter : dataParameterList) {
            dataParameter.setData(result);
        }
        return result;
    }
}

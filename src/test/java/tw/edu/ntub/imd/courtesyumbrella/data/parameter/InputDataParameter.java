package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

public class InputDataParameter extends AbstractDataParameter implements DataParameter {
    private final String[] values;

    public InputDataParameter(String name, String... values) {
        super(name);
        this.values = values;
    }

    @Override
    public void setData(ObjectNode data) {
        String name = getName();
        if (values.length > 1) {
            ArrayNode arrayNode = data.putArray(name);
            for (String value : values) {
                arrayNode.add(value);
            }
        } else if (values.length == 1) {
            data.put(name, values[0]);
        }
    }

    @Override
    public void setData(MockMultipartHttpServletRequestBuilder builder) {
        builder.param(getName(), getValues());
    }

    public String[] getValues() {
        return values;
    }
}

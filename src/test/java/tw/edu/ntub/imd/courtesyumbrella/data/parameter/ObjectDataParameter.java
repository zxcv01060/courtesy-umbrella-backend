package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

public class ObjectDataParameter extends AbstractDataParameter implements DataParameter {
    private final DataParameter[] dataParameterWrapperArray;

    public ObjectDataParameter(String name, DataParameter... dataParameterArray) {
        super(name);
        dataParameterWrapperArray = new DataParameterWrapper[dataParameterArray.length];
        for (int i = 0; i < dataParameterArray.length; i++) {
            DataParameter dataParameter = dataParameterArray[i];
            dataParameterWrapperArray[i] = new DataParameterWrapper(name, dataParameter);
        }
    }

    @Override
    public void setData(ObjectNode data) {
        ObjectNode objectNode = data.putObject(getName());
        for (DataParameter dataParameter : dataParameterWrapperArray) {
            dataParameter.setData(objectNode);
        }
    }

    @Override
    public void setData(MockMultipartHttpServletRequestBuilder builder) {
        for (DataParameter dataParameter : dataParameterWrapperArray) {
            dataParameter.setData(builder);
        }
    }
}

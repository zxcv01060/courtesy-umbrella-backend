package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.io.IOException;

public class DataParameterWrapper extends AbstractDataParameter implements DataParameter {
    private final DataParameter parameter;

    public DataParameterWrapper(String parameterPath, DataParameter parameter) {
        super(parameterPath + parameter.getName());
        this.parameter = parameter;
    }

    @Override
    public void setData(ObjectNode data) {

    }

    @Override
    public void setData(MockMultipartHttpServletRequestBuilder builder) {
        try {
            if (parameter instanceof InputDataParameter) {
                InputDataParameter inputDataParameter = (InputDataParameter) parameter;
                builder.param(getFullName(), inputDataParameter.getValues());
            } else if (parameter instanceof FileDataParameter) {
                FileDataParameter fileDataParameter = (FileDataParameter) parameter;
                builder.file(new MockMultipartFile(getFullName(), fileDataParameter.getInputStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

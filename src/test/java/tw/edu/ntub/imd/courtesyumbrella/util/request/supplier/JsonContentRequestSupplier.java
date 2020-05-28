package tw.edu.ntub.imd.courtesyumbrella.util.request.supplier;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;
import tw.edu.ntub.imd.courtesyumbrella.util.DataParameterUtils;

import java.util.List;

public abstract class JsonContentRequestSupplier implements RequestSupplier {
    @Override
    public RequestBuilder get(String url, List<DataParameter> dataParameterList) {
        MockHttpServletRequestBuilder result = getBuilder(url);
        ObjectNode content = DataParameterUtils.transferToObjectNode(dataParameterList);
        result.contentType(MediaType.APPLICATION_JSON);
        result.characterEncoding("UTF-8");
        result.content(content.toString());
        return result;
    }

    public abstract MockHttpServletRequestBuilder getBuilder(String url);
}

package tw.edu.ntub.imd.courtesyumbrella.util.request.supplier;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;

import java.util.List;

public class FormDataRequestSupplier implements RequestSupplier {
    @Override
    public RequestBuilder get(String url, List<DataParameter> dataParameterList) {
        MockMultipartHttpServletRequestBuilder result = MockMvcRequestBuilders.multipart(url);
        for (DataParameter dataParameter : dataParameterList) {
            dataParameter.setData(result);
        }
        return result;
    }
}

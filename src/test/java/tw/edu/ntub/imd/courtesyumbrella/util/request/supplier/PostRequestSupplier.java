package tw.edu.ntub.imd.courtesyumbrella.util.request.supplier;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class PostRequestSupplier extends JsonContentRequestSupplier {
    @Override
    public MockHttpServletRequestBuilder getBuilder(String url) {
        return MockMvcRequestBuilders.post(url);
    }
}

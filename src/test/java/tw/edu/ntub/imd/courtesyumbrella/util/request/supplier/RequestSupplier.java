package tw.edu.ntub.imd.courtesyumbrella.util.request.supplier;

import org.springframework.test.web.servlet.RequestBuilder;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;

import java.util.List;

public interface RequestSupplier {
    RequestBuilder get(String url, List<DataParameter> dataParameterList);
}

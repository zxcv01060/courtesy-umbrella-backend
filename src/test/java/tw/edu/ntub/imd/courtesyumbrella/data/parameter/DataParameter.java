package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

public interface DataParameter {
    String getName();

    String getPath();

    void setData(ObjectNode data);

    void setData(MockMultipartHttpServletRequestBuilder builder);

    default boolean equalWith(DataParameter other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else {
            String currentParameterName = getName();
            return currentParameterName.equals(other.getName());
        }
    }
}

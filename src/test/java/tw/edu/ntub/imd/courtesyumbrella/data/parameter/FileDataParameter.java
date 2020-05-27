package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

public class FileDataParameter extends AbstractDataParameter implements DataParameter {
    private final Supplier<InputStream> inputStreamSupplier;

    public FileDataParameter(String name, Supplier<InputStream> inputStreamSupplier) {
        super(name);
        this.inputStreamSupplier = inputStreamSupplier;
    }

    @Override
    public void setData(ObjectNode data) {

    }

    @Override
    public void setData(MockMultipartHttpServletRequestBuilder builder) {
        try {
            builder.file(new MockMultipartFile(getFullName(), getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getInputStream() {
        return inputStreamSupplier.get();
    }
}

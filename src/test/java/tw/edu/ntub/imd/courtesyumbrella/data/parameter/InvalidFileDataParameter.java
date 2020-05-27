package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

import java.io.InputStream;
import java.util.function.Supplier;

public class InvalidFileDataParameter extends FileDataParameter implements InvalidDataParameter {
    private final String invalidMessage;

    public InvalidFileDataParameter(String name, Supplier<InputStream> inputStreamSupplier, String invalidMessage) {
        super(name, inputStreamSupplier);
        this.invalidMessage = invalidMessage;
    }

    @Override
    public String getInvalidMessage() {
        return invalidMessage;
    }
}

package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

public class InvalidInputDataParameter extends InputDataParameter implements InvalidDataParameter {
    private final String invalidMessage;

    public InvalidInputDataParameter(String name, String invalidMessage, String... values) {
        super(name, values);
        this.invalidMessage = invalidMessage;
    }

    @Override
    public String getInvalidMessage() {
        return invalidMessage;
    }
}

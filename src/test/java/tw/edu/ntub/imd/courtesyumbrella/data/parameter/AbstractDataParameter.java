package tw.edu.ntub.imd.courtesyumbrella.data.parameter;

public abstract class AbstractDataParameter implements DataParameter {
    private final ParameterNameProcessor nameProcessor;

    public AbstractDataParameter(String name) {
        nameProcessor = new ParameterNameProcessor(name);
    }

    @Override
    public String getName() {
        return nameProcessor.getName();
    }

    @Override
    public String getPath() {
        return nameProcessor.getPath();
    }

    public String getFullName() {
        return nameProcessor.getFullName();
    }
}

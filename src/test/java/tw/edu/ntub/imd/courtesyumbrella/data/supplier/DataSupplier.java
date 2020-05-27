package tw.edu.ntub.imd.courtesyumbrella.data.supplier;

import tw.edu.ntub.imd.courtesyumbrella.data.parameter.DataParameter;
import tw.edu.ntub.imd.courtesyumbrella.data.parameter.InvalidDataParameter;

import java.util.ArrayList;
import java.util.List;

public interface DataSupplier {
    List<DataParameter> getRequiredValidDataList();

    List<DataParameter> getNotRequiredValidDataList();

    List<InvalidDataParameter> getInvalidDataList();

    default List<DataParameter> getAllValidDataList() {
        List<DataParameter> result = new ArrayList<>(getRequiredValidDataList());
        result.addAll(getNotRequiredValidDataList());
        return result;
    }
}

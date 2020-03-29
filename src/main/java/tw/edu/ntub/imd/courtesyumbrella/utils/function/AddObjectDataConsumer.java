package tw.edu.ntub.imd.courtesyumbrella.utils.function;

import tw.edu.ntub.imd.courtesyumbrella.utils.json.object.ObjectData;
import tw.edu.ntub.imd.utils.function.TripleConsumer;

@FunctionalInterface
public interface AddObjectDataConsumer<T> extends TripleConsumer<ObjectData, Integer, T> {
    @Override
    default void accept(ObjectData objectData, Integer integer, T t) {
        addObject(objectData, integer, t);
    }

    void addObject(ObjectData objectData, int index, T t);
}

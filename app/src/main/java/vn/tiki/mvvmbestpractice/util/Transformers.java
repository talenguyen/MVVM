package vn.tiki.mvvmbestpractice.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tale on 2/16/16.
 */
public class Transformers {

    public static <S, D> List<D> transform(List<S> source, TransformFunc<S, D> transformFunc) {
        if (transformFunc == null) {
            throw new NullPointerException("transformFunc must not be null");
        }
        if (source == null || source.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        final List<D> result = new ArrayList<>();
        for (S s : source) {
            D d = transformFunc.transform(s);
            result.add(d);
        }
        return result;
    }
}

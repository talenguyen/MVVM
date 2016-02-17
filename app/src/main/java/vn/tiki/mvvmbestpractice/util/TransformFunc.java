package vn.tiki.mvvmbestpractice.util;

/**
 * Created by tale on 2/16/16.
 */
public interface TransformFunc<S, D> {
    D transform(S source);
}

package home.javaphite.decisiontree.common;

import java.util.function.Function;
import java.util.function.Predicate;

public class GenericBinaryDecisionTreeNode<T, R> implements BinaryDecisionTreeNode<T, R> {

    private final Function<T, R> onTrueFunction;

    private final Function<T, R> onFalseFunction;

    private final Predicate<T> predicate;

    public GenericBinaryDecisionTreeNode(
            Predicate<T> predicate, Function<T, R> onTrueFunction,
            Function<T, R> onFalseFunction) {
        this.predicate = predicate;
        this.onTrueFunction = onTrueFunction;
        this.onFalseFunction = onFalseFunction;
    }

    @Override
    public R onTrue(T input) {
        return onTrueFunction.apply(input);
    }

    @Override
    public R onFalse(T input) {
        return onFalseFunction.apply(input);
    }

    @Override
    public boolean test(T input) {
        return predicate.test(input);
    }
}

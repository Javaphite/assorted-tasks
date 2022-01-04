package home.javaphite.decisiontree.common;

import java.util.function.Predicate;

public interface BinaryDecisionTreeNode<T, R> extends Predicate<T>, DecisionMaker<T, R> {

    @Override
    default R makeDecision(T input) {
        return test(input) ? onTrue(input): onFalse(input);
    }

    R onTrue(T input);

    R onFalse(T input);
}

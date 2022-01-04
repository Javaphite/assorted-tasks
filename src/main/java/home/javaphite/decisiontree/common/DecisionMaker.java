package home.javaphite.decisiontree.common;

public interface DecisionMaker<T, R> {

    R makeDecision(T input);
}

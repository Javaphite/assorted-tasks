package home.javaphite.decisiontree.common;


public class BinaryDecisionTree<T, R> implements DecisionMaker<T, R> {

    private BinaryDecisionTreeNode<T, R> root;

    public BinaryDecisionTree(BinaryDecisionTreeNode<T, R> root) {
        this.root = root;
    }

    @Override
    public R makeDecision(T input) {
        return root.makeDecision(input);
    }
}

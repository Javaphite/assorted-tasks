package home.javaphite.decisiontree;

import home.javaphite.decisiontree.common.GenericBinaryDecisionTreeNode;

public interface FizzBuzzStaticBinaryDecisionTreeNodes {

    GenericBinaryDecisionTreeNode<Integer, String> BUZZ =
            new GenericBinaryDecisionTreeNode<>(i -> i % 5 == 0,
                    i -> "Buzz",
                    i -> i.toString());

    GenericBinaryDecisionTreeNode<Integer, String> FIZZ_BUZZ =
            new GenericBinaryDecisionTreeNode<>(i -> i % 5 == 0,
                    i -> "FizzBuzz",
                    i -> "Fizz");

    GenericBinaryDecisionTreeNode<Integer, String> FIZZ =
            new GenericBinaryDecisionTreeNode<>(i -> i % 3 == 0,
                    i -> FIZZ_BUZZ.makeDecision(i),
                    i -> BUZZ.makeDecision(i));
}


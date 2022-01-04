package home.javaphite.decisiontree;

import home.javaphite.decisiontree.common.BinaryDecisionTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FizzBuzzStaticBinaryDecisionTreeTest {

    BinaryDecisionTree<Integer, String> decisionTree =
            new BinaryDecisionTree<>(FizzBuzzStaticBinaryDecisionTreeNodes.FIZZ);

    @Test
    void shouldReturnFizz() {
        int input = 9;
        assertEquals("Fizz", decisionTree.makeDecision(input));
    }

    @Test
    void shouldReturnBuzz() {
        int input = 25;
        assertEquals("Buzz", decisionTree.makeDecision(input));
    }

    @Test
    void shouldReturnFizzBuzz() {
        int input = 15;
        assertEquals("FizzBuzz", decisionTree.makeDecision(input));
    }

    @Test
    void shouldReturnOriginalValue() {
        int input = 31;
        assertEquals("31", decisionTree.makeDecision(input));
    }

}
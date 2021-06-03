package cs445.rec3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.Arrays;

/**
 * A class that implements simple JUnit tests for the LinkedBag class.
 * @author William C. Garrison III
 * @author Brian Nixon
 */

public class LinkedBagTest {
    // Initialize any global reference variables/final values
    BagInterface<String> bag;
    final String[] duplicateWords = {"data", "structures", "are", "fun", "programs", "need", "data", "foobar"};

    /**
     * This method is called before each test. Used to initialize
     * all global variables before tests
     */
    @BeforeEach
    public void setup() {
        bag = new LinkedBag<String>();
    }

    /**
     * This method is called after each test. Used to reset all
     * global variables after tests
     */
    @AfterEach
    public void teardown() {
        bag = null;
    }

    /**
     * Test case for empty bag after initial creation.
     *
     * Preconditions: None
     * Execution Steps: Call bag.isEmpty(), bag.getCurrentSize()
     *      bag.getFrequencyOf(), bag.contains()
     * Postconditions: The return of each method call should be:
     *      true, 0, 0, false
     */
    @Test
    public void testEmptyBag() {
        assertTrue(bag.isEmpty());
        assertEquals(0, bag.getCurrentSize(), "Empty bag should have size of 0");
        assertEquals(0, bag.getFrequencyOf("Garrison"), "Garrison should have 0 frequency in empty bag");
        assertFalse(bag.contains("Garrison"));
    }

    /**
     * Test case for toArray method.
     *
     * Preconditions: Add duplicateWords to bag.
     * Execution Steps: Call bag.toArray()
     * Postconditions: bag.toArray() is equal to the
     *      duplicateWords array.
     */
    @Test
    public void testToArray() {
        for (String word : duplicateWords) {
            assertTrue(bag.add(word));
        }

        // Execution Steps
        Object[] objs = bag.toArray();
        String[] returnedStrings = new String[duplicateWords.length];
        for (int i = 0; i < objs.length; i++) {
            returnedStrings[i] = (String) objs[i];  //cast each object to String
        }

        for (String word : duplicateWords) {
            boolean foundWord = false;
            for (String innerWord : returnedStrings) {
                if (word.equals(innerWord)) {
                    foundWord = true;
                }
            }
            assertTrue(foundWord);
        }
    }

    /**
     * Test case for equals method.
     *
     * Preconditions: Add duplicateWords to bag and bagTwo. Add "Garrison"
     *      to bagThree.
     * Execution Steps: Call bag.equals(bagTwo), call bag.equals(bagThree),
     *      call bagTwo.equals(bagThree), bagTwo.equals(bag)
     * Postconditions: The return of each method call should be:
     *      true, false, false, true
     */
    @Test
    public void testEquals() {
        BagInterface<String> bagTwo = new LinkedBag<String>();
        BagInterface<String> bagThree = new LinkedBag<String>();

        for (String word : duplicateWords) {
            assertTrue(bag.add(word));
        }

        Util.shuffle(duplicateWords);   //shuffle the order of words
        for (String word : duplicateWords) {
            assertTrue(bagTwo.add(word));
        }

        assertTrue(bagThree.add("Garrison"));

        // Execution Steps
        assertTrue(bag.equals(bagTwo));
        assertFalse(bag.equals(null));
        assertFalse(bag.equals("A string"));
        assertFalse(bag.equals(bagThree));
        assertFalse(bagTwo.equals(bagThree));
        assertTrue(bagTwo.equals(bag));
    }

    /**
     * Test case for removeDuplicatesOf method
     *
     * Preconditions: Add duplicateWords to bag.
     * Execution Steps: Call bag.getFrequencyOf("data"), call
     *      bag.removeDuplicatesOf("data"), call bag.getFrequencyOf("data")
     * Postconditions: The return of the calls to getFrequencyOf should be:
     *      2, 1
     */
    @Test
    public void testRemoveDuplicatesOf() {
        for (String word : duplicateWords) {
            assertTrue(bag.add(word));
        }

        // Execution Steps
        assertEquals(2, bag.getFrequencyOf("data"));
        bag.removeDuplicatesOf("data");
        assertEquals(1, bag.getFrequencyOf("data"));
    }

    /**
     * Test case for remove duplicates.
     *
     * Preconditions: Add duplicateWords to bag.
     * Execution Steps: Call bag.getCurrentSize(), call bag.removeAllDuplicates(),
     *      call bag.getCurrentSize(), call bag.getFrequencyOf("data")
     * Postconditions: The return of each method call should be:
     *      24, 7, 1
     */
    @Test
    public void testRemoveAllDuplicates() {
        for (String word : duplicateWords) {
            assertTrue(bag.add("data"));
            assertTrue(bag.add(word));  //assert that each insertion is successful
            assertTrue(bag.add("data"));
        }

        // Execution Steps
        assertEquals(24, bag.getCurrentSize(), "Bag should have 24 elements before removing duplicates");
        bag.removeAllDuplicates();
        assertEquals(7, bag.getCurrentSize(), "Bag should have 7 elements after removing duplicates");
        assertEquals(1, bag.getFrequencyOf("data"), "Bag should only have 1 instance of \"data\"");
    }

}


package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;
import implementation.KeyValuePair;
import implementation.TreeIsEmptyException;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
    @Test
    public void testAddAndSizeAndIsEmpty() throws Exception {
        BinarySearchTree<String,Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 3);
        assertTrue(bst.isEmpty());
        
        bst.add("Yaniv", 20);
        assertFalse(bst.isEmpty());
        assertEquals(1, bst.size());
        
        bst.add("Azi", 21).add("Jordan", 19);
        assertEquals(3, bst.size());
        
        try { //full tree
            bst.add("Eli", 20);
            fail("Expected TreeIsFullException");
        } catch (TreeIsFullException e) {}
        
        // Duplicate key
        bst = new BinarySearchTree<String,Integer>(Comparator.naturalOrder(), 5);
        bst.add("Sam", 20);
        bst.add("Sam", 21);
        
        assertEquals(1, bst.size());
        assertEquals("{Sam:21}", bst.toString());
    }

    @Test
    public void testToStringEmptyAndMultiple() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 5);
        assertEquals("EMPTY TREE", bst.toString());
        
        bst.add("Yaniv", 20).add("Azi", 21).add("Jordan", 19);
        String rep = bst.toString();
        
        assertTrue(rep.contains("{Yaniv:20"));
        assertTrue(rep.contains("{Azi:21"));
        assertTrue(rep.contains("{Jordan:19}"));
    }

    // Test getMinimumKeyValue and getMaximumKeyValue
    @Test
    public void testMinMax() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 10);
        bst.add("Bronny", 9).add("Lebron", 6).add("Luka", 77);
        
        KeyValuePair<String, Integer> min = bst.getMinimumKeyValue();
        KeyValuePair<String, Integer> max = bst.getMaximumKeyValue();
        
        assertEquals("Bronny", min.getKey()); //by value so Bronny not by value (Lebron:6)
        assertEquals(9, (int) min.getValue());
        assertEquals("Luka", max.getKey());
        assertEquals(77, (int) max.getValue());
        
        // Test exceptions on empty
        bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 1);
        try { 
        	bst.getMinimumKeyValue();
        	fail();
        } catch (TreeIsEmptyException e) {}
        try { 
        	bst.getMaximumKeyValue();
        	fail();
        } catch (TreeIsEmptyException e) {}
    }

    @Test
    public void testFind() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 5);
        bst.add("Yaniv", 20).add("Azi", 21).add("Jordan", 19);
        
        assertNull(bst.find("Eli"));
        
        KeyValuePair<String, Integer> found = bst.find("Jordan");
        
        assertNotNull(found);
        assertEquals("Jordan", found.getKey());
        assertEquals(19, (int) found.getValue());
        
        try { 
        	bst.find(null);
        	fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testDelete() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 5);
        bst.add("Yaniv", 20).add("Azi", 21).add("Jordan", 19).add("Eli", 20).add("Sam", 21);
        
        bst.delete("Azi");
        assertNull(bst.find("Azi"));
        assertEquals(4, bst.size());
        
        // delete root
        bst.delete("Yaniv");
        assertNull(bst.find("Yaniv"));
        assertEquals(3, bst.size());
        
        bst.delete("Jake");
        assertEquals(3, bst.size());
        
        try { 
        	new BinarySearchTree<Integer,Integer>(Comparator.naturalOrder(),1).delete(1);
        	fail();
    	} catch (TreeIsEmptyException e) {}
    }

    @Test
    public void testProcessInorder() throws Exception {
        BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 5);
        bst.add("Yaniv", 20).add("Azi", 21).add("Jordan", 19).add("Eli", 20).add("Sam", 21);
        
        ArrayList<String> keys = new ArrayList<>();
        bst.processInorder((k,v) -> keys.add(k));
        assertEquals(Arrays.asList("Azi", "Eli", "Jordan", "Sam", "Yaniv"), keys);
        
        try {
        	bst.processInorder(null);
        	fail();
    	} catch (IllegalArgumentException e) {}
    }


    @Test
    public void testSubTreeWithStringKeys() throws Exception {
        BinarySearchTree<String,Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 10);
        
        Arrays.asList("Yaniv", "Azi", "Jordan", "Eli", "Sam").forEach(key -> {
        try {
        	int value;
            switch (key) {
                case "Yaniv":
                	value = 20;
                	break;
                case "Azi":
                	value = 21;
                	break;
                case "Jordan":
                	value = 19;
                	break;
                case "Eli":
                	value = 20;
                	break;
                case "Sam":
                	value = 21;
                	break;
                default:
                	throw new AssertionError("Unexpected key: " + key);
            }
            bst.add(key, value);
        } catch (TreeIsFullException e) {
            throw new RuntimeException(e);
        }});
        
        BinarySearchTree<String,Integer> sub = bst.subTree("Eli", "Sam");
        String result = sub.toString();
        assertTrue(result.contains("{Eli:20}"));
        assertTrue(result.contains("{Jordan:19}"));
        assertTrue(result.contains("{Sam:21}"));
        assertFalse(result.contains("{Azi:21}")); //cut off
        assertFalse(result.contains("{Yaniv:20}")); //cut off

        try {
            bst.subTree("Sam", "Eli");
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void testGetLeavesValues() throws Exception {
        BinarySearchTree<String,Integer> bst = new BinarySearchTree<String, Integer>(Comparator.naturalOrder(), 10);
        assertTrue(bst.getLeavesValues().isEmpty());
        
        bst.add("Yaniv", 20).add("Azi", 21).add("Jordan", 19).add("Eli", 20).add("Sam", 21);
        
        TreeSet<Integer> leaves = bst.getLeavesValues();

        assertTrue(leaves.contains(20) && leaves.contains(21));
    }
}

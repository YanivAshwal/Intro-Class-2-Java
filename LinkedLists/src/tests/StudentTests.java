package tests;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;
import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;

/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {
	
    private Comparator<Integer> intComparator = new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o1.compareTo(o2);
        }
    };

    
	@Test
	public void testAddToFrontAndEnd() {
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		list.addToEnd(10);
		
        assertNotNull(list.getFirst());
        assertNotNull(list.getLast());
        assertEquals((Integer) 10, list.getFirst());
        assertEquals((Integer) 10, list.getLast());
        
        
        list.addToEnd(20);
        
        assertEquals((Integer) 10, list.getFirst());
        assertEquals((Integer) 20, list.getLast());
        
        assertEquals(2, list.getSize());
	}
	
	@Test
	public void testRetrieveFrontandEnd() {
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		list.addToFront(10);
		assertEquals((Integer) 10, list.retrieveFirstElement());
		assertEquals(0, list.getSize());
		
		list.addToFront(15); 
		list.addToEnd(25); 
		assertEquals((Integer) 25, list.retrieveLastElement());
		assertEquals(1, list.getSize()); 
	}
	@Test
	public void testRemove() { 
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		list.addToFront(10);
		list.addToEnd(20);
		list.addToEnd(30);
		list.addToEnd(40); 
		list.addToEnd(10);
		
		list.remove(10, intComparator);
		assertEquals(list.getFirst(), (Integer) 20);
		assertEquals(list.getLast(),  (Integer) 40);
		assertEquals(list.getSize(), 3);
	}
	
	@Test
	public void testReverseMethods() { 
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		list.addToEnd(10);
		list.addToEnd(20);
		list.addToEnd(30);
		list.addToEnd(40); 
		list.addToEnd(50);
		
		assertEquals("[50, 40, 30, 20, 10]", list.getReverseArrayList().toString());
		assertEquals("[50, 40, 30, 20, 10]", list.getReverseList().toString());
	}
	
	@Test
	public void testSortedAdd() {
		SortedLinkedList<Integer> sortedList = new SortedLinkedList<>(intComparator);
		
		sortedList.add​(30); 
		sortedList.add​(20); 
		sortedList.add​(10);
		sortedList.add​(15);
		sortedList.add​(35);
		
		assertEquals("[10, 15, 20, 30, 35]", sortedList.toString());
		assertEquals(sortedList.getSize(), 5);

		
		sortedList.remove​(35);
		sortedList.remove​(15);
		assertEquals(sortedList.getSize(), 3);
	}

}

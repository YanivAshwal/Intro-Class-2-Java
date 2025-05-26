package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (comparator == null || maxEntries < 1) { 
			throw new IllegalArgumentException("Null comparator or maxEntries < 1");
		}
		treeSize = 0;
		root = null;
		this.comparator = comparator;
		this.maxEntries = maxEntries;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (key == null || value == null) { 
			throw new IllegalArgumentException("Null key or value") ;
		}
		
		if (isEmpty()) {
			root = new Node(key, value);
			treeSize++;
		} else if (treeSize == maxEntries) {
			throw new TreeIsFullException("Tree is at max capacity");
		} else {
			addAux(key, value, root);
		}
		return this;
	}
	
	private void addAux(K key, V value, Node rootAux) { 
		int c = comparator.compare(key, rootAux.key);
		
		if (c == 0) { //If Key already exists
			rootAux.value = value; //update the curr value
		} else if (c < 0) {	//if val < this.val
			if (rootAux.left == null) { //if left is empty
				rootAux.left = new Node(key,value); //Nodify it 
				treeSize++;
			} else { //if key 
				addAux(key,value, rootAux.left);
			}
		} else { 
			if (rootAux.right == null) {
				rootAux.right = new Node(key, value);
				treeSize++;
			} else { 
				addAux(key, value, rootAux.right);
			}
		}
	}

	public String toString() {
		return toStringAux(root);
	}
	
	
	private String toStringAux(Node root) {
		if (isEmpty()) {
			return "EMPTY TREE";
		} 
		return root == null ? "" : toStringAux(root.left) 
				+ "{" + root.key + ":" + root.value + "}" + toStringAux(root.right);
	}

	/* Provided: d0n't modify */
	public boolean isEmpty() {
		return root == null;
	}

	/* Provided: d0n't modify */
	public int size() {
		return treeSize;
	}

	/* Provided: d0n't modify */
	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty.");
		}
		return minAux(root);
	}
	
	private KeyValuePair<K, V> minAux(Node root){ 
		if (root == null || root.left == null) {
			KeyValuePair<K, V> min =  new KeyValuePair<K, V>(root.key, root.value);
			return min; 
		}
		return minAux(root.left);
	}
 	
	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Tree is empty.");
		}
		return maxAux(root);
	}
	
	private KeyValuePair<K,V> maxAux(Node root) {
		if (root == null || root.right == null) {
			KeyValuePair<K,V> max = new KeyValuePair<K,V>(root.key, root.value);
			return max;
		}
		return maxAux(root.right);
	}

	public KeyValuePair<K, V> find(K key) {
		if (key == null) { 
			throw new IllegalArgumentException("Key cannot be null");
		}
		return findAux(key, root);
	}
	
	private KeyValuePair<K,V> findAux(K key, Node root) {
		if (root == null) {
			return null;
		}
		int c = comparator.compare(key, root.key);

		if (c == 0) {
			return new KeyValuePair<K,V>(root.key, root.value);
		} else if (c < 0) { 
			return findAux(key, root.left);
		} else {
			return findAux(key, root.right);
		}
		
	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (key == null) { 
			throw new IllegalArgumentException("Key cannot be null");
		} else if (isEmpty()) {
			throw new TreeIsEmptyException("Empty Tree"); 
		}
		root =  deleteAux(key, root);
		return this;
		
	}
	
	
	private Node deleteAux(K key, Node root) { 
		if (root == null) {
			return null;
		}
		
		int c = comparator.compare(key, root.key);
		
		if (c < 0) {
			root.left = deleteAux(key, root.left); //step deeper 
		} else if (c > 0) {
			root.right = deleteAux(key, root.right);
		} else { 	//found a match
			
			if (root.left == null) { 
				treeSize--;
				return root.right; //only right subtree exists 
				
			} else if (root.right == null ) {
				treeSize--;
				return root.left; //only left subtree exists
				
			} else { 
				KeyValuePair<K,V> largestLeft = maxAux(root.left); //max of left subtree
				
				//copy desired values to the original; 
				root.key = largestLeft.getKey();
				root.value = largestLeft.getValue();
				

				//delete the max of left subtree so it ends up being a single child node problem again
				root.left = deleteAux(largestLeft.getKey(), root.left);
			}
		}
		return root;
	}

	
	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException("Callback is null");
		}
		processAux(callback, root); 
	}
	
	private void processAux(Callback<K, V> callback, Node root) {
		if (root == null) {
			return;
		}
		processAux(callback,root.left);
		callback.process(root.key, root.value);
		processAux(callback, root.right);
	}
	

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null  || comparator.compare(lowerLimit, upperLimit) > 0 ) {
			throw new IllegalArgumentException("null limit or lowerLim is greater than upperLim");
		}
		BinarySearchTree<K, V> sub = new BinarySearchTree<K,V>(comparator,maxEntries);
		subAux(lowerLimit, upperLimit, root, sub);
		return sub;
	}
	
	private BinarySearchTree<K, V> subAux(K ll, K ul, Node root, BinarySearchTree<K, V> sub) {
		if (root == null) {
			return null;
		}
		/*
		 * If root.key < lowerLimit: skip left, recurse right
		 * If root.key > upperLimit: skip right, recurse left
		 * Else:
		 * 	Recurse left
		 * 	Add current node
		 * 	Recurse right
		 */
		
		if (comparator.compare(root.key, ll) < 0) { 
			subAux(ll, ul, root.right, sub);
		} else if (comparator.compare(root.key, ul) > 0)  {
			subAux(ll, ul, root.left, sub);
		} else if (comparator.compare(root.key, ll) >= 0 && comparator.compare(root.key, ul) <= 0) {
			subAux(ll, ul, root.left, sub);
			try {
				sub.add(root.key, root.value);
			} catch (TreeIsFullException e) {
				throw new IllegalArgumentException("Tree is full.");
			}
			subAux(ll, ul, root.right, sub);
		}
		return sub;
	}

	public TreeSet<V> getLeavesValues() {
		TreeSet<V> leaves  = new TreeSet<>();
		leavesAux(root, leaves);
		return leaves;
		
	}
	
	private void leavesAux(Node root, TreeSet<V> leaves) {
		if (root == null) {
			return;
		}
		leavesAux(root.left, leaves);
		if (root.left == null && root.right == null) {
			leaves.add(root.value);
		}
		leavesAux(root.right, leaves);	
	}
}

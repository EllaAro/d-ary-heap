/**
 * D-Heap
 */

package ex2;
public class DHeap
{
	
    private int size, max_size, d;
    private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
               max_size = m_size;
			   d = m_d;
               array = new DHeap_Item[max_size];
               size = 0;
    }
	
	/**
	 * public int getSize()
	 * Returns the number of elements in the heap.
	 * @return size of the heap
	 */
	public int getSize() {
		return size;
	}
	
  /**
     * public int arrayToHeap(DHeap_Item[] array1)
     *@param DHeap_Item[] array
     *@return number of comparisons
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * precondition: array1.length() <= max_size
     * postcondition: isHeap()
     * 				  size = array.length()
     * Returns number of comparisons along the function run. 
     * 
	 */
    public int arrayToHeap(DHeap_Item[] array1) 
    {
    	fixPositions(array1);
    	this.array = new DHeap_Item[max_size];
    	System.arraycopy(array1, 0, this.array, 0, array1.length);
    	this.size = array1.length;
    	if (array1.length == 0) {
    		return 0;
    	}
    	int parent = DHeap.parent(size-1, this.d);
    	int numOfOperations = 0;
    	for (int i = parent; i>=0; i--) {
    		numOfOperations += heapifyDown(array[i]);
    	}
        return numOfOperations ; 
    }
    /** 
     *   private void fixPositions(DHeap_Item[] array1)
     *   the function receives a DHeap_Item array and corrects the position of each of the elements in the array by order.
     *   @param DHeap_Item[] array
     **/
    
    private void fixPositions(DHeap_Item[] array1) {
	for(int i = 0; i<array1.length; i++) {
		array1[i].setPos(i);
	}
	
}

	/**
     * private int heapifyDown(DHeap_Item item)
     * 
     * the function receives a DHeap_item variable and performs a heapify down operation on the variable.
     * preconidtion: the item exists in the array.
     * postcondition: the item in his right place after heapify down. 
     * @param DHeap_Item item
     * @return number of comperations
     */

    private int heapifyDown(DHeap_Item item) {
    	int numOfOperations = 0;
    	int k=1;
    	DHeap_Item minChild = null;
    	while (DHeap.child(item.getPos(), 1, this.d) < size) {
    		k = 1;
    		minChild = array[DHeap.child(item.getPos(), k, this.d)];
    		k++;
    		while (DHeap.child(item.getPos(), k, this.d)< this.size && (k<=d)) {
    			if (minChild.getKey() > array[DHeap.child(item.getPos(), k, this.d)].getKey()) {
    				minChild = array[DHeap.child(item.getPos(), k, this.d)];
    			}
    			numOfOperations++;
    			k++;
    		}
    		numOfOperations++;
    		if (item.getKey() <= minChild.getKey()) {
    			break;
    		}
    		else {
    			replace(item, minChild);
    			
    		}
		
	}
	return numOfOperations;
}
    /**
     * private void replace(DHeap_Item item, DHeap_Item other) 
     * the function receives two DHeap_item variables and replaces their position in the array
     * pre-condition: Two of the variables exist.
     * postcondition: the two items' positions are replaced by each other.
     * @param DHeap_Item  items
     * @param DHeap_Item other
     */

	private void replace(DHeap_Item item, DHeap_Item other) {
		int temp;
		this.array[item.getPos()] = other;
		this.array[other.getPos()] = item;
		temp = other.getPos();
		other.setPos(item.getPos());
		item.setPos(temp);
		
	}

	/**
     * public boolean isHeap()
     * uses isHeapRec(int i) for recursion
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or has size == 0.
     *   
     */
    public boolean isHeap() 
    {
 
        return  isHeapRec(0); // just for illustration - should be replaced by student code
    }


 private boolean isHeapRec(int i) {
		if (size==0) {
    		return true;
    	}
		boolean b = true;
		for (int k=1; k<=d; k++) {
			if (child(i,k,this.d) >= size) {
				return true;
			}
			else if (array[child(i,k,this.d)].getKey()< array[i].getKey()) {
					return false;
			}
			}
			 for (int k=1; k<=d; k++) {
				 b = b & isHeapRec(child(i,k,this.d));
		}
		return b;
	}

/**
     * public static int parent(i,d), child(i,k,d)
     * (2 methods)
     *
     * precondition: i >= 0, d >= 2, 1 <= k <= d
     *
     * The methods compute the index of the parent and the k-th child of 
     * vertex i in a complete D-ary tree stored in an array. 
     * Note that indices of arrays in Java start from 0.
     * if parent does not exist return -1.
     */
    public static int parent(int i, int d) { 
    	if(i==0) {
    		return -1; // parent not existed
    	}
    		
    	return (i-1)/d; 
    }
    public static int child (int i, int k, int d) 
    { 
    	return d*i +k;
    	} 
   

    /**
    * public int Insert(DHeap_Item item)
    *
	* Inserts the given item to the heap.
	* Returns number of comparisons during the insertion.
	*
    * precondition: item != null
    *               isHeap()
    *               size < max_size
    * 
    * postcondition: isHeap()
    */
    public int Insert(DHeap_Item item) 
    { 
    	array[size] = item;
    	item.setPos(size);
    	size++;
    	return heapifyUp(item);
    }
/**
 * the function receives a DHeap_item variable and performs a heapify up operation on the variable.
 * pre-condition: the item exists in the array.
 * postcondition: the item in his right place after heapify down.
 * @param item
 * @returns the number of comperations
 */
 private int heapifyUp(DHeap_Item item) {
	 int numOfOperations = 1;
	 while (item.getPos()>0 && item.getKey() < array[parent(item.getPos(),d)].getKey()) {
		 numOfOperations++;
		 replace(item,array[parent(item.getPos(),d)]);
	 }
		return numOfOperations;
	}

/**
    * public int Delete_Min()
    *
	* Deletes the minimum item in the heap.
	* Returns the number of comparisons made during the deletion.
    * 
	* precondition: size > 0
    *               isHeap()
    * 
    * postcondition: isHeap()
    */
    public int Delete_Min()
    {
    	replace(array[0],array[this.size-1]);
		this.size--;
		return heapifyDown(array[0]);
    }


    /**
     * public DHeap_Item Get_Min()
     *
	 * Returns the minimum item in the heap.
	 *
     * precondition: heapsize > 0
     *               isHeap()
     *		size > 0
     * 
     * postcondition: isHeap()
     */
    public DHeap_Item Get_Min()
    {
	return array[0];
    }
	
  /**
     * public int Decrease_Key(DHeap_Item item, int delta)
     *
	 * Decerases the key of the given item by delta.
	 * Returns number of comparisons made as a result of the decrease.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Decrease_Key(DHeap_Item item, int delta)
    {
    	item.setKey(item.getKey()-delta);
    	return heapifyUp(item);
    }
	
	  /**
     * public int Delete(DHeap_Item item)
     *
	 * Deletes the given item from the heap.
	 * Returns number of comparisons during the deletion.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Delete(DHeap_Item item)
    {
    	int numOfOperations = Decrease_Key(item, item.getKey()-this.Get_Min().getKey() +1);
    	numOfOperations+= Delete_Min();
	return numOfOperations;
    }
	
	/**
	* Sort the input array using heap-sort (build a heap, and 
	* perform n times: get-min, del-min).
	* Sorting should be done using the DHeap, name of the items is irrelevant.
	* 
	* Returns the number of comparisons performed.
	* 
	* postcondition: array1 is sorted 
	*/
	public static int DHeapSort(int[] array1, int d) {
		int numOfOperations = 0;
		DHeap_Item [] arr = new DHeap_Item[array1.length];
		for (int i=0; i<array1.length; i++) {
			arr[i] = new DHeap_Item("value", array1[i]);
			arr[i].setPos(i);
		}
		DHeap heap = new DHeap(d, arr.length);
		numOfOperations+= heap.arrayToHeap(arr);
		int index = 0;
		while (heap.getSize() !=0) {
			array1[index] = heap.Get_Min().getKey();
			index++;
			numOfOperations+= heap.Delete_Min();
		}
		
		
		return numOfOperations;
	}
}

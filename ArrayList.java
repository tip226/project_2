import java.util.Iterator;
import java.util.ListIterator;
/**
 * Class ArrayList
 * @author Houria Oudghiri
 * Date of creation: October 10, 2021
 * Date of last modification: November 6, 2022
 * Last modified by Tina Pham
 */
public class ArrayList<E> implements Cloneable, List<E>{
   // data members
   private E[] elements;
   private int size;
   // Constructors
   // O(1)
   public ArrayList() {
	   elements = (E[]) new Object[10];
	   size = 0;
   }
   public ArrayList(int capacity) {
     elements = (E[]) new Object[capacity];
     size = 0;
   }
   // Adding an item to the list (2 methods)
   // O(1) - O(n)
    public boolean add(E item) {
		return add(size, item);
    }
    public boolean add(int index, E item){
		if(index > size || index < 0)
			throw new ArrayIndexOutOfBoundsException();
		ensureCapacity();
		for(int i=size-1; i>=index; i--){
			elements[i+1] = elements[i];
        }
		elements[index] = item;
		size++;
		return true;
    }
    // Getter and Setter
    // O(1)
    public E get(int index) {
		  checkIndex(index);
		  return elements[index];
    }
    public E set(int index, E item) {
		  checkIndex(index);
		  E oldItem = elements[index];
		  elements[index] = item;
		  return oldItem;
    }
    // Size of the list
    // O(1)
    public int size() { 
      return size; 
    }
    // Clear the list
    // O(1)
    public void clear() { 
      size = 0; 
    }
    // Check if the list is empty
    // O(1)
    public boolean isEmpty() { 
      return (size == 0);
    }
    // Removing an object from the list
    // O(n)
    public boolean remove(Object o) {
      E item = (E) o;
      for(int i=0; i<size; i++)
		    if(elements[i].equals(item)){
            remove(i);
            return true;
        }
      return false;
    }
    // Removing the item at index from the list
    // O(n)
    public boolean remove(int index) {
      checkIndex(index);
      E item = elements[index];
      for(int i=index; i<size-1; i++)
			  elements[i] = elements[i+1];
      size--;
      return true;
    }
    // Shrink the list to size
    // O(n)
    public void trimToSize() {
		  if (size != elements.length) {
			    E[] newElements = (E[]) new Object[size];// capacity = size
			    for(int i=0; i<size; i++)
				    newElements[i] = elements[i];
			    elements = newElements;
		  }
    }
    // Grow the list if needed
    // O(n)
    private void ensureCapacity() {
	    if(size >= elements.length) {
          int newCap = (int) (elements.length * 1.5);
		      E[] newElements = (E[]) new Object[newCap];
		      for(int i=0; i<size; i++)
				    newElements[i] = elements[i];
		      elements = newElements;
	    }
    }
    // Check if the index is valid
    // O(1)
    private void checkIndex(int index){
		  if(index < 0 || index >= size)
			    throw new ArrayIndexOutOfBoundsException(
              "Index out of bounds. Must be between 0 and "+(size-1));
    }
    // toString() method
    // O(n)
    public String toString() {
		  String output = "[";
		  for(int i=0; i<size-1; i++)
			    output += elements[i] + " ";
		  output += elements[size-1] + "]";
		  return output;
    }
    // Iterator for the list
    // O(1)
    public Iterator<E> iterator(){
		  return new ArrayIterator();
    }
    // Inner class that implements the interface Iterator<E>
    // O(1)
    private class ArrayIterator implements Iterator<E>{
	    private int current = -1;

      // O(1)
	    public boolean hasNext() { 
        return current < size-1; 
      }

      // O(1)
	    public E next() { 
        return elements[++current]; 
      }
    }
    // Clone method (deep copy)
    // O(n)
    public Object clone(){
        ArrayList<E> copy = new ArrayList<>();
        for(int i=0; i<size; i++){
            copy.add(elements[i]);
        }
        return copy;
    }
    // Search for a value in the list
    // O(n)
    public boolean contains(Object o){
        E value = (E) o;
        Iterator<E> iter = iterator();
        while(iter.hasNext()){
            if(iter.next().equals(value)){
                return true;
            }
        }
        return false;
    }
    // Search for another list in the list
    // O(n)
    public boolean containsAll(List<E> otherList){
        for(int i = 0; i < otherList.size(); i++){
            if(!contains(otherList.get(i))){
                return false;
            }
        }
        return true;
    }
    // Adding another list to the list
    // O(n)
    public boolean addAll(List<E> otherList){
        int lastSize = size;
        for(int i = 0; i < otherList.size(); i++){
          add(otherList.get(i));
        }
        return lastSize != size;
    }
    // Removing elements of otherList
    // O(n)
    public boolean removeAll(List<E> otherList){
      int lastSize = size;
      for(int i = 0; i < otherList.size(); i++){
        if(this.contains(otherList.get(i))){
          remove(otherList.get(i));
        }
      }
      return lastSize != size;
    }
    // Retaining only the elements from otherList
    // O(n)
    public boolean retainAll(List<E> otherList){
      int lastSize = size;
      for(int i = 0; i < otherList.size(); i++){
          if(!this.contains(otherList.get(i))){
            remove(otherList.get(i));
          }
      }
      return lastSize != size;
    }
    // Method to get a list iterator for the list
    // O(1)
    public ListIterator<E> listIterator(){
      return new ArrayListIterator();
    }
    // Method to get a list iterator for the list at a specific position
    // O(1)
    public ListIterator<E> listIterator(int index){
      checkIndex(index);
      return new ArrayListIterator(index);
    }
    // Inner class that implements the interface ListIterator<E>
    // O(1)
    private class ArrayListIterator implements ListIterator<E>{
	    private int current;
      
      // O(1)
      public ArrayListIterator(){
        this.current = -1;
      }

      // O(1)
      public ArrayListIterator(int index){
        this.current = index;
      }

      // O(1)
	    public boolean hasNext() { 
        return current < size-1; 
      }

      // O(1)
	    public E next() { 
        return elements[++current]; 
      }

      // O(1)
      public boolean hasPrevious(){
        return current >= 0;
      }

      // O(1)
      public E previous(){
        return elements[current--];
      }

      // O(1)
      public void add(E value){
        throw new UnsupportedOperationException();
      }

      // O(1)
      public void remove(){
        throw new UnsupportedOperationException();
      }

      // O(1)
      public void set(E value){
        throw new UnsupportedOperationException();
      }

      // O(1)
      public int nextIndex(){
        throw new UnsupportedOperationException();
      }

      // O(1)
      public int previousIndex(){
        throw new UnsupportedOperationException();
      }
    }
    // Method to get the elements of the list as an array of type Object
    // O(1)
    public Object[] toArray(){
      E[] array = (E[]) new Object[size];
        for(int i = 0; i < size; i++){
            array[i] = elements[i];
        }
        return array;
    }
}
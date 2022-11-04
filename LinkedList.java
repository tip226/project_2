import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Class LinkedList
 * @author Houria Oudghiri
 * Date of creation: October 10, 2021
 * Date of last modification: October 22, 2022
 */
public class LinkedList<E> implements Cloneable, List<E>{
     // Data members
	private Node head, tail;
	int size;
    // Inner class Node
	private class Node{
		E value;
		Node next;
        Node previous;
        
        // O(1)
		Node(E initialValue){
			value = initialValue; 
            next = null;
            previous = null;
		}
	}
    // Constructor
    // O(1)
	public LinkedList() {
		head = tail = null;
		size = 0;
	}

    // Adding an item to the head of the list
    // O(1)
    public boolean addFirst(E item) {
		Node newNode = new Node(item);
		if(head == null) {
            head = tail = newNode; 
        }
		else {
            newNode.next = head;
            head.previous = newNode; // creating the link backward from the old head to newNode;
			head = newNode;
		}
		size++; 
        return true;
    }
    // Adding an item to the end of the list
    // O(1)
    public boolean addLast(E item) {
        Node newNode = new Node(item);
		if(head == null) { 
            head = tail = newNode; 
        }
		else { 
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
            }
		size++; 
        return true;
    }
    // Adding an item to the list
    // O(1)
    public boolean add(E item) {
		return addLast(item);
    }
    // Adding an item to the list at a specific index
    // O(n)
    public boolean add(int index, E item){
        Node temp = new Node(item);
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        } else if(index == 0){
            addFirst(item);
        } else if(index == size){
            add(item);
        } else{
            Node current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
            Node previous = current.previous;
            previous.next = temp;
            temp.previous = previous;
            temp.next = current;
            current.previous = temp;
            size++;
        }
        return true;

    }
    // Getting the item at the head of the list
    // O(1)
    public E getFirst() {
		if (head == null)
			throw new NoSuchElementException();
		return head.value;
    }
    // Getting the element at the end of the list
    // O(1)
    public E getLast() {
		if (head == null)
			throw new NoSuchElementException();
		return tail.value;
    }
    // Getting the item from the list
    // O(n)
    public E get(int index){
        Node current = head;
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        if(index == 0){
            return getFirst();
        } else if(index == (size - 1)){
            return getLast();
        } else {
            for(int i = 0; i < index - 1; i++){
                current = current.next;
            }
        }
        return current.value;
    }
    // Modify the value of an element in the list
    // O(n)
    public E set(int index, E value){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        Node current = head;
        for(int i = 0; i < index; i++){
            current = current.next;
        }
        E oldValue = current.value;
        current.value = value;
        return oldValue;
    }
    // Remove a value from the list
    // O(n)
    public boolean remove(Object o){
        Node toRemove = null;
        Node current = head;
        while(current != null){
            if(current.value.equals(o)){
                toRemove = current;
                break;
            }
            current = current.next;
        }
        if(toRemove == null){
            return false;
        }
        if(toRemove == head){
            removeFirst();
        } else if(toRemove == tail){
            removeLast();
        } else {
            Node next = toRemove.next;
            Node previous = toRemove.previous;
            next.previous = previous;
            previous.next = next;
        }
        return true;
    }
    // Remove an element from the list at a specific index
    // O(n)
    public boolean remove(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        else if(index == 0){
            return removeFirst();
        } else if(index == size - 1){
            return removeLast();
        } else {
            Node current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
            System.out.println(current.value);
            Node previous = current.previous;
            Node next = current.next;
            previous.next = current.next;
            next.previous = previous;
            size--;
            return true;
        }
    }
    // Removing the item at the head of the list
    // O(1)
    public boolean removeFirst() {
		if (head == null) 
            throw new NoSuchElementException();
		head = head.next;
		if(head == null)
            tail = null;
        else {
            head.previous = null;
        }
		size--; 
        return true;
    }
    // Removing the item at the end of the list
    // O(1)
    public boolean removeLast() {
		if (head == null) 
            throw new NoSuchElementException();
		if(size == 1) 
            return removeFirst();
		tail = tail.previous;
        tail.next = null;
		size--; 
        return true;
    }
    // Search for a value in the list
    // O(n)
    public boolean contains(Object o){
        E value = (E) o;
        Node current = head;
        while(current != null && !current.value.equals(value)){
            current = current.next;
        }
        if(current != null)
            return true;
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
        return true;
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
        return true;
    }
    // toString() method
    // O(n)
    public String toString() {
		String output = "[";
		Node node = head;
		while(node != null) {
			output += node.value + " ";
			node = node.next;
		}
		output += "]";
		return output;
    }
    // clear, check if empty, and size of the list
    // O(1)
    public void clear() {
        head = tail = null; 
        size = 0; 
    }
    // O(1)
    public boolean isEmpty() {
        return (size == 0);
    }
    // O(1)
    public int size() {
        return size; 
    } 
    // Implementing an iterator for the list
    // O(1)
    public Iterator<E> iterator(){
		  return new LinkedListIterator();
    }
    // Inner class to implement the interface Iterator
    private class LinkedListIterator implements Iterator<E>{
		private Node current = head;
        // O(1)
		public boolean hasNext() {
			return (current != null);
		}
        // O(1)
	    public E next() {
            if(current == null)
			  throw new NoSuchElementException();
			E value = current.value;
			current = current.next; 
            return value;
		}
    }
    // Method to get a list iterator for the list
    public ListIterator<E> listIterator(){
        return new LinkedListListIterator();
    }
    // Method to get a list iterator for the list at a specific position
    public ListIterator<E> listIterator(int index){
        if(index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return new LinkedListListIterator(index);
    }
    // Inner class that implements the interface ListIterator<E>
    private class LinkedListListIterator implements ListIterator<E>{
        private Node current;
        // O(1)
        public LinkedListListIterator(){
            this.current = head;
        }
        // O(n)
        public LinkedListListIterator(int index){
            this.current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
        }
        // O(1)
		public boolean hasNext() {
			return (current != null);
		}
        // O(1)
	    public E next() {
            if(current == null)
			  throw new NoSuchElementException();
			E value = current.value;
			current = current.next; 
            return value;
        }
        // O(1)
        public boolean hasPrevious(){
            if(current != null){
                return true;
            }
            return false;
        }
        // O(1)
        public E previous(){
            E value = null;
            if(current != null){
                value = current.value;
                current = current.previous;
                return value;
            }
            throw new NoSuchElementException();
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
    // O(n)
    public Object[] toArray(){
        E[] array = (E[]) new Object[size];
        Node current = head;
        for(int i = 0; current != null && i < size; i++){
            array[i] = current.value;
            current = current.next;
        }
        return array;
    }
    // Clone method (deep copy)
    // O(n)
    public Object clone(){
        LinkedList<E> copy = new LinkedList<>();
        for(Node node = head; node!=null; node = node.next){
            copy.add(node.value);
        }
        return copy;
    }
}
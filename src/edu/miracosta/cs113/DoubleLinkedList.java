package edu.miracosta.cs113;

/**
 * DoubleLinkedList.java : This class implements the methods from the List interface and create an inner class
 * implementing ListIterator interface.
 *
 *  @author Danny Lee
 *  @version 1.1
 */

import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.Comparator;
import java.util.Spliterator;


public class DoubleLinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Default constructor, initializes instance variables.
     */
    public DoubleLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * @return iterator
     */
    public Iterator<E> iterator(){
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public E next() {
                return null;
            }
        };
    }

    /**
     *Returns an array containing all of the elements in this list in proper sequence (from first to last element).
     * @return Object[0]
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array.
     * @param a
     * @param <T>
     * @return null
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     * @return DoublyLinkedListIterator an iterator.
     */
    public ListIterator<E> listIterator(){//Is it returning ListIterator<E> return type?
        //Isn't it returning a DoublyLinkedListIterator type object?
        return new DoublyLinkedListIterator();
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the specified position in the list.
     * @param index
     * @return listIterator an iterator.
     */
    public ListIterator<E> listIterator(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Invalid index" + index);
        }

        DoublyLinkedListIterator listIterator = new DoublyLinkedListIterator();

        for(int i = 0; i < index ; i++){
            listIterator.next();
        }
        return listIterator;
    }

    /**
     * Returns a view of the portion of this list between the specified fromIndex, inclusive, and toIndex, exclusive.
     * @param fromIndex
     * @param toIndex
     * @return null
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Creates a Spliterator over the elements in this list.
     * @return null
     */
    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     * @param element
     * @return true
     */
    public boolean add(E element){
        //System.out.println("Adding new elements!");
        Node<E>  newNode = new Node<>(element);
        if(isEmpty()){
            head = newNode;
        }else{
            tail.next = newNode;
        }
        tail = newNode;
        size++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * @param index
     * @param element
     */
    public void add(int index, E element){

        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        Node<E> nodeBeforeIndex = null;
        Node<E> nodeAtIndex = head;

        //Find the node at index
        for(int i = 1; i <= index; i++){
            nodeBeforeIndex = nodeAtIndex;
            nodeAtIndex = nodeAtIndex.next;
        }

        //Add the node between nodeBeforeIndex and nodeAtIndex
        Node<E> newNode = new Node<>(element);
        newNode.next = nodeAtIndex;

        if(nodeBeforeIndex != null){
            nodeBeforeIndex.next = newNode;
        }else{
            //When it is the first node, update head
            head = newNode;
        }

        if(index == size){
            tail = newNode;
        }

        //Update size
        size ++;

    }

    /**
     * Removes all of the elements from this list (optional operation).
     */
    public void clear(){
        head = null;
        size = 0;
    }

    /**
     * Compares the specified object with this list for equality.
     * @param otherObject
     * @return true or false
     */
    public boolean equals(Object otherObject){
        if(otherObject == null){
            return false;
        }else if(getClass() != otherObject.getClass()){
            return false;
        }else{
            DoubleLinkedList<E> otherList = (DoubleLinkedList<E>)otherObject;
            if(size() != otherList.size()){
                return false;
            }
            Node<E> position = head;
            Node<E> otherPosition = otherList.head;

            while(position != null){
                if(!(position.data.equals(otherPosition.data))){
                    return false;
                }
                position = position.next;
                otherPosition = otherPosition.next;
            }
            return true; //no mismatch found
        }

    }

    /**
     * Returns true if this list contains the specified element.
     * @param o
     * @return true or false
     */
    public boolean contains(Object o){
        int index = indexOf(o);
        return index != -1;
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index
     * @return E data of the node at the index.
     */
    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Invalid index!");
        }
        Node<E> targetNode = head;
        for(int i = 1; i <= index ; i++){
            targetNode = targetNode.next;
        }
        return targetNode.data;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element.
     * @param o
     * @return int value representing the index.
     */
    public int indexOf(Object o){
        Node<E> position = head;
        // int count = 0;
        // Node<E> givenNode = new Node<>();

        for(int i = 0; i < size; i++){
            if(o == null){//if o is null
                if(position.data == null){
                    return i;
                }
            }else{//if o is not null
                if(position.data.equals(o)){
                    return i;
                }
            }
            position = position.next;
        }

        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in this list, or -1 if this list does not contain the element.
     * @param o
     * @return int value of the last occurrence of the element.
     */
    public int lastIndexOf(Object o){
        Node<E> position = head;
        int lastIndex = -1;

        for(int i = 0; i < size; i++){
            if (o == null) {
                if(position.data == null){
                    lastIndex = i;
                    position = position.next;
                }
            }else{
                if(position.data.equals(o)){
                    lastIndex = i;
                }
            }
            position = position.next;
        }

        return lastIndex;
    }

    /**
     * Returns true if this list contains no elements.
     * @return true or false
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Removes the element at the specified position in this list (optional operation).
     * @param index
     * @return E data of the node removed
     */
    public E remove(int index){
        Node<E> nodeToRemove = head;
        Node<E> nodeBefore = null;

        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }

        //Find the corresponding node
        for(int i = 0; i < index; i++){
            nodeBefore = nodeToRemove;
            nodeToRemove = nodeToRemove.next;
        }

        if(nodeBefore == null) {//when removing first node
            head = nodeToRemove.next;
            nodeToRemove.next.previous = head;
        }else if(nodeToRemove == tail){//when removing last node
            tail = nodeBefore;
            tail.next = null;
        }else{//in the middle
            nodeToRemove.next.previous = nodeBefore;
            nodeBefore.next = nodeToRemove.next;
        }

        size--;
        return nodeToRemove.data;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present (optional operation).
     * @param o
     * @return true or false
     */
    public boolean remove(Object o){
        int indexOfFirstOccurrence = 0;
        indexOfFirstOccurrence = indexOf(o);
        if(indexOfFirstOccurrence == -1){
            return false;
        }
        remove(indexOfFirstOccurrence);
        size--;
        return true;
    }

//    public boolean remove(double doubleValue){
//
//        int indexOfFirstOccurrence = 0;
//        indexOfFirstOccurrence = indexOf((Double)doubleValue);
//
//        if(indexOfFirstOccurrence == -1){
//            return false;
//        }
//        remove(indexOfFirstOccurrence);
//        size--;
//        return true;
//    }

    /**
     * Returns true if this list contains all the specified elements.
     * @param c
     * @return false
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    /**
     * Adds all the specified elements to the list
     * @param c
     * @return false
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    /**
     *  Adds all the specified elements to the list in the given index.
     * @param index
     * @param c
     * @return false
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    /**
     * Removes all the specified elements in the list
     * @param c
     * @return false
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    /**
     * Retain all the specified elements.
     * @param c
     * @return false
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    /**
     * Replaces with the specified elements.
     * @param operator
     */
    @Override
    public void replaceAll(UnaryOperator<E> operator) {

    }

    /**
     * Sorts the list.
     * @param c
     */
    @Override
    public void sort(Comparator<? super E> c) {

    }

    /**
     * Replaces the element at the specified position in this list with the specified element (optional operation).
     * @param index
     * @param element
     * @return E
     */
    public E set(int index, E element){

        Node<E> previousIndexNode = null;
        Node<E> atIndexNode = head;
        if(index < 0 || index >= size){
            throw new ArrayIndexOutOfBoundsException();
        }
        //Find the corresponding node
        for(int i = 1; i <= index; i++){
            previousIndexNode = atIndexNode;
            atIndexNode = atIndexNode.next;
        }

        Node<E> nodeReplacing = new Node<>(element);
        nodeReplacing.next = atIndexNode.next;
        nodeReplacing.previous = atIndexNode.previous;
        atIndexNode.next.previous = nodeReplacing;

        if(previousIndexNode != null){
            previousIndexNode.next = nodeReplacing;
        }else{
            //first node
            head = nodeReplacing;
        }

        if(index == size){
            tail = nodeReplacing;
        }

//        Node<E> nodeReplaced = head;
//        Node<E> nodeReplacing = new Node<>(element);
//        nodeReplacing = head;
//        if(index < 0 || index >= size){
//            throw new ArrayIndexOutOfBoundsException();
//        }
//        for(int i = 0; i < index; i++){
//            nodeReplaced = nodeReplacing.next;
//        }
//
//        nodeReplaced.previous.next = nodeReplacing;
//        nodeReplaced.next.previous = nodeReplacing;
//
//        nodeReplacing.next = nodeReplaced.next;
//        nodeReplacing.previous = nodeReplaced.previous;

        return nodeReplacing.data;
    }

    /**
     * Returns the number of elements in this list.
     * @return size
     */
    public int size(){
        size = 0;
        Node<E> position = head;
        while(position != null){
            size++;
            position = position.next;

        }
        return size;
    }

    /**
     * Returns the data of the elements in the list.
     * @return stringToReturn the information of the elements in the list.
     */
    public String toString(){
        Node<E> position = head;
        String stringToReturn = "";

        if(position == null){
            stringToReturn = "[]";
            return stringToReturn;
        }else{
            while(position != null){
                if(position.next != null) {
                    stringToReturn += position.data + ", ";
                }else{
                    stringToReturn += position.data;
                }

                position = position.next;
            }
        }

        return "[" + stringToReturn + "]";

    }

    /**
     *Inner class Node
     */
    private static class Node<E>{
        private E data;
        private Node<E> next;
        private Node<E> previous;

        /**
         * Default constructor
         */
        public Node(){
            data =null;
            next = null;
            previous = null;
        }

        /**
         * Constructor with one parameter
         * @param data
         */
        public Node(E data){
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        /**
         * copy constructor
         * @param anotherNode
         */
        public Node(Node<E> anotherNode){
            data = anotherNode.data;
            next = anotherNode.next;
            previous = anotherNode.previous;
        }

        /**
         * full constructor
         * @param data
         * @param next
         * @param previous
         */
        public Node(E data, Node<E> next, Node<E> previous){
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Returns the data of the node.
         * @return string value of the data
         */
        public String toString(){
            if(data == null){
                return "no data";
            }else{
                return(data + "");
            }
        }

        /**
         * Compares the specified object with this Node for equality.
         * @param anObject
         * @return
         */
        public boolean equals(Object anObject){
            if(anObject == null || getClass() != anObject.getClass()){
                return false;
            }
            Node aNode = (Node)anObject;
            return (data.equals(aNode.data));
        }
    }//end of inner class Node



    /**
     *Inner class DoublyLinkedListIterator this class implements ListIterator interface.
     */
    private class DoublyLinkedListIterator implements ListIterator<E>{

        private Node<E> nextData;
        private Node<E> lastDataReturned;
        private int index = 0;

        /**
         * Default constructor
         */
        public DoublyLinkedListIterator(){
            nextData = head;
            lastDataReturned = null;
            index = 0;
        }

        /**
         * Inserts the specified element into the list (optional operation).
         * @param newData
         */
        public void add(E newData){

            Node<E> newNode = new Node<E>(newData);
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;


//            if(nextData == head){//Adding to the head of the list
//                Node<E> newNode = new Node<E>();
//                newNode.next = nextData;
//                nextData.previous = newNode;
//                head = newNode;
//            }else if(nextData == null){//Adding to the tail of the list
//                Node<E> newNode = new Node<E>();
//                tail.next = newNode;
//                newNode.previous = tail;
//                tail = newNode;
//            }else{
//                Node<E> newNode = new Node<E>();
//                // Node<E> previousNode = new Node(nextData.previous.data, nextData);
//                newNode.previous = nextData.previous;
//                nextData.previous.next = newNode;
//                newNode.next = nextData;
//                nextData.previous = newNode;
//            }
            size ++;
            index ++;

        }

        /**
         * Returns true if this list iterator has more elements when traversing the list in the forward direction.
         * @return true or false
         */
        public boolean hasNext(){
//            if(index < size()){
//                return true;
//            }
            return nextData != null;
        }

        /**
         * Returns true if this list iterator has more elements when traversing the list in the reverse direction.
         * @return true or false
         */
        public boolean hasPrevious(){
            return index > 0;
        }

        /**
         * Returns the next element in the list and advances the cursor position.
         * @return E next element
         */
        public E next(){
            if(!hasNext()){
                throw new NoSuchElementException("The list does not have a next value!");
            }
            E dataToReturn = (E)nextData.data;
            if(lastDataReturned == null){//very first 'next' call
                lastDataReturned = head;
                nextData = nextData.next;
                if(nextData != null){
                    nextData.previous = lastDataReturned;
                }
                lastDataReturned.previous = null;
                lastDataReturned.next = nextData;
                index ++;
                return dataToReturn;
            }

            if(nextData.next != null){
                lastDataReturned = nextData;
                lastDataReturned.previous = nextData.previous;
                nextData = nextData.next;
                nextData.previous = lastDataReturned;
                lastDataReturned.next = nextData;
            }else if(nextData.next == null){
                lastDataReturned = nextData;
                lastDataReturned.previous = nextData.previous;
                nextData = null;
                lastDataReturned.next = nextData;
            }



            index ++;
            return dataToReturn;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to next().
         * @return index + 1
         */
        public int nextIndex(){//It doesn't matter if the list is empty?/index is at last index?
            return index + 1;
        }

        /**
         * Returns the previous element in the list and moves the cursor position backwards.
         * @return E previous data
         */
        public E previous(){
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }


            nextData = lastDataReturned;
            System.out.println("nextData2 : " + nextData);

            lastDataReturned = nextData.previous;
            System.out.println("lastDataReturned : " + lastDataReturned);

            index --;
            return nextData.data;
        }

        /**
         * Returns the index of the element that would be returned by a subsequent call to previous().
         * @return index - 1
         */
        public int previousIndex(){//It doesn't matter if the list is empty?/index is at first index?
            return index - 1;
        }

        /**
         * Removes from the list the last element that was returned by next() or previous() (optional operation).
         */
        public void remove(){
            Node<E> nodeBefore;
            Node<E> nodeAfter;



            if(lastDataReturned == null){
                throw new IllegalStateException();
            }
            if(lastDataReturned.next == null && nextData == null){//when 'next' was previously called and iterator is past the last element.
                System.out.println("Removing the element after 'next' was called");
                nodeBefore = lastDataReturned.previous;
                nodeAfter = nextData;
                if(nodeAfter != null){  //when removing the tail(last element).
                    nodeAfter.previous = nodeBefore;
                }else if(nodeAfter == null){
                    tail = nodeBefore;
                }
                if(nodeBefore != null){ //when removing the head(first element).
                    nodeBefore.next = nodeAfter;
                }
            }
            else if(lastDataReturned.next.equals(nextData)){//when 'next' was previously called
                System.out.println("Removing the element after 'next' was called");
                nodeBefore = lastDataReturned.previous;
                nodeAfter = nextData;
                nodeAfter.previous = nodeBefore;
                if(nodeBefore != null){ //when removing the head(first element).
                    nodeBefore.next = nodeAfter;
                }
                //lastDataReturned.previous.next = nextData;

            }else if(lastDataReturned.equals(nextData)){//when 'previous' was called
                nodeBefore = nextData.previous;
                nodeAfter = nextData.next;
                if(nodeAfter != null){  //when removing the tail(last element).
                    nodeAfter.previous = nodeBefore;
                }
                nodeBefore.next = nodeAfter;
            }
            index--;
        }

        /**
         * Replaces the last element returned by next() or previous() with the specified element (optional operation).
         * @param newData
         */
        public void set(E newData){
            if(lastDataReturned == null){
                throw new IllegalStateException();
            }
            lastDataReturned.data = newData;
        }


    }//end of inner class DoublyLinkedListIterator

}//end of outer class DoubleLinkedList<E>
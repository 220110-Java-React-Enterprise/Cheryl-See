import java.util.Iterator;


/**
 * Simple doubly linkedlist implementation, extending custom list interface.
 * Also implements Iterable interface. (commented out)
 * @param <T>
 */
public class CustomLinkedList<T> implements CustomListInterface<T>, Iterable<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;


    /**
     * Adds an object to the end of the linked list
     * @param t object to be added to the list
     */
    @Override
    public void add(T t) {
        Node<T> newNode = new Node<T>(t);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    /**
     * Adds an object to the linked list at the specified index, splicing into place
     * and effectively shifting all further objects
     * @param index position to add object
     * @param t object to be added
     */
    @Override
    public void add(T t, int index) throws IndexOutOfBoundsException {
        // handle if empty list

        //Implement this method

        Node nodeBeingChecked = head;
        for(int i=0; i<size; i++) {
            //if it matches, insert new node
            if (i == index) {
                Node newNode = new Node(t, nodeBeingChecked, nodeBeingChecked.prev);
                newNode.prev.next = newNode;
                nodeBeingChecked.prev = newNode;
                size++;
                return;
            }
            nodeBeingChecked = nodeBeingChecked.next;
        }

        // Cases to handle
        // Adding to head (index 0)
        // Adding to tail
        // Adding to middle
        // Adding to empty list
        // Adding to index that is out of bounds (too large, neg number)
        // Attempting to add incorrect type (stretch goal)

        // Figure out how to throw exception more gracefully
        // it should not reach this point if it has been inserted
        throw new IndexOutOfBoundsException("Requested index is out of bounds.");
    }

    /**
     * gets the object found at provided index position
     * @param index location of the object to get
     * @return object found at index position
     */
    @Override
    public T get(int index) {
        Node nodeBeingChecked = head;
        for(int i=0; i<size; i++) {
            if (i == index) {
                return (T)nodeBeingChecked.obj;
                //return nodeBeingChecked.obj;
                //return nodeBeingChecked.getData();
            }
            nodeBeingChecked = nodeBeingChecked.next;
        }
        // index not found (ex. larger than size)
        throw new IndexOutOfBoundsException("Requested index is not valid.");
    }

    /**
     * Clears the linked list by setting head and tail to null.
     *
     */
    @Override
    public void clear() {
        //Implement this method
        // Set head to null
        // Set tail to null
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Searches linked list for an object using Object.equals() to compare
     * returns the index of the first matching object found. -1 if not found.
     * @param t the object to match
     * @return index of the first matching object found. -1 if not found
     */
    @Override
    public int contains(T t) {
        //Implement this method
        // Iterate through
        Node nodeBeingChecked = head;
        for(int i=0; i<size; i++) {
            //if it matches, insert new node
            if (nodeBeingChecked.obj.equals(t)) {
                return i;
            }
            nodeBeingChecked = nodeBeingChecked.next;
        }
        // Item was not found in the linked list
        return -1;
    }

    /**
     * removes an object from linked list and splices the two resulting separate lists
     * together.
     * @param index the location of the object to be removed.
     */
    @Override
    public void remove(int index) {
        //Implement this method
        Node nodeBeingChecked = head;
        // Just adding this in case there is only 1 element
        if ((this.size == 1) && (index == 0)) {
            clear();
            return;
        }

        for(int i=0; i<size; i++) {
            if (i == index) {
                // Different handling is required if this is the head/tail
                if (nodeBeingChecked == head) {
                    Node nextNode = nodeBeingChecked.next;
                    nextNode.prev = null;
                    this.head = nextNode;
                }
                else {
                    if (nodeBeingChecked == tail) {
                        Node previousNode = nodeBeingChecked.prev;
                        previousNode.next = null;
                        this.tail = previousNode;
                    } else {
                        nodeBeingChecked.prev.next = nodeBeingChecked.next;
                        nodeBeingChecked.next.prev = nodeBeingChecked.prev;
                    }
                }
                // Node found and removed
                size--;
                return;
            }// Node not found, iterate again
            nodeBeingChecked = nodeBeingChecked.next;
        }

        // Cases to handle
        // Adding to head (index 0)
        // Adding to tail
        // Adding to middle
        // Adding to empty list
        // Adding to index that is out of bounds (too large, neg number)
        // Attempting to add incorrect type (stretch goal)


        // Figure out how to throw exception more gracefully
        // it should not reach this point if it has been removed
        //throw new IndexOutOfBoundsException("Requested index is out of bounds.");
        // is this even needed? if it didn't exist, it's been removed?
    }


    /**
     * returns the size of the linked list
     * @return size of linked list
     */
    @Override
    public int size() {
        //Implement this method
        return this.size;
    }

    /**
     * iterator implementation
     * @return returns an iterator object to traverse the linked list
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> cursor = head;

            /**
             * checks if the linked list has another node, testing if the cursor points to a node
             * or if it is null
             * @return true if the cursor points to a node, false if the cursor node reference is null
             */
            @Override
            public boolean hasNext() {
                if (cursor == null){
                    return false;
                }
                return true;
            }

            /**
             * returns the node the cursor points to, then advances the cursor to the next node
             * @return the object at the location of the cursor
             */
            @Override
            public T next() {
                T t = cursor.obj;
                cursor = cursor.next;
                return t;
            }
        };
    }


    /**
     * Private node class contains a reference to object of list type, a reference to the next node, and
     * to the previous node.
     * @param <T>
     */
    private class Node<T> {
        Node<T> next;
        Node<T> prev;
        T obj;

        // Trying to bypass a potential error - getter
        public T getData() {
            return this.obj;
        }

        /**
         * empty constructor creates an empty node
         */
        Node() {

        }

        /**
         * creates a node and stores an object by reference
         * @param t the stored object
         */
        Node(T t) {
            obj = t;
        }

        /**
         * creates a node which stores an object by reference and has a reference to another node
         * @param t object to be stored
         * @param next next node in list
         */
        Node(T t, Node<T> next) {
            this(t);
            this.next = next;
        }

        /**
         * creates a node which stores an object by reference and has refrences to two nodes,
         * previous and next in the list
         * @param t the object to be stored
         * @param next reference to next node in list
         * @param prev reference to previous node in list
         */
        Node(T t, Node<T> next, Node<T> prev) {
            this(t, next);
            this.prev = prev;
        }
    }


    /**
     * You can use this method to test the list, but it may be a good idea to add more tests
     * to make sure everything works properly. This test will print all the strings given in
     * the parameter list.
     * @param greeting - a string with a greeting message - first item added to list
     * @param goodbye - a string with a closing message - last item added to list
     * @param args - variable arguments, any number of strings which will be added to list
     */
    public void testMethod(String greeting, String goodbye, String... args) {
        System.out.println(greeting);
        for(int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
        System.out.println(goodbye);
    }

    /**
     * Another method that can be used to test the list, give any number of ints in the parameter list
     * and it will return the sum of those integers.
     * @param nums - variable arguments - any number of integer values
     * @return - the sum of the integers given in the parameter list
     */
    public int testSum(Integer... nums) {
        int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        return sum;
    }

    // This should only be used with test data
    public void testPrintAllElements() {
        for(int i=0; i<this.size; i++) {
            System.out.println(get(i));
        }
    }

    // A sequence of tests to test .remove()
    public void testRemoval() {
        /*
        for(int i=1; i<11; i++) {
            list.add(i);
        }
        list.remove(4);
        System.out.println("#4 removed");
        System.out.println("size -1: " + list.size());
        list.remove(0);
        System.out.println("#0 removed");
        System.out.println("size -1 (head removed): " + list.size());

        System.out.println("\nFinal result:");
        for(int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }
        */

    }


}
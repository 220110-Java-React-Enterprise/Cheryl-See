public class Test {

    // Testing adding elements
    public void testLinkedListAdd() {
        CustomLinkedList<Integer> list = new CustomLinkedList<>();
        // Populate list
        for(int i=1; i<11; i++) {
            list.add(i);
        }

        // Add to middle:
        System.out.println("Adding to middle (index 5): ");
        list.add(200, 5);
        list.testPrintAllElements();

        // Cases to handle
        // Adding to head (index 0)
        // Adding to tail
        // Adding to middle
        // Adding to empty list
        // Adding to index that is out of bounds (too large, neg number)
        // Attempting to add incorrect type (stretch goal)
    }
}


/*

                case 4: {
                    // TODO: This option shouldn't exist.  Only for debugging linked list.
                    System.out.println("Testing linked list.");
                    CustomLinkedList<Integer> list = new CustomLinkedList<>();
                    // Populate list

                for(int i=1; i<11; i++) {
                    list.add(i);
                }

                    list.add(1);
                            list.testPrintAllElements();
                            System.out.println("original size: " + list.size());
                            list.remove(0);
                            System.out.println("new size: " + list.size());
                            list.testPrintAllElements();
                            break;
                            } // Remove this
 */

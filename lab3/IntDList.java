/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Linda Deng (9/1/2021)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * @param index index of node to return,
     *          where index = 0 returns the first node,
     *          index = 1 returns the second node,
     *          index = -1 returns the last node,
     *          index = -2 returns the second to last node, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *          and -size <= index <= -1 for negative indices.
     * @return The node at index index
     */
    private DNode getNode(int index) {
        // TODO: Implement this method and return correct node
        return null;
    }

    /**
     * @param index index of element to return,
     *          where index = 0 returns the first element,
     *          index = 1 returns the second element,
     *          index = -1 returns the last element,
     *          index = -2 returns the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *          and -size <= index <= -1 for negative indices.
     * @return The integer value at index index
     */
    public int get(int index) {
        // TODO: Implement this method (Hint: use `getNode`)
        return 0;
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        // TODO: Implement this method
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        // TODO: Implement this method
    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position, etc.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices
     *              and -(size+1) <= index <= -1 for negative indices.
     */
    public void insertAtIndex(int d, int index) {
        // TODO: Implement this method
    }

    /**
     * Removes the first item in the IntDList and returns it.
     * Assume `deleteFront` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     * Assume `deleteBack` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * @param index index of element to be deleted,
     *          where index = 0 returns the first element,
     *          index = 1 will delete the second element,
     *          index = -1 will delete the last element,
     *          index = -2 will delete the second to last element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices
     *              and -size <= index <= -1 for negative indices.
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        // TODO: Implement this method to return correct value
        return null;
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}

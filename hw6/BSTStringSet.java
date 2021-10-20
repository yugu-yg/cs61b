import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Yu
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    public Node helperPut(String s, Node p) {
        if (p == null) {
            return new Node(s);
        } else {
            if (s.compareTo(p.s) < 0) {
                p.left = helperPut(s, p.left);
            }
            if (s.compareTo(p.s) > 0) {
                p.right = helperPut(s, p.right);
            }
        }
        return p;
    }

    public void put(String s) {
        _root = helperPut(s, _root);
    }

    public boolean helperContains (String s, Node root) {
        if (root == null) {
            return false;
        } else if (s.compareTo(root.s) == 0) {
            return true;
        } else if (s.compareTo(root.s) > 0) {
            return helperContains(s, root.right);
        } else if (s.compareTo(root.s) < 0) {
            return helperContains(s, root.left);
        }
        return false;
    }
    @Override
    public boolean contains(String s) {
        return helperContains(s, _root);
    }

    @Override
    public List<String> asList() {
        List<String> list = new ArrayList<>();
        BSTIterator iter = new BSTIterator(_root);
        while (iter.hasNext()) {
            list.add(iter.next());
        }
        return list;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }


    public Iterator<String> iterator(String low, String high) {
        return null;  // FIXME: PART B (OPTIONAL)
    }


    /** Root node of the tree. */
    private Node _root;
}

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class BSTStringSetTest  {

    @Test
    public void testPut() {
        BSTStringSet bst = new BSTStringSet();
        bst.put("a");
        bst.put("tst");
        assertEquals(bst.iterator().next(), "a");
    }

    @Test
    public void testContains() {
        BSTStringSet bst = new BSTStringSet();
        bst.put("a");
        bst.put("tst");
        assertTrue(bst.contains("a"));
        assertTrue(bst.contains("tst"));

    }

    @Test
    public void testasList() {
        BSTStringSet bst = new BSTStringSet();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("tst");
        bst.put("a");
        bst.put("tst");
        assertEquals(list, bst.asList());
        System.out.println(bst.asList());
    }
}

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class IsBalancedTest {

    private val CMP_NATURAL_ORDER = { i1: Int, i2: Int -> i1.compareTo(i2)}
    @Test
    fun isBalanced_empty_trees() {
        val tree = emptyBST()
        assertTrue(isBalanced(tree))
    }

    @Test
    fun isBalanced_singleNodeBST() {
        var tree = singleNodeBST(1)
        assertTrue(isBalanced(tree))
        tree = add(tree, 0, CMP_NATURAL_ORDER)
        assertEquals(2, sizeTree(tree))
        assertTrue(isBalanced(tree))
        tree = add(tree, 2, CMP_NATURAL_ORDER)
        assertEquals(3, sizeTree(tree))
        assertTrue(isBalanced(tree))
    }

    @Test
    fun isBalanced_populatedBST() {
        var tree = populatedBST(intArrayOf(10, 4, 1, 8, 5, 6, 7, 0, 2, 3, 9, 12, 11))
        assertFalse(isBalanced(tree))
        tree = remove(tree, 3, CMP_NATURAL_ORDER)
        tree = remove(tree, 6, CMP_NATURAL_ORDER)
        tree = remove(tree, 7, CMP_NATURAL_ORDER)
        assertEquals(10, sizeTree(tree))
        assertTrue(isBalanced(tree))
    }

    @Test
    fun isBalanced_completeBST() {
        var tree = completeBST()
        assertTrue(isBalanced(tree))
        tree = remove(tree, 1, CMP_NATURAL_ORDER)
        tree = remove(tree, 5, CMP_NATURAL_ORDER)
        tree = remove(tree, 9, CMP_NATURAL_ORDER)
        tree = remove(tree, 13, CMP_NATURAL_ORDER)
        assertEquals(11, sizeTree(tree))
        assertTrue(isBalanced(tree))
        tree = remove(tree, 3, CMP_NATURAL_ORDER)
        tree = remove(tree, 7, CMP_NATURAL_ORDER)
        assertEquals(9, sizeTree(tree))
        assertTrue(isBalanced(tree))
        tree = remove(tree, 6, CMP_NATURAL_ORDER)
        tree = remove(tree, 2, CMP_NATURAL_ORDER)
        assertEquals(7, sizeTree(tree))
        assertFalse(isBalanced(tree))
    }



}






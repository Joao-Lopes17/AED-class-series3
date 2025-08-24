import java.lang.Math.abs
import kotlin.math.max

data class Node<E>(var item: E, var left: Node<E>?, var right: Node<E>?)

fun <E> contains(root: Node<E>?, min: E, max: E, cmp: (e1: E, e2: E) -> Int): Boolean {
    if (root == null) return false
    if (cmp(max, root.item) >= 0 && cmp(root.item, min) >= 0) return true
    val l = contains(root.left, min, max, cmp)
    val r = contains(root.right, min, max, cmp)
    return (l || r)
}

fun <E> isBalanced(root: Node<E>?): Boolean {
    return isCompleteSum(root) != -2

}

fun createBSTFromRange(start: Int, end: Int): Node<Int>? {
    if (end < start) return null
    val mid = (start + end) / 2
    val root = Node(mid, null, null)
    root.left = createBSTFromRange(start, mid - 1)
    root.right = createBSTFromRange(mid + 1, end)
    return root
}

fun <E> isCompleteSum(root: Node<E>?): Int {
    if (root == null) return -1
    val l = isCompleteSum(root.left)
    val r = isCompleteSum(root.right)
    if (l == -2 || r == -2) return -2
    return if (l != r && abs(l - r) != 1) -2 else 1 + max(l, r)
}



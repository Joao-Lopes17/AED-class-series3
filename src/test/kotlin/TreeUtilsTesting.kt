import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.math.max

/**Create BSTs using Ints**/
val cmp={ i1: Int, i2: Int -> i1 - i2 }

fun emptyBST(): Node<Int>?=null

fun singleNodeBST(i:Int): Node<Int>?=add(null,i,cmp)

fun <E> minimum(root: Node<E>): Node<E> {
    if (root.left == null) return root
    else return minimum(root.left!!)
}

fun <E> maximum(root: Node<E>): Node<E> {
    if (root.right == null) return root
    else return minimum(root.right!!)
}

fun populatedBST(array:IntArray): Node<Int>? {
    var tree: Node<Int>? = null
    for (i in array) tree = add(tree, i, cmp)
    return tree
}

/**Generic BST Functions**/
fun <E> height(root: Node<E>?): Int {
     return if (root == null)  -1
     else 1 + max(height(root.left),height(root.right))
}

fun <E> sizeTree(root: Node<E>?): Int {
    return if (root == null) 0 else 1 + sizeTree(root.left) + sizeTree(root.right)
}

fun <E> search(root: Node<E>?, key: E, cmp: Comparator<E>): Node<E>? {
    if (root == null || cmp.compare(key, root.item) == 0) return root
    else if (cmp.compare(key, root.item) < 0) return search(root.left, key, cmp)
    else return search(root.right, key, cmp)
}

fun <E> add(root: Node<E>?, e: E, cmp: Comparator<E>): Node<E> {
    var root = root
    if (root == null) root = Node(e,null,null)
    else if (cmp.compare(e, root.item) < 0) root.left = add(root.left, e, cmp)
    else root.right = add(root.right, e, cmp)
    return root
}

fun <E> remove(root: Node<E>?, e: E?, cmp: Comparator<E>): Node<E>? {
    var root = root
    if (root == null) return root
    else {
        if (cmp.compare(e, root.item) < 0) root.left = remove<E>(root.left, e, cmp)
        else if (cmp.compare(e, root.item) > 0) root.right = remove<E>(root.right, e, cmp)
        else { // found, delete it
            if (root.left == null) root = root.right
            else if (root.right == null) root = root.left
            else {
                val y: Node<E> = minimum(root.right!!)
                root.item = y.item
                root.right = remove(root.right, y.item, cmp)
            }
        }
        return root
    }
}

fun <E> assertEqual(root1: Node<E>?, root2: Node<E>?) {
    if (root1 != null && root2 != null) {
        assertEquals(root1.item, root2.item)
        assertEqual(root1.left, root2.left)
        assertEqual(root1.right, root2.right)
    }
    assertEquals(root1, root2)
}


//prefix path
fun <E> preorder(root: Node<E>?) {
    if (root != null) {
        print("$root.item ")
        preorder(root.left)
        preorder(root.right)
    }
}
//infix path
fun <E> inorder(root: Node<E>?) {
    if (root != null) {
        inorder(root.left)
        print("$root.item")
        inorder(root.right)
    }
}
//suffix path
fun <E> postorder(root: Node<E>?) {
    if (root != null) {
        postorder(root.left)
        postorder(root.right)
        print("$root.item")
    }
}

fun completeBST(): Node<Int>? {
    val nodes = intArrayOf(8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15)
    var tree: Node<Int>? = null
    for (i in nodes.indices) tree = add(tree, nodes[i], cmp)
    return tree
}















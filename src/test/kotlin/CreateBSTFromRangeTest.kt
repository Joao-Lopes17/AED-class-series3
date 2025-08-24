import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateBSTFromRangeTest {

    val cmp = { i1: Int, i2: Int -> i1 - i2 }

    @Test
    fun creatBSTFromRange_empty_trees() {
        var tree = emptyBST()
        val resultTree = createBSTFromRange(0, -1)
        assertEqual(tree,resultTree)
        tree = singleNodeBST(0)

    }

     @Test
     fun createBSTFromRange_singleton_trees(){
         var tree =populatedBST(intArrayOf(1))
         val resultTree = createBSTFromRange(1, 1)
         assertEqual(tree,resultTree)
     }

    @Test
    fun createBSTFromRange_someElements1_trees(){
        var tree =populatedBST(intArrayOf(5,2,7,1,3,4,6,8,9))
        val resultTree = createBSTFromRange(1, 9)
        assertEqual(tree,resultTree)
    }





}



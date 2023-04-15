package bst

import bst.nodes.AVLNode
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.max

class AvlTreeTest {
    private fun<K: Comparable<K>, V> isAvl(node: AVLNode<K, V>?): Boolean {
        if (node == null) return true
        return isAvl(node.left) &&
               isAvl(node.right) &&
               (subtreeHeight(node.left) - subtreeHeight(node.right) in -1..1)
    }
    private fun <K: Comparable<K>, V> subtreeHeight(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return max(subtreeHeight(node.left), subtreeHeight(node.right)) + 1
    }
    private fun<K: Comparable<K>, V> countNodes(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return countNodes(node.left) + countNodes(node.right) + 1
    }

    private lateinit var tree: AVLTree<Int, Int>
    private val values = IntArray(1000) { it + 1 }


    @BeforeEach
    fun initializeObjects() {
        tree = AVLTree()
        values.shuffle()
    }

    @Test
    fun `Empty tree`() {
        assertNull(tree.rootNode)
        assertEquals(0, countNodes(tree.rootNode))
        assertTrue(isAvl(tree.rootNode))
    }

    @Nested
    inner class `Insertion tests` {
        @Test
        fun `Single insertion`() {
            tree.insert(1, 1)
            assertEquals(1, tree.rootNode?.key)
            assertEquals(1, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `Bigger insertion`() {
            tree.insert(2, 2)
            tree.insert(3, 3)
            tree.insert(1, 1)

            assertEquals(2, tree.rootNode?.key)
            assertEquals(3, tree.rootNode?.right?.key)
            assertEquals(1, tree.rootNode?.left?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `Same key inserted twice`() {
            tree.insert(1, 1)
            tree.insert(1, 2)
            assertEquals(1, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }
    }
}


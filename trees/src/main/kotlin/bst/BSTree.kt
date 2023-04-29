package bst
import bst.nodes.BSTNode

class BSTree<K: Comparable<K>, V>(val key: K? = null, val value: V? = null): AbstractBST<K, V, BSTNode<K, V>>() {
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)
    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }

    fun setName(treeName: String){
        this.treeName = treeName
    }
    fun clear(){
        this.rootNode = null
    }

    fun getRoot(): BSTNode<K, V>?{
        return this.rootNode
    }
}

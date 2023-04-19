package bst

import bst.nodes.BSTNode
<<<<<<< HEAD
<<<<<<< HEAD
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter
=======
=======

>>>>>>> 001bf13 (Add x, y coordinates to nodes)
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.session.query
>>>>>>> 2dd00ac (db: initial commit for neo4j)

<<<<<<< HEAD
class BSTree<K: Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null, val treeName:String = ""): AbstractBST<K, V, BSTNode<K, V>>() {
=======
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter


class BSTree<K: Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null): AbstractBST<K, V, BSTNode<K, V>>() {
>>>>>>> 001bf13 (Add x, y coordinates to nodes)
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)

    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }

    //save_node is method for check recursive addition elements to db
    fun save_node(node: BSTNode<K, V>?, parentNode:BSTNode<K, V>? = null){
        node?.let {
            save_node(node.left, node)
            save_node(node.right, node)
            println("parent node: ${parentNode?.key}: ${parentNode?.value} saving node: ${node.key}: ${node.value}")
        }
    }

    fun saveTreeToJson(){
        val gson = Gson()
        val writer = FileWriter("${this.treeName}.json")
        val treeToSave = this
//        val person = Person("John", 30)
        gson.toJson(treeToSave, writer)
        writer.close()
    }

    fun readFromJson(treeName: String): BSTree<*, *>{
        val gson = Gson()
        val reader = FileReader(treeName)
        val tree = gson.fromJson(reader, BSTree::class.java)
        reader.close()
        return tree
    }
}


fun main(){
<<<<<<< HEAD
    val test_data = BSTree("121", "dgs", "tree_1")
=======
    val test_data = BSTree("121", "dgs")
>>>>>>> 001bf13 (Add x, y coordinates to nodes)
    test_data.insert("110", "dafad")
    test_data.insert("118", "adfaf")
    test_data.insert("124", "fggsg")
    test_data.remove("124")
    test_data.saveTreeToJson()
    test_data.readFromJson("tree_1.json")
//    test_data.save_node(test_data.rootNode)
//    val json = Gson().toJson(test_data)
//    println(json)
//    val ret_obj = Gson().fromJson<BSTree<Comparable<Any>, Any>>(json, BSTree::class.java)

}

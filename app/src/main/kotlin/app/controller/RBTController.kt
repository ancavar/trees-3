package app.controller

import bst.RedBlackTree
import bst.nodes.RBTNode
import bst.db.controllers.JsonController
import bst.db.controllers.Neo4jController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Label
import javafx.scene.control.Tooltip
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType
import tornadofx.Controller
import kotlin.math.min

class RBTController: Controller() {
    fun isNumeric(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    fun insertNode(tree: RedBlackTree<Int, String>, treePane: Pane, key: Int, value: String) {
        tree.insert(key, value)
        drawTree(tree, treePane)
    }

    fun clearTree(tree: RedBlackTree<Int, String>, treePane: Pane) {
        tree.clear()
        treePane.children.clear()
    }

    //make here not null check
    fun drawTree(tree: RedBlackTree<Int, String>, treePane: Pane) {
        treePane.children.clear()
        val root = tree.getRoot()
        if (root != null) {
            drawNode(root, treePane, treePane.width / 2.0, 50.0, treePane.width / 4.0)
        }
    }

    private fun drawNode(node: RBTNode<Int, String>, treePane: Pane, x: Double, y: Double, offsetX: Double) {
        val circleRadius = 20.0
        val circle = Circle(circleRadius)
        circle.centerX = x
        circle.centerY = y
        circle.fill = Color.WHITE
        circle.stroke = Color.BLACK

        val nodeText = Text(node.key.toString())
        nodeText.x = circle.centerX
        nodeText.y = circle.centerY
        nodeText.boundsType = TextBoundsType.VISUAL

        val scale = min(circleRadius * 1.3 / nodeText.boundsInLocal.width, circleRadius * 1.3 / nodeText.boundsInLocal.height)
        nodeText.scaleX = scale
        nodeText.scaleY = scale

        nodeText.x -= nodeText.boundsInLocal.width / 2
        nodeText.y += nodeText.boundsInLocal.height / 3

        val tooltip = Tooltip(node.value)
        Tooltip.install(circle, tooltip)
        Tooltip.install(nodeText, tooltip)

        treePane.children.addAll(circle, nodeText)

        if (node.left != null) {
            val leftX = x - offsetX
            val leftY = y + 50
            val leftLine = Line(x, y + circleRadius, leftX, leftY - circleRadius)
            treePane.children.add(leftLine)
            drawNode(node.left!!, treePane, leftX, leftY, offsetX / 2.0)
        }

        if (node.right != null) {
            val rightX = x + offsetX
            val rightY = y + 50
            val rightLine = Line(x, y + circleRadius, rightX, rightY - circleRadius)
            treePane.children.add(rightLine)
            drawNode(node.right!!, treePane, rightX, rightY, offsetX / 2.0)
        }
    }

    fun getTreesList(): ObservableList<String>? {
        val controller = Neo4jController()
        val treeNames = controller.getNames()
        val values = FXCollections.observableArrayList<String>()
        treeNames.forEach {
            values.add(it)
        }
        return values
    }
    fun getTreeFromNeo4j(name: String): RedBlackTree<Int, String>? {
        val controller = Neo4jController()
        return controller.getTree(name)
    }

    fun deleteTreeFromDB(name: String) {
        Neo4jController().run {
            removeTree(name)
        }
    }
    fun saveTree(tree: RedBlackTree<Int, String>, treeName: String) {
        val controller = Neo4jController()
        controller.saveTree(tree, treeName)
    }
    fun deleteNode(value: Int, tree: RedBlackTree<Int, String>, treePane: Pane){
        tree.remove(value)
        drawTree(tree, treePane)
    }
}

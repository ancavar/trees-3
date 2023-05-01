package app.view.treeView
import app.controller.AVLController
import bst.AVLTree
import bst.BSTree
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.layout.Pane
import tornadofx.*
class AVLTreeView: View() {
    private val controller: AVLController by inject()
    private var tree = AVLTree<Int, String>()
    private val treePane = Pane()
    private val key = SimpleStringProperty()
    private val value = SimpleStringProperty()
    private var trees = controller.getTreesList()
    private var selectedItem: String? = ""
    private val treeName = SimpleStringProperty()
    private val valueFotDeletion = SimpleStringProperty()
    override val root = vbox {
        hbox {
            val availableTrees = combobox<String> {
                this@AVLTreeView.trees?.let { items.addAll(it) }
                selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                    this@AVLTreeView.selectedItem = newValue
                }
            }

            button("Select") {
                action {
                    println("Selected item: $selectedItem")
                    val loadedTree = selectedItem?.let { controller.getTreeFromJson(it) }
                    if (loadedTree != null) {
                        tree = loadedTree
                        controller.drawTree(tree, treePane)
                    }
                }
            }
            button("Delete") {
                action {
                    selectedItem?.let {
                        controller.clearTree(tree, treePane)
                        controller.deleteTreeFromDB(it)
                    }
                    availableTrees.items.remove(selectedItem)

                    println("Item deleted: $selectedItem")
                }
            }

            button("Clear") {
                action {
                    controller.clearTree(tree, treePane)
                }
            }
            form {
                fieldset {
                    field("Key input") {
                        textfield(key)
                    }
                    field("Value input") {
                        textfield(value)
                    }

                    button("Add Node") {
                        action {
                            if (key.value != null && value.value != null && controller.isNumeric(key.value)) {
                                controller.insertNode(tree, treePane, key.value.toInt(), value.value)
                            } else {
                                alert(type = Alert.AlertType.ERROR, header = "Insertion Error")
                            }
                            key.value = ""
                            value.value = ""
                        }
                    }
                    field("Value input"){
                        textfield(valueFotDeletion)
                    }
                    button("Delete node"){
                        action {
                            if (controller.isNumeric(valueFotDeletion.value)){
                                controller.deleteNode(valueFotDeletion.value.toInt(), tree, treePane)
                            }
                            else{
                                alert(type = Alert.AlertType.ERROR, header = "Deletion Error")
                            }
                        }
                    }

                    field("Input tree name") {
                        textfield(treeName)
                    }
                    button("Save tree") {
                        action {
                            if (tree.getRoot() != null) {
//                                tree.treeName = treeName.value
                                controller.saveTree(tree, treeName.value)
                                if (!availableTrees.items.contains(treeName.value)) {
                                    availableTrees.items.add(treeName.value)
                                }
                            }
                            else{
                                alert(type = Alert.AlertType.ERROR, header = "Can not save tree with empty root")

                            }
                        }
                    }
                }
            }
        }
        button("Binary Search Tree") {
            action {
                replaceWith(BinarySearchTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
        button("Red Black Tree") {
            action {
                replaceWith(RedBlackTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
        this += treePane
        treePane.apply {
            minWidth = 600.0
            minHeight = 400.0
            style = "-fx-border-color: black;"
        }
    }
}
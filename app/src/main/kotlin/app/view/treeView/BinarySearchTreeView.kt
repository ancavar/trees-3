package app.view.treeView
import tornadofx.*
import app.controller.BSTController
import app.view.AdditionErrorFragment
import bst.BSTree
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.Pane
import javafx.stage.StageStyle
//import tornadofx.Stylesheet.Companion.selected

class BinarySearchTreeView : View() {
    private val controller: BSTController by inject()
    private val tree = BSTree<Int, String>()
    private val treePane = Pane()
    val key = SimpleStringProperty()
    val value = SimpleStringProperty()
    val trees = controller.getTreesList()
    val selected = stringProperty("")
    override val root = vbox {
        hbox {
            button("Clear") {
                action { controller.clearTree(tree, treePane) }
            }
            listview(trees).onUserSelect{
                selected.set(it)
                log.info { selected.value }
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
                            if ( key.value != null && value.value != null){
                                if (controller.isNumeric(key.value)){
                                    controller.insertNode(tree, treePane, key.value.toInt(), value.value)
                                }
                                else{
                                    find<AdditionErrorFragment>().openModal(stageStyle = StageStyle.UTILITY)
                                }
                            }
                            else{
                                find<AdditionErrorFragment>().openModal(stageStyle = StageStyle.UTILITY)
                            }
                            key.value = ""
                            value.value = ""
                        }
                    }
                }
            }
        }
        button("AVL Tree") {
            action {
                replaceWith(AVLTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
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

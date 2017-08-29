import org.w3c.dom.HTMLDivElement
import uibrowser.BrowserContainerUI
import uicontroller.EditorPanelController
import uicontroller.UIFieldMeta
import uicontroller.bind
import uimodel.UIState
import kotlin.browser.document

/**
 * Created by richard on 04/03/2017.
 */


fun main(args: Array<String>) {
//  (document.getElementById("foo") as? HTMLDivElement)?.let {
//    println("Found Foo")
//    val containerUI = BrowserContainerUI(
//        element = it
//    )
//    val barF = containerUI.textFieldEditorUI("bar")
//
//    barF.updateListener = {
//      println("Ha")
//      UIState(value = it)
//    }
//  }

  val uiMetas = arrayOf(UIFieldMeta("firstName"))

  (document.getElementById("bar") as? HTMLDivElement)?.let {
    val containerUI = BrowserContainerUI(
        element = it
    )
    val editorPanelController = EditorPanelController (
        bind(uiMetas, containerUI)
    )

    containerUI.actionUI("save") {
      println("Saving")
      println(editorPanelController.data())
    }


  }


}
import httprpc.get
import org.w3c.dom.HTMLDivElement
import uibrowser.ActionUI
import uicontroller.EditorPanelController
import uicontroller.UIFieldMeta
import uicontroller.bind
import uimodel.ContainerUI
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

  get(
      url = "https://api.ipify.org?format=json",
      headers = listOf(
       )
  ).then(
      onFulfilled = { println(it) },
      onRejected = { println(it.message) }
  )


  val uiMetas = arrayOf(UIFieldMeta("firstName"), UIFieldMeta("lastName"))

  (document.getElementById("EntryPoint") as? HTMLDivElement)?.let {
    try {
      val appUI = uibrowser.AppUI(
          element = it
      )

      val editorPanelController = EditorPanelController(
          bind(uiMetas, appUI.rootContainer.ui<ContainerUI>("person-data", ContainerUI::class))
      )

      appUI.rootContainer.ui<ActionUI>("save-action", ActionUI::class).onFired = {
        println("Saving")
        println(editorPanelController.data())
      }


    } catch (e: Exception) {
      println(e.message)
    }
  }
}
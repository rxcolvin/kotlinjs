package uibrowser

import org.w3c.dom.*
import org.w3c.dom.events.Event
import uimodel.*
import kotlin.dom.clear

/**
 * Created by richard on 08/08/2017.
 */


class BrowserAppUI : AppUI {

}

class BrowserWindowUI : WindowUI {
  override val mainContainer: ContainerUI
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}


fun divToMap(div: HTMLDivElement): Map<String, HTMLElement> =
    div.children.asList().filter {
      it.hasAttribute("data-fieldName") && it is HTMLElement
    }.map {
      Pair(it.getAttribute("data-fieldName") as String, it as HTMLElement)
    }.toMap()


class BrowserContainerUI(
    private val element: HTMLDivElement
) : ContainerUI {
  val elementMap = divToMap(element)

  private fun fieldEditorElementFor(name: String) =
    elementMap.get(name) as? HTMLInputElement


  override fun textFieldEditorUI(name: String): TextFieldEditorUI = BrowserTextFieldEditorUI(
      name = name,
      element = fieldEditorElementFor(name)
  )

  override fun longFieldEditorUI(name: String): LongFieldEditorUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fieldListUI(name: String): ListEditorUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun containerUI(name: String): ContainerUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun actionUI(name: String): ActionUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun labelUI(name: String): LabelUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun actionGroupUI(name: String): ActionGroupUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

class BrowserTextFieldEditorUI(
    override val name: String,
    val element: HTMLInputElement?
) : TextFieldEditorUI {
  override var format: String = "*"
  override var uiState: UIState<String> = UIState()
  override var updateListener: (String) -> UIState<String> = { UIState<String>(value = it) }

  init {
    element?.let {
      it.type = "text"
      updateState(uiState)
      it.onchange = this::onChange
    }

  }

  private fun onChange(event: Event) {
    val newState = updateListener(element!!.value)
    updateState(newState)
  }

  private fun updateState(uiState: UIState<String>) {
    element?.let {
      it.readOnly = uiState.isReadOnly
      it.value = uiState.value ?: ""
      if (uiState.hasFocus) it.focus()
    }
  }

}

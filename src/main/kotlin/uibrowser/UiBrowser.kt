package uibrowser

import org.w3c.dom.*
import org.w3c.dom.events.Event
import uimodel.*

/**
 * Created by richard on 08/08/2017.
 */


class AppUI : uimodel.AppUI {

}

class WindowUI : uimodel.WindowUI {
  override val mainContainer: ContainerUI
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}


fun divToMap(div: HTMLDivElement): Map<String, HTMLElement> =
    div.children.asList().filter {
      it.hasAttribute("data-fieldName") && it is HTMLElement
    }.map {
      Pair(it.getAttribute("data-fieldName") as String, it as HTMLElement)
    }.map {
      println(it)
      it
    }.toMap()


class BrowserContainerUI(
    element: HTMLDivElement
) : uimodel.ContainerUI {
  val elementMap = divToMap(element)

  private fun fieldEditorElementFor(name: String) =
      elementMap.get(name) as HTMLInputElement

  private fun actionElementFor(name: String): HTMLButtonElement {
    val ret = elementMap.get(name)
    println(ret)
    return ret as HTMLButtonElement
  }


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

  override fun actionUI(
      name: String,
      onFired: () -> Unit
  ): ActionUI = ActionUI(
      name = name,
      onFired = onFired,
      element = actionElementFor(name)
  )

  override fun labelUI(name: String): LabelUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun actionGroupUI(name: String): ActionGroupUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

class BrowserTextFieldEditorUI(
    override val name: String,
    val element: HTMLInputElement
) : uimodel.TextFieldEditorUI {
  override var format: String = "*"
  override var uiState: UIState<String> = UIState()
  override var updateListener: (String) -> UIState<String> = { UIState<String>(value = it) }
  override var value: String
    get() = element.value
    set(value) {
      element.value = value
    }

  init {
    element.let {
      it.type = "text"
      updateState(uiState)
      it.onchange = this::onChange
    }
  }

  private fun onChange(event: Event) {
    val newState = updateListener(element.value)
    updateState(newState)
  }

  private fun updateState(uiState: UIState<String>) {
    element.let {
      it.readOnly = uiState.isReadOnly
      it.value = uiState.value ?: ""
      if (uiState.hasFocus) it.focus()
    }
  }
}

class ActionUI(
    override val name: String,
    val element: HTMLButtonElement,
    override val onFired: () -> Unit
) : uimodel.ActionUI {
  init {
    println("Click Set")
    element.onclick = { _ -> println("onFired");onFired() }
  }

  override val label: String
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
  override var enabled: Boolean
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    set(value) {}

}


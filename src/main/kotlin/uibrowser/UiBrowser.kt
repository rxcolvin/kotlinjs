package uibrowser

import org.w3c.dom.*
import org.w3c.dom.events.Event
import uimodel.*

/**
 * Created by richard on 08/08/2017.
 */


abstract class UI : uimodel.UI {}

class AppUI : uimodel.AppUI {

}

class WindowUI : uimodel.WindowUI {
  override val mainContainer: ContainerUI
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}


fun divToMap(div: HTMLDivElement): Map<String, UI> =
    //TODO sort out nested divs
    div.children.asList().filter {
      it.hasAttribute("data-name") && it is HTMLElement
    }.map {
      val name = it.getAttribute("data-name")
      Pair(name as String, elementToUI(name, it as HTMLElement))
    }.map {
      println(it)
      it
    }.toMap()

fun elementToUI(
    name: String,
    element: HTMLElement
) = when (element) {
  is HTMLLabelElement -> LabelUI(name, element)
  fsdfsdfsdf //Container, INput, Action
  else -> throw RuntimeException()
}


class ContainerUI(
    override val name: String,
    element: HTMLDivElement
) : uimodel.ContainerUI, UI() {
  val uiMap = divToMap(element)

  override fun textFieldEditorUI(name: String): TextFieldEditorUI = BrowserTextFieldEditorUI(
      name = name,
      element = uiMap.get(name) as HTMLInputElement
  )

  override fun longFieldEditorUI(name: String): LongFieldEditorUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun fieldListUI(name: String): ListEditorUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun containerUI(name: String): uimodel.ContainerUI =
      ContainerUI(
          name = name,
          element = uiMap.get(name) as HTMLDivElement
      )

  override fun actionUI(
      name: String,
      onFired: () -> Unit
  ): ActionUI = ActionUI(
      name = name,
      onFired = onFired,
      element = uiMap.get(name) as HTMLButtonElement
  )

  override fun labelUI(name: String): uimodel.LabelUI {
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
) : uimodel.ActionUI, UI() {
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

class LabelUI(
    override val name: String,
    val element: HTMLLabelElement

) : uimodel.LabelUI, UI() {
  override var label: String
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    set(value) {}

}


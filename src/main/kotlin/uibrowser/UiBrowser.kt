package uibrowser

import org.w3c.dom.*
import org.w3c.dom.events.Event
import uimodel.*
import kotlin.reflect.KClass

/**
 * Created by richard on 08/08/2017.
 */


abstract class UI : uimodel.UI {}

class AppUI(element: HTMLDivElement) : uimodel.AppUI {
  override val rootContainer = ContainerUI("rootContainer", element)
}

class WindowUI : uimodel.WindowUI {
  override val mainContainer: ContainerUI
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}


fun elementToUI(element: HTMLElement): UI? =
    bindings
        .map {
          if (it.first(element)) it.second(element) else null
        }
        .filter {
          it != null
        }
        .firstOrNull()

fun buildMap(element: HTMLDivElement): Map<String, UI> = buildUIs(element, ArrayList()).associateBy { it.name }

fun buildUIs(element: HTMLElement, list: MutableList<UI>) : MutableList<UI> {
  element.children.asList().forEach {
    if (it is HTMLElement) {
      val ui = elementToUI(it)
      if (ui != null) {
        list.add(ui)
        println("Added ${ui.name} of ${ui::class}")
      } else {
        buildUIs(it, list)
      }
    }
  }
  return list
}

class ContainerUI(
    override val name: String,
    element: HTMLDivElement
) : uimodel.ContainerUI, UI() {

  private val uiMap: Map<String, uimodel.UI> = buildMap(element)

  override fun <T : uimodel.UI> ui(
      name: String,
      klass: KClass<T>
  ): T {
    val ret = uiMap.getOrElse(name) {
      throw RuntimeException("uiComponent $name not found in container ${this.name}")
    }

    if (klass.isInstance(ret)) {
      return ret as T
    }

    throw RuntimeException(
        """uiComponent $name in container ${this.name} has wrong type. Requested ${klass} got ${ret::class}""")
  }
}

class StringEditorUI(
    override val name: String,
    val element: HTMLInputElement
) : uimodel.StringEditorUI, UI() {
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
    val element: HTMLButtonElement
) : uimodel.ActionUI, UI() {
  override var onFired: () -> Unit = {}

  init {
    element.onclick = { onFired() }
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
    get() = element.title
    set(value) {
      element.title = value
    }

}

fun ss(test: (HTMLElement) -> Boolean, f: (HTMLElement) -> UI) = Pair(test, f)

fun HTMLElement.isType(type: String) =
    this.hasAttribute("data-type") && this.getAttribute("data-type") == type

fun HTMLElement.hasName() =
    this.hasAttribute("data-name")

fun HTMLElement.name() =
    this.getAttribute("data-name") ?: ""


val bindings: Array<Pair<(HTMLElement) -> Boolean, (HTMLElement) -> UI>> =
    arrayOf(
        ss(
            { it is HTMLDivElement && it.isType("Container") && it.hasName() },
            { ContainerUI(name = it.name(), element = it as HTMLDivElement) }
        ),
        ss(
            { it is HTMLInputElement && it.isType("StringEditor") && it.hasName() },
            { StringEditorUI(name = it.name(), element = it as HTMLInputElement) }
        ),
        ss(
            { it is HTMLLabelElement && it.isType("Label") && it.hasName() },
            { LabelUI(name = it.name(), element = it as HTMLLabelElement) }
        ),
        ss(
            { it is HTMLButtonElement && it.isType("Action") && it.hasName() },
            { ActionUI(name = it.name(), element = it as HTMLButtonElement) }
        )


    )


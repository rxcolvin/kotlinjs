package uicontroller

import uimodel.*




interface FieldController {
  val name: String
  fun setValue(value: Any?)
  fun getValue() : Any?
  val isDirty : Boolean
}

class StringEditorController(
    var fieldEditorUI: StringEditorUI
) : FieldController {
  override val name: String
    get() = fieldEditorUI.name

  override fun setValue(
      value: Any?
  ) {
    fieldEditorUI.value = value as String
  }

  override fun getValue() = fieldEditorUI.value

  override val isDirty: Boolean
    get() = false
}



class UIFieldMeta(
    val name: String
) {

}

class EditorPanelController
(
    val fieldControllers: Array<FieldController>
) {

  val isDirty : Boolean
  get() =
    fieldControllers.map { it.isDirty }.firstOrNull() ?: false


  fun data(map: Map<String, Any?>) {
    fieldControllers.forEach {
      it.setValue(map[it.name])
    }
  }

  fun data() = fieldControllers.map {
    Pair(it.name, it.getValue())
  }.toMap()

}



class EntityController(
    val fieldContainer: ContainerUI,
    val actionContainer: ActionGroupUI,
    val onSave: () -> Unit
) {

}

class ActionController(
    val actionUI: ActionUI
) {

}


class LoginController {

}


fun bind(uiFieldMetas: Array<UIFieldMeta>, containerUI: ContainerUI) : Array<FieldController> =
    uiFieldMetas.map {bind(it, containerUI)}.toTypedArray()

fun bind(
    uiFieldMeta: UIFieldMeta,
    containerUI: ContainerUI
) : FieldController {
  return StringEditorController(containerUI.ui(uiFieldMeta.name, StringEditorUI::class))
}
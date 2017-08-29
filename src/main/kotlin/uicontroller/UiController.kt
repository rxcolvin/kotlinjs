package uicontroller

import uimodel.*



class EntityController (
    val fieldContainer: ContainerUI,
    val actionContainer: ActionGroupUI,
    val onSave: () -> Unit
) {

}

class ActionController(
    val actionUI: ActionUI
) {

}

open class FieldController

class TextFieldEditorController(
    var fieldEditorUI: TextFieldEditorUI
) : FieldController() {

}
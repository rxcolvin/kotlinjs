/**
 * Created by richard.colvin on 17/03/2017.
 */
package uimodel

import kotlin.reflect.KClass

class Image {

}

class Date {

}

class Time {

}

class Timestamp {

}

interface UI {
  val name: String
}


interface FieldEditorUI<T> : UI {
  var value: T
  var uiState: UIState<T>
  var updateListener: (String) -> UIState<T>
}

interface StringEditorUI : FieldEditorUI<String> {
  var format: String
}

interface LongFieldEditorUI : FieldEditorUI<Long> {
  var range: Pair<Long, Long>
  var style: String //Slider, List
}

interface DateFieldEditorUI : FieldEditorUI<Date> {
  var range: Pair<Date, Date>
  var style: String //Slider, List
}

interface ListEditorUI : UI {
  var uiState: UIState<Int>
  var onUpdate: (String) -> UIState<Int>
  var style: String //Dropdown, radio
  var values: List<String>
}

interface LabelUI : UI {
  var label: String
}

// Placeholder
interface ImageIUI : UI {
  var image: Image
}


interface ActionUI : UI {
  val label: String
  var onFired: () -> Unit
  var enabled: Boolean
}


interface ActionGroupUI : UI {
  val actions: MutableList<ActionUI>
}

sealed class ValidState(val msg: String = "") {
  object OK : ValidState()
  class Warning(msg: String) : ValidState(msg)
  class Error(msg: String) : ValidState(msg)
}

data class UIState<T>(
    val value: T? = null,
    val isReadOnly: Boolean = false,
    val validState: ValidState = ValidState.OK,
    val hasFocus: Boolean = false,
    val isDirty: Boolean  = false
)

/**
 *
 */
interface ContainerUI : UI {
   fun <T:UI> ui(name:String, klass: KClass<T>) : T
}

interface WindowUI {
  val mainContainer: ContainerUI
}

/**
 *
 */
interface AppUI {
  val rootContainer:ContainerUI

}







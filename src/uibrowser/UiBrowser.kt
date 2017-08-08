package uibrowser

import org.w3c.dom.Element
import org.w3c.dom.HTMLDivElement
import uimodel.*

/**
 * Created by richard on 08/08/2017.
 */



class BrowserApp : AppUI {

}

class BrowserWindow : WindowUI {
  override val mainContainer: ContainerUI
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}


fun divToMap(div: HTMLDivElement) : Map<String, Element> {
  
}

class BrowserContainer(
    private val element :  HTMLDivElement
) : ContainerUI {
  val elementMap =
  override fun textFieldEditorUI(name: String): TextFieldEditorUI {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

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

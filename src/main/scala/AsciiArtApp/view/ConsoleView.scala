package AsciiArtApp.view

import AsciiArtApp.controller.ConsoleController
import AsciiArtApp.input.Input

class ConsoleView(input: Input, controller: ConsoleController) {
  private val config = input.getImageData

  def start(): Unit = {

    controller.load( getInput() )
    val filters = getFilters()
    val table = getTable()
    controller.transform(filters, table)
    val modes = getOutputMode()
    controller.exportAscii(modes)
  }



  def getFilters(): Map[String, String] = {
    var result: Map[String, String] = Map()
    val filter1 = "rotate"
    val filter2 = "flip"
    val filter3 = "scale"
    val filter4 = "brightness"
    val filter5 = "font"
    if (config.hasParameter(filter1)) {
      result += (filter1 -> config.getParameter(filter1).get)
    }
    if (config.hasParameter(filter2)) {
      result += (filter2 -> config.getParameter(filter2).get)
    }
    if (config.hasParameter(filter3)) {
      result += (filter3 -> config.getParameter(filter3).get)
    }
    if (config.hasParameter(filter4)) {
      result += (filter4 -> config.getParameter(filter4).get)
    }
    if (config.hasParameter(filter5)) {
      result += (filter5 -> config.getParameter(filter5).get)
    }
    result
  }

  def getTable(): String = {
    config.getParameter("table").get
  }

  def getInput(): String = {
      config.getParameter("input").get
  }
  def getOutputMode(): Map[String, String] = {
    var result: Map[String, String] = Map()
    val modeFile = "outputFile"
    val modeStd = "outputStd"
    if (config.hasParameter(modeFile)) {
      result += modeFile -> config.getParameter(modeFile).get
    }
    if (config.hasParameter(modeStd)) {
      result += modeStd -> config.getParameter(modeStd).get
    }
    result
  }
}

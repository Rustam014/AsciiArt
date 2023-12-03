package AsciiArtApp.input


class Config {
  private var parameters: Map[String, String] = Map()

  def setParameter(key: String, value: String): Unit = parameters += (key -> value)

  def getParameter(key: String): Option[String] = parameters.get(key)

  def hasParameter(key: String): Boolean = parameters.contains(key)
}


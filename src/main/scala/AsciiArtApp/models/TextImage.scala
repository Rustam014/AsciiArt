package AsciiArtApp.models

class TextImage(override val width: Int, override val height: Int) extends Image[Char] {
  protected var data: Array[Array[Char]] = Array.fill(height, width)(' ')

  def getPixel(x: Int, y: Int): Char = data(y)(x)
  def setPixel(x: Int, y: Int, value: Char): Unit = data(y)(x) = value
  def getData(): Array[Array[Char]] = data
}
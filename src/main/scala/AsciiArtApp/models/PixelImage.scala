package AsciiArtApp.models

class PixelImage(override val width: Int, override val height: Int) extends Image[Byte] {
  protected var data: Array[Array[Byte]] = Array.fill(height, width)(0)

  def getPixel(x: Int, y: Int): Byte = data(y)(x)
  def setPixel(x: Int, y: Int, value: Byte): Unit = data(y)(x) = value
}
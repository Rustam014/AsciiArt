package AsciiArtApp.table

import AsciiArtApp.models.{ImageBase, PixelImage, TextImage}

trait TransformTable[T, D] {
  protected var tableData: String
  def applyTransformation(image: T): D
}

trait ImageTransformationTable[T <: ImageBase, D <: ImageBase] extends TransformTable[T, D] {

}

trait LinearTable extends ImageTransformationTable[PixelImage, TextImage] {

}

trait UnLinearTable extends ImageTransformationTable[PixelImage, TextImage] {

}

class BourkeTransformationTable extends LinearTable {
  protected var tableData: String = " .:-=+*#%@"

  override def applyTransformation(image: PixelImage): TextImage = {
    val width = image.width
    val height = image.height
    val textImage = new TextImage(width, height)

    for (y <- 0 until height) {
      for (x <- 0 until width) {
        val greyValue = image.getPixel(x, y) & 0xFF
        val index = (greyValue * (tableData.length - 1)) / 255
        textImage.setPixel(x, y, tableData(index))
      }
    }

    textImage
  }
}
/*
class BigBourkeTransformationTable extends LinearTable {
  protected var tableData: String = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. "
  override def applyTransformation(image: PixelImage): TextImage = {

  }
}

class CustomTransformationTable(data: String) extends ImageTransformationTable[PixelImage, TextImage] {
  protected var tableData: String = data
  override def applyTransformation(image: PixelImage): TextImage = {

  }
}*/
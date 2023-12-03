package AsciiArtApp

import AsciiArtApp.models.{Image, ImageBase, PixelImage}

trait ImageOperation[T <: ImageBase] {
  def apply(image: T): T
}

trait PixelOperation extends ImageOperation[PixelImage] {

}

class AdjustContrastOperation(param: Double) extends PixelOperation {
  def apply(image: PixelImage): PixelImage = {
    val width = image.width
    val height = image.height

    // Рассчитываем среднюю яркость
    var totalBrightness = 0
    for (x <- 0 until width; y <- 0 until height) {
      totalBrightness += image.getPixel(x, y) & 0xFF
    }
    val meanBrightness = totalBrightness / (width * height)

    // Создаем новый PixelImage для результата
    val contrastImage = new PixelImage(width, height)

    // Регулируем контраст для каждого пикселя
    for (x <- 0 until width; y <- 0 until height) {
      val brightness = image.getPixel(x, y) & 0xFF
      val adjustedBrightness = ((brightness - meanBrightness) * param + meanBrightness).toInt

      // Значения должны быть ограничены диапазоном [0, 255]
      val clampedBrightness = math.min(255, math.max(0, adjustedBrightness)).toByte
      contrastImage.setPixel(x, y, clampedBrightness)
    }

    contrastImage
  }
}

class ResizeOperation(newWidth: Int, newHeight: Int) extends PixelOperation {
  def apply(image: PixelImage): PixelImage = {
    val originalWidth = image.width
    val originalHeight = image.height
    val resizedImage = new PixelImage(newWidth, newHeight)

    for (x <- 0 until newWidth; y <- 0 until newHeight) {
      val srcX = (x * originalWidth / newWidth).toInt
      val srcY = (y * originalHeight / newHeight).toInt
      val newPixel = image.getPixel(srcX, srcY)
      resizedImage.setPixel(x, y, newPixel)
    }

    resizedImage
  }
}

class ConvertToGreyscaleOperation() extends PixelOperation {
  def apply(image: PixelImage): PixelImage = {
    val width = image.width
    val height = image.height
    val greyImage = new PixelImage(width, height)

    for (x <- 0 until width) {
      for (y <- 0 until height) {
        val color = image.getPixel(x, y) & 0xFF
        greyImage.setPixel(x, y, color.toByte)
      }
    }

    greyImage
  }
}

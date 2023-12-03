package AsciiArtApp.loader

import AsciiArtApp.models.{Image, PixelImage}

import java.io.File
import javax.imageio.ImageIO

class FileImageLoader extends ImageLoader[PixelImage] {
  def load(path: String): PixelImage = {
    val file = new File(path)
    val originalImage = ImageIO.read(file)

    val width = originalImage.getWidth
    val height = originalImage.getHeight

    val pixelImage = new PixelImage(width, height)

    for (x <- 0 until width; y <- 0 until height) {
      val rgb = originalImage.getRGB(x, y)
      val red = (rgb >> 16) & 0xFF
      val green = (rgb >> 8) & 0xFF
      val blue = rgb & 0xFF

      val grey = (0.3 * red + 0.59 * green + 0.11 * blue).toInt.toByte
      pixelImage.setPixel(x, y, grey)
    }
    pixelImage
  }
}

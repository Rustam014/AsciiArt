package AsciiArtApp.filter.filters

import AsciiArtApp.filter.ImageFilter
import AsciiArtApp.models.{Image, ImageBase, PixelImage}

class RotateFilter(angle: Double) extends ImageFilter {
  override def apply(image: ImageBase): ImageBase = {
    image match {
      case pixelImage: PixelImage => image
      case _ => image
    }
  }
}

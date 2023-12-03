package AsciiArtApp.loader

import AsciiArtApp.models.Image

trait ImageLoader[T <: Image[_]] extends Loader[ Image[_] ]{
  def load(path: String): T
}
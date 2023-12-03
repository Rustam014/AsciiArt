package AsciiArtApp

import AsciiArtApp.loader.Loader
import AsciiArtApp.models.{Image, PixelImage}

class ImageFacade(storage: ImageStorage) {
  def get(): Image[_] = storage.get()

  def save(image: Image[_]): Unit = storage.update(image)

}

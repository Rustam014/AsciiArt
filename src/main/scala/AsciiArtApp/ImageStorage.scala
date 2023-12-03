package AsciiArtApp

import AsciiArtApp.models.Image


class ImageStorage(initialItem: Image[_]) extends Storage[ Image[_] ] {

  private var actual_item: Image[_] = initialItem

  override def get(): Image[_] = actual_item

  override def update(item: Image[_]): Unit = {
    actual_item = item
  }
}
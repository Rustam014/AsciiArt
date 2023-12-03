package AsciiArtApp.view

import AsciiArtApp.controller.ConsoleController
import AsciiArtApp.{ImageFacade, ImageStorage}
import AsciiArtApp.input.ConsoleInput
import AsciiArtApp.loader.FileImageLoader
import AsciiArtApp.models.{Image, PixelImage}

object CLI {

  def main(args: Array[String]): Unit = {

    val loader = new FileImageLoader()
    val storage = new ImageStorage(new PixelImage(100,100))
    val facade = new ImageFacade(storage)
    val input = new ConsoleInput(args)
    val controller = new ConsoleController(facade, loader)
    val view = new ConsoleView(input, controller)
    view.start()
  }
}
/*
* TODO
*  удалить репозитории exportcontroller
*  операции scale, greyscale konverzi apod. do vlastních modulů
*  business modely pro různé typy obrázků a jejich pixelů ale už jim neimplementuj žádné operace, ty udělej zvlášť do již zmíněných modulů.
*  Fasády by měla exekuovat nějaký business process
*  изменить UI и отвязать его от ConfigBuilder
*  сделать таблицы расширяймыми
* */
package AsciiArtApp.controller

import AsciiArtApp.exporter.{ConsoleExporter, FileExporter, StreamTextExporter, TextExporter}
import AsciiArtApp.{AdjustContrastOperation, ImageFacade}
import AsciiArtApp.loader.FileImageLoader
import AsciiArtApp.models.{PixelImage, TextImage}

import java.io.File

class ConsoleController(facade: ImageFacade, loader: FileImageLoader) {

  def load(inputPath: String): Unit = {
    val image = loader.load(inputPath)
    facade.save(image)
  }

  def transform(filters: Map[String, String], table: String): Unit = {
    val image = facade.get().asInstanceOf[PixelImage]
    val newImage = applyOperations(image)
    val newImage2 = applyFilters(newImage, filters)
    val textImage = applyTableTransformation(newImage2, table)
    facade.save(textImage)
  }

  private def applyOperations(image: PixelImage): PixelImage = {
    val newController = new NewController
    val operations = newController.getImageOperation()
    operations.foldLeft(image) { (img, operation) => operation.apply(img) }

  }
  private def applyFilters(image: PixelImage, filters: Map[String, String]): PixelImage = {
    val newController = new NewController
    val filtersSeq = newController.getImageFilters(filters)
    filtersSeq.foldLeft(image) { (img, filter) =>
      filter.apply(img).asInstanceOf[PixelImage]
    }
  }

  private def applyTableTransformation(image: PixelImage, table: String): TextImage = {
    val newController = new NewController
    val transformTable = newController.getTransformationTable(table)
    transformTable.applyTransformation(image)
  }

  private def getModes(mode: Map[String, String]): Seq[TextExporter] = {
    mode.flatMap {
      case ("outputFile", value) =>
        Some(new FileExporter(new File(value)))
      case ("outputStd", _) =>
        Some(new ConsoleExporter)
      case _ => None
    }.toSeq
  }

  private def getAscii(): String = {
    val image = facade.get().asInstanceOf[TextImage]
    val text = image.getData()
    text.toString
  }

  def exportAscii(mode: Map[String, String]): Unit = {
    val modes = getModes(mode)
    val image = getAscii()
    modes.foreach { mode => mode.display(image) }
  }



}

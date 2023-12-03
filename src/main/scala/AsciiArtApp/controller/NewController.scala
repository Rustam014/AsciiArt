package AsciiArtApp.controller

import AsciiArtApp.{AdjustContrastOperation, ConvertToGreyscaleOperation, ImageOperation, PixelOperation, ResizeOperation}
import AsciiArtApp.filter.ImageFilter
import AsciiArtApp.filter.filters.RotateFilter
import AsciiArtApp.models.{Image, ImageBase, PixelImage, TextImage}
import AsciiArtApp.table.{BourkeTransformationTable, ImageTransformationTable}

class NewController {
  def getImageFilters(filters: Map[String, String]): Seq[ImageFilter] = {
    filters.flatMap {
      case ("rotate", angle) =>
        try {
          Some(new RotateFilter(angle.toDouble))
        } catch {
          case e: NumberFormatException => None
        }

      case _ => None
    }.toSeq
  }

  def getImageOperation(): Seq[PixelOperation] = {
    Seq(
      new AdjustContrastOperation(1.2),
      new ResizeOperation(800, 600),
      new ConvertToGreyscaleOperation()
    )
  }

  def getTransformationTable(table: String): ImageTransformationTable[PixelImage, TextImage] = {
    table match {
      case "bourke" => new BourkeTransformationTable
      case _ => new BourkeTransformationTable
    }
  }

}

package AsciiArtApp.input

import java.awt.image.BufferedImage
import java.awt.{Color, Font}
import java.io.File
import java.nio.file.{Files, Paths}
import java.util.Random
import javax.imageio.ImageIO
trait ArgumentParser {
  def parse(argument: String, config: Config): (Boolean, Config)
}

class ImageParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    if (argument.isEmpty) {
      throw new IllegalArgumentException("Missing image argument")
    }

    if (!Files.exists(Paths.get(argument)) || !Files.isReadable(Paths.get(argument))) {
      throw new IllegalArgumentException("File not acceptable")
    }

    if (isValidImage(argument)) {
      config.setParameter("input", argument)
      (true, config)
    } else {
      throw new IllegalArgumentException("Invalid Format")
    }
  }
  private def isValidImage(value: String): Boolean = {
    try {
      val image = ImageIO.read(Files.newInputStream(Paths.get(value)))
      image != null
    } catch {
      case _: Exception => false
    }
  }
}

class OutputFileParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    val path = Paths.get(argument)
    if (!path.toString.endsWith(".txt")) {
      throw new IllegalArgumentException("Invalid Format")
    }

    if (!Files.exists(path))
      Files.createFile(path)
    if (Files.isWritable(path)) {
      config.setParameter("outputPath", argument)
      config.setParameter("outputFile", "file")
      (true, config)
    }
    else {
      throw new IllegalArgumentException("Invalid Format")
    }
  }
}

class RotateParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    argument.toIntOption match {
      case Some(intValue) =>
        config.setParameter("rotate", argument)
        (true, config)
      case _ =>
        throw new IllegalArgumentException("Invalid Format")
    }
  }
}

class FlipParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    argument match {
      case "x" | "y" =>
        config.setParameter("flip", argument)
        (true, config)
      case _ =>
        throw new IllegalArgumentException("Invalid Format")
    }
  }
}

class ScaleParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    argument.toDoubleOption match {
      case Some(doubleValue) =>
        config.setParameter("scale", argument)
        (true, config)
      case _ =>
        throw new IllegalArgumentException("")
    }
  }
}

class BrightnessParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    argument.toIntOption match {
      case Some(intValue) =>
        config.setParameter("brightness", argument)
        (true, config)
      case _ =>
        throw new IllegalArgumentException("")
    }
  }
}

class FontAspectParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    val split = argument.split(":")
    if (split.length == 2) {
      val x = split(0).toInt
      val y = split(1).toInt
      config.setParameter("font", argument)
      (true, config)
    } else {
      throw new IllegalArgumentException("Invalid flip axis. Only 'x' or 'y' are allowed.")
    }
  }
}

class TableParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    argument match {
      case "bourke" =>
        config.setParameter("table", "bourke")
      case "bourke#2" =>
        config.setParameter("table", "bourke#2")
      case "unlinear" =>
        config.setParameter("table", "unlinear")
      case _ =>
        config.setParameter("table", "bourke")
    }
    (true, config)
  }
}

class ConsoleParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    config.setParameter("outputStd", "std")
    (true, config)
  }
}

class RandomParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    val imagePath = createImage()
    if (imagePath.isEmpty) {
      (false, config)
    }
    else {
      config.setParameter("input", imagePath)
      (true, config)
    }

  }

  def createImage(): String = {
    val random = new Random()
    val width = 400
    val height = 400
    val image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val g = image.createGraphics()

    // Set a random color as background
    g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
    g.fillRect(0, 0, width, height)

    // Draw a random number of shapes with random colors, sizes, and positions
    for (_ <- 1 to random.nextInt(10) + 1) {
      g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
      random.nextInt(3) match {
        case 0 => g.fillRect(random.nextInt(width), random.nextInt(height), random.nextInt(100), random.nextInt(100))
        case 1 => g.fillOval(random.nextInt(width), random.nextInt(height), random.nextInt(100), random.nextInt(100))
        case 2 => g.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width), random.nextInt(height))
      }
    }

    // Set a random color and choose a random string from the list to draw on the image
    val texts = List("I like OOP!", "Scala is great!", "Random art", "Random rules!", "bla-bla-bla")
    g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
    g.setFont(new Font("Arial", Font.BOLD, 20 + random.nextInt(21)))
    g.drawString(texts(random.nextInt(texts.length)), 50, height - 50)

    g.dispose()

    val outputPath = ".\\image_jpg\\random-image.jpg"
    ImageIO.write(image, "jpg", new File(outputPath))
    outputPath
  }
}

class InvertParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    config.setParameter("invert", argument)
    (true, config)
  }
}

class CustomTableParser() extends ArgumentParser {
  override def parse(argument: String, config: Config): (Boolean, Config) = {
    if (argument.isEmpty) {
      println("Error: missing argument for --custom-table")
      config.setParameter("table", "bourke")
      (true, config)
    }
    else {
      config.setParameter("custom", argument)
      (true, config)
    }
  }
}
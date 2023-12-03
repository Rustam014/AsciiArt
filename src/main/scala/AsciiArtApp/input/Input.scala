package AsciiArtApp.input

import scala.annotation.tailrec

trait Input {
  def getImageData: Config
}

class ConsoleInput(args: Array[String]) extends Input {
  override def getImageData: Config = {
    val argsParser = new ArgsParser()

    if (args.count(_ == "--image") > 1) {
      throw new IllegalArgumentException("Missing image argument")
    }

    if (!args.contains("--image") && !args.contains("--image-random")) {
      throw new IllegalArgumentException("Missing image argument")
    }

    val additionalArgs = Array("--table", "bourke")
    var argsCopy = args.clone()
    if (!args.contains("--table")) {
      argsCopy = args ++ additionalArgs.filterNot(arg => args.contains(arg))
    }
    val config = new Config()
    argsParser.nextOption(config, argsCopy.toList)
  }
}

class NetworkInput(url: String) extends Input {
  override def getImageData: Config = {
    val config = new Config()
    config
  }
}

class FileInput(filePath: String) extends Input {
  override def getImageData: Config = {
    val config = new Config()
    config
  }
}


class ArgsParser() {
  private val argumentWithValueParsers: Map[String, ArgumentParser] = Map(
    "--image" ->              new ImageParser(),
    "--output-file" ->        new OutputFileParser(),
    "--rotate" ->             new RotateParser(),
    "--flip" ->               new FlipParser(),
    "--scale" ->              new ScaleParser(),
    "--brightness" ->         new BrightnessParser(),
    "--font-aspect-ratio" ->  new FontAspectParser(),
    "--table" ->              new TableParser(),
    "--custom-table" ->       new CustomTableParser()
  )
  private val argumentWithoutValueParsers: Map[String, ArgumentParser] = Map(
    "--output-console" ->     new ConsoleParser(),
    "--image-random" ->       new RandomParser(),
    "--invert" ->             new InvertParser(),
  )


  @tailrec
  final def nextOption(config: Config, list: List[String]): Config = {
    list match {
      case key :: tail if argumentWithoutValueParsers.contains(key) =>
        val parser = argumentWithoutValueParsers(key)
        val (isValid, newConfig) = parser.parse("", config)
        if (isValid) nextOption(newConfig, tail) else newConfig

      case key :: value :: tail if argumentWithValueParsers.contains(key) =>
        val parser = argumentWithValueParsers(key)
        val (isValid, newConfig) = parser.parse(value, config)
        if (isValid) nextOption(newConfig, tail) else newConfig

      case _ => config
    }
  }
}
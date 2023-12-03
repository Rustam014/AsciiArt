package AsciiArtApp.exporter

import java.io.{File, FileOutputStream, IOException}
import java.nio.file.{Files, Paths, StandardOpenOption}

class FileExporter(file: File)
  extends StreamTextExporter(new FileOutputStream(file))
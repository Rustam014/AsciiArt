package AsciiArtApp.exporter

trait Exporter[T] {
  def display(image: T): Unit
}
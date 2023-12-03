package AsciiArtApp.loader

trait Loader[T] {
  def load(path: String): T
}
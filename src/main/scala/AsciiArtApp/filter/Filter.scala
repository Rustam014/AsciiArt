package AsciiArtApp.filter

trait Filter[T] {
  def apply(image: T): T
}
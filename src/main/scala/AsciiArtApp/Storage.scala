package AsciiArtApp

trait Storage [T] {
  def get(): T
  def update(item: T): Unit

}

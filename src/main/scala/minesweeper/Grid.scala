package minesweeper

trait Grid[A] {

  val underlying: List[A] 
  val _x: Int
  val _y: Int
  
  assert(_x > 0 && _y > 0 && underlying.size == _x * _y)
  
  def get(x: Int, y: Int): A = underlying(x*_y + y)
  
  def get(pair: (Int, Int)): A = get(pair._1, pair._2)

  def neighboursWithDiagonals(coordinate: (Int, Int)): List[(Int, Int)] = Grid.neighboursWithDiagonals(coordinate)(_x, _y)
}

object Grid {
  def neighboursWithDiagonals(coordinate: (Int, Int))(_x: Int, _y: Int): List[(Int, Int)] = {
    val (x, y) = coordinate
    val temp = List((x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)).appendedAll(
      List((x + 1, y + 1), (x + 1, y - 1), (x - 1, y + 1), (x - 1, y - 1))
    )
    temp
      .filter(pair => pair._1 >= 0 && pair._2 >= 0)
      .filter(pair => pair._1 < _x && pair._2 < _y)
  }
}
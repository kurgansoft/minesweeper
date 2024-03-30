package minesweeper

import Field.*
import minesweeper.FieldState.{EXPLORED, FLAGGED, UNEXPLORED}

import scala.collection.mutable.ListBuffer
import scala.util.Random
case class MineSweeperGrid(
                            override val underlying: List[Cell] = List.fill(81)(Cell(EMPTY(0),FieldState.UNEXPLORED)),
                            override val _x: Int = 9,
                            override val _y: Int = 9,
                            noOfFieldsToExplore: Int = 71
                          ) extends Grid[Cell] {

  lazy val victory: Boolean = noOfFieldsToExplore == 0
  private def internalExpandField(toExpand: (Int, Int)): Set[(Int, Int)] = {
    val result: ListBuffer[(Int, Int)] = ListBuffer()
    val toExplore: ListBuffer[(Int, Int)] = ListBuffer(toExpand)

    val alreadyExplored = scala.collection.mutable.Set[(Int,Int)]()

    var loopCounter: Int = 0

    while (toExplore.nonEmpty) {
      loopCounter = loopCounter+1
      val x = toExplore.remove(0)
      alreadyExplored.add(x)
      get(x) match
        case Cell(Field.EMPTY(0), FieldState.UNEXPLORED) =>
          val neighbours = neighboursWithDiagonals(x)
          val reveal = neighbours.filter(pair => get(pair).fieldState == FieldState.UNEXPLORED && get(pair).field.isInstanceOf[EMPTY]).toSet
          val expandFurther = neighbours.filter(pair => get(pair).fieldState == FieldState.UNEXPLORED && get(pair).field == EMPTY(0)).toSet

          result.addAll(reveal + x)
          toExplore.addAll(expandFurther -- alreadyExplored)
        case Cell(Field.EMPTY(_), FieldState.UNEXPLORED) =>
          result.addOne(x)
        case _ => ()
    }
    println(s"internal expandField loop was _executed_ $loopCounter times.")
    result.toSet
  }

  def handleLeftClick(x: Int, y: Int): (MineSweeperGrid, Int) = {
    get(x,y) match
      case Cell(MINE, FieldState.UNEXPLORED) =>
        (this.copy(underlying = underlying.updated(x*_x + y, get(x,y).copy(fieldState = EXPLORED))), 1)
      case Cell(EMPTY(_), FieldState.UNEXPLORED) =>
        val aaa = internalExpandField((x,y))
        val indexesToUpdate: Set[Int] = aaa.map(pair => pair._1 * _x + pair._2)
        val newList = underlying.zipWithIndex.map((cell, index) =>
          if (indexesToUpdate.contains(index))
            cell.copy(fieldState = FieldState.EXPLORED)
          else
            cell)
        val newT = this.copy(underlying = newList, noOfFieldsToExplore = noOfFieldsToExplore - aaa.size)
        (newT, {if (newT.victory) 2 else 0})
      case _ => (this, 0)
  }

  def handleRightClick(x: Int, y: Int): MineSweeperGrid =
    get(x, y) match
      case Cell(_, FieldState.UNEXPLORED) =>
        this.copy(underlying = underlying.updated(x * _x + y, get(x, y).copy(fieldState = FLAGGED)))
      case Cell(_, FieldState.FLAGGED) =>
        this.copy(underlying = underlying.updated(x * _x + y, get(x, y).copy(fieldState = UNEXPLORED)))
      case _ => this

}

object MineSweeperGrid {

  private def generateMines(seed: Long = 0L): List[Int] = {
    val MAX: Int = 80
    val random = new Random(seed)

    (1 to 10).foldLeft(List.empty[Int])((acc, _) => {
      val randomNumber = random.nextInt(MAX) + 1
      var pick: Int = acc.lastOption.fold(randomNumber)(last => (last + randomNumber) % MAX)
      while (acc.contains(pick))
        pick = pick + 1
      acc.appended(pick)
    })
  }
  def generateGrid(seed: Long, startField: (Int, Int)): MineSweeperGrid = {
    val array = Array.fill(9)(Array.fill(9)(Field.EMPTY(0)))
    val offset = startField._1 * 9 + startField._2
    val mines = generateMines(seed)
    mines.foreach(mine => {
      val temp = (mine + offset + 1) % 81
      val (x, y) = (temp / 9, temp % 9)
      array(x)(y) = Field.MINE
    })
    for {
      x <- 0 until 9
      y <- 0 until 9
    } array(x)(y) match
      case Field.MINE => ()
      case _ =>
        val number = Grid.neighboursWithDiagonals((x, y))(9,9).count(coordinate => array(coordinate._1)(coordinate._2) == Field.MINE)
        array(x)(y) = Field.EMPTY(number)
    MineSweeperGrid(array.flatten.toList.map(x => Cell(x, FieldState.UNEXPLORED)))
  }
}
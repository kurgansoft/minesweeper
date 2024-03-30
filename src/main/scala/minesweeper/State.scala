package minesweeper

import minesweeper.GameState._
import uiglue.EventLoop.EventHandler
import uiglue.UIState
import zio.{UIO, ZIO}

case class State(grid: Option[MineSweeperGrid] = None, gameState: GameState = NOT_STARTED, noOfRemainingFieldsToExplore: Int = -1) extends UIState[Action] {

  override def processEvent(action: Action): (UIState[Action], EventHandler[Action] => UIO[List[Action]]) =
    action match
      case LeftClick(x, y) =>
        println(s"field clicked: ($x,$y)")
        grid match
          case None => (this, _ => for {
            seed <- zio.Random.nextLong
          } yield List(GenerateAndExpand(seed, x, y)))
          case Some(mineSweeperGrid) =>
            gameState match
              case IN_PROGRESS =>
                mineSweeperGrid.get(x, y) match
                  case Cell(_, FieldState.UNEXPLORED) =>
                    val (newGrid, resultCode) = mineSweeperGrid.handleLeftClick(x,y)
                    this.copy(Some(newGrid), gameState = {
                      resultCode match
                        case 1 => EXPLODED
                        case 2 => VICTORY
                        case _ => IN_PROGRESS
                    })
                  case _ => this
              case _ =>
                println("clicking has no effect if not in progress...")
                this
      case RightClick(x,y) =>
        (grid, gameState) match
          case (Some(mineSweeperGrid), IN_PROGRESS) =>
            this.copy(grid = Some(this.grid.get.handleRightClick(x,y)))
          case _ => this
          
      case GenerateAndExpand(seed, x, y) =>
        val generatedGridAfterFirstExpansion = MineSweeperGrid.generateGrid(seed, (x, y)).handleLeftClick(x, y)._1
        val state = State(Some(generatedGridAfterFirstExpansion), GameState.IN_PROGRESS, noOfRemainingFieldsToExplore = 71)
        state

      case NewGame => State()

}

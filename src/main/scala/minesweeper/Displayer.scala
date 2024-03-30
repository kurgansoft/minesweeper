package minesweeper

import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.callback.Callback
import japgolly.scalajs.react.facade.SyntheticMouseEvent
import japgolly.scalajs.react.vdom.{Attr, TagOf, all}
import japgolly.scalajs.react.vdom.all.*
import minesweeper.Field.*
import minesweeper.FieldState.UNEXPLORED
import org.scalajs.dom.html.Div
import uiglue.EventLoop.EventHandler

// TagOf
// TagMod

object Displayer {

  val fieldStyle =
    List(
      display := "flex",
      flexDirection := "column",
      justifyContent := "center",
      alignContent := "center",
      flexWrap:= "wrap",
      border:="solid 1px blue"
    )

  val gridStyle = List(
    width:="300px",
    height:="300px",
    display:= "grid",
    gridTemplateRows:= "repeat(9, 1fr)",
    gridTemplateColumns:= "repeat(9, 1fr)",
    backgroundColor:="green",
    gridGap:="2px",
  )

  def displayCell(cell: Cell, index: Int, gameIsOver: Boolean = false)
                 (eventEmitter: EventHandler[LeftClick | RightClick])
  : TagOf[Div] = {
    val imageName: String = cell match
      case Cell(MINE, FieldState.UNEXPLORED) if gameIsOver => "mine"
      case Cell(_, FieldState.UNEXPLORED) => "unexplored"
      case Cell(EMPTY(_), FieldState.FLAGGED) if gameIsOver => "incorrectly_flagged"
      case Cell(_, FieldState.FLAGGED) => "flagged"
      case Cell(EMPTY(0), FieldState.EXPLORED) => "empty"
      case Cell(EMPTY(number), FieldState.EXPLORED) => number.toString
      case Cell(MINE, FieldState.EXPLORED) => "exploded_mine"
      case _ => ""


    val callback = (event: japgolly.scalajs.react.facade.SyntheticMouseEvent[Div]) => Callback {
      event.preventDefault()
      val (x,y) = (index / 9, index % 9)
      event.button match
        case 0 => // LEFT_CLICK
          println(s"left click on cell with index $index")
          eventEmitter(LeftClick(x,y))
        case 2 => // RIGHT_CLICK
          println(s"right click on cell with index $index")
          eventEmitter(RightClick(x,y))
        case _ => ()
    }

     div(position:="relative",
       img(position:="absolute", src:=s"icons/$imageName.png", width:="100%", height:="100%", draggable:=false),
       onClick ==> callback,
       onContextMenu ==> callback
     )
  }

  val rootComponent =
    ScalaComponent.builder[(State, EventHandler[Action])]("RootComponent")
      .render_P({
        (state, eventEmitter) => state.grid match
          case None =>
            div(
              h1(s"${state.gameState}", color:="yellow"),
            div(
              (for (field <- 0 to 80)
                yield displayCell(Cell(Field.EMPTY(0), UNEXPLORED), field)(eventEmitter)(fieldStyle.toTagMod)
              ).toTagMod
            )(gridStyle.toTagMod)
            )
          case Some(grid) =>
            div(
              h1(s"${state.gameState}", color:="yellow"),
              div(
                (for {
                  x <- 0 to 8
                  y <- 0 to 8
                } yield displayCell(grid.get(x,y), x*9 + y , state.gameState == GameState.EXPLODED)(eventEmitter)
                (fieldStyle.toTagMod)).toTagMod
              )(gridStyle.toTagMod),
              button("START NEW GAME", onClick --> Callback(eventEmitter(NewGame)))
            )
      }).build

}

package minesweeper

import org.scalajs.dom.html.Div
import uiglue.EventLoop.EventHandler
import uiglue.{EventLoop, UIState}
import zio.Unsafe
import zio.internal.stacktracer.Tracer

import scala.concurrent.Future

object EntryPoint {

  // TODO test that first click is never a mine

  implicit val ec: scala.concurrent.ExecutionContext = org.scalajs.macrotaskexecutor.MacrotaskExecutor
  implicit val unsafe: Unsafe = Unsafe.unsafe(x => x)

  def main(args: Array[String]): Unit = {

    val document = org.scalajs.dom.window.document
    val newDiv: Div = document.createElement("div").asInstanceOf[Div]
    document.body.replaceChildren(newDiv)

    val state: State = State()

    val renderFunction: (UIState[Action, Any], EventHandler[Action]) => Unit =
      (state, eventHandler) => Displayer.rootComponent(state.asInstanceOf[State], eventHandler).renderIntoDOM(newDiv)

    val loop = EventLoop.createLoop[Action, Any](state, renderFunction, List.empty)

    Future {
      zio.Runtime.default.unsafe.run(loop)
    }
  }
}

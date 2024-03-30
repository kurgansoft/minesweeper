package minesweeper

import uiglue.Event

sealed trait Action extends Event

case class LeftClick(x: Int, y: Int) extends Action
case class RightClick(x: Int, y: Int) extends Action
case object NewGame extends Action


case class GenerateAndExpand(seed: Long, x: Int, y: Int) extends Action


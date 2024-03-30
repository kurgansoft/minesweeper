package minesweeper

enum Field:
  case MINE extends Field
  case EMPTY(adjacentMines: Int) extends Field
  
enum FieldState:
  case UNEXPLORED
  case EXPLORED
  case FLAGGED
The classic minesweeper game implemented with Scala.js

# How to build

## Fast build
```
sbt fastOptJS
```

## Optimized build
```
sbt fullOptJS
```

# How to run

Given that you have built with fastOptJS version, simply open the index.html 
file (src/main/resources/index.html) in IntelliJ, then in the upper right corner 
click on one of the browser icons. IntelliJ will launch a livereload server and 
it should work out of the box.

If you want to use the fullOptJS version, first comment out the line in the html file
that references fastOpt, and uncomment the one that references fullOpt.

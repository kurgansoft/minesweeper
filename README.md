The classic minesweeper game implemented with Scala.js

# How to play

Visit:

https://kurgansoft.github.io/minesweeper/

# How to build locally

## Fast build
```
sbt fastOptJS
```

## Optimized build
```
sbt fullOptJS
```

# How to run locally

Open the index.html file (src/main/resources/index.html) and comment out the line that references
'main.js'. Uncomment the line that references root-fastopt.js or root-opt.js 
(depending on which version you have built in the previous step).

Given that you have the index.html open in IntelliJ, you can click on one of the browser
icons in the upper right corner. IntelliJ will launch a livereload server and
it should work out of the box.

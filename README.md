# Tic Tac Toe

Simple TicTacToe game written in clojuscript. Build for learning purpouses. The
idea and setup came from a [youtube video](https://www.youtube.com/watch?v=pIiOgTwjbes)
by Timothy Pratley. It is, however, my own take on the implementation.

# Try it live

You can try it right now, as it is [posted on Github Pages.](http://yannvanhalewyn.github.io/tictactoe-cljs/)

## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL.

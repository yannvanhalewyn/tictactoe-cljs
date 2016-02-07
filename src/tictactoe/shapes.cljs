(ns tictactoe.shapes)

(defn circle [x y]
  [:circle
   {
    :r 0.4
    :cx (+ x 0.5)
    :cy (+ y 0.5)
    :fill "none"
    :stroke-width "0.1"
    :stroke "#5b8"}])

(defn cross [x y]
  [:g {:stroke "#b45"
       :stroke-width 0.35
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.5 x) "," (+ 0.5 y) ") "
            "scale(0.35)")}
   [:line {:x1 -1 :y1 -1 :x2 1 :y2 1}]
   [:line {:x1 1 :y1 -1 :x2 -1 :y2 1}]])

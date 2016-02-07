(ns tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(defn new-board [n]
  (vec (repeat n (vec (repeat n 0)))))

(defonce app-state
  (atom {:text "Hello world!"
         :board (new-board 3)}))

(for [i (range (count (:board @app-state)))
      j (range (count (:board @app-state))) ]
  (prn i j))

(defn circle [x y]
  (prn "Ok!")
  [:circle
   {
    :r 0.4
    :cx (+ x 0.5)
    :cy (+ y 0.5)
    :fill "none"
    :stroke-width "0.1"
    :stroke "#5b8"}])

(defn blank [x y]
  [:rect
   {:width 0.9
    :height 0.9
    :fill "#546"
    :key (str x y)
    :x (+ 0.05 x)
    :y (+ 0.05 y)
    :on-click (fn [] (swap! app-state update-in [:board y x] inc))}])

(defn cross [x y]
  [:g {:stroke "#b45"
       :stroke-width 0.35
       :stroke-linecap "round"
       :transform
       (str "translate(" (+ 0.5 x) "," (+ 0.5 y) ") "
            "scale(0.35)")}
   [:line {:x1 -1 :y1 -1 :x2 1 :y2 1}]
   [:line {:x1 1 :y1 -1 :x2 -1 :y2 1}]])

(defn board []
  (into
   [:svg
    {:view-box "0 0 3 3"
     :width 500
     :height 500}]
   (for [i (range (count (:board @app-state)))
         j (range (count (:board @app-state))) ]
     (case (get-in @app-state [:board j i])
       0 [blank i j]
       1 [circle i j]
       2 [cross i j])
     )
   ))

(defn app []
  [:div
   [cross 1 1]
   [:h1 (:text @app-state)]
   [:button {:on-click #(prn @app-state)} "Print State"]
   [board]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state assoc-in [:board 0 0] 2)
)

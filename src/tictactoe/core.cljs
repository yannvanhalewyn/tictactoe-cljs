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

(defn circle []
  [:circle
   {
    :r 25
    :cx 30
    :cy 30
    :fill "none"
    :stroke-width "8"
    :stroke "#5b8"}])

(defn board []
  (into
   [:svg
    {:view-box "0 0 3 3"
     :width 500
     :height 500}]
   (for [i (range (count (:board @app-state)))
         j (range (count (:board @app-state))) ]
     [:rect {:width 0.9
             :height 0.9
             ;; :fill "#546"
             :fill (if (zero? (get-in @app-state [:board j i]))
                     "green"
                     "yellow")
             :key (str i j)
             :x i
             :y j
             :on-click (fn [] (swap! app-state update-in [:board j i] inc))}])))

(defn app []
  [:div
   [:h1 (:text @app-state)]
   [:button {:on-click #(prn @app-state)} "Print State"]
   [board]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
)

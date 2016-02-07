(ns tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [tictactoe.shapes :refer [circle cross]]
            [tictactoe.logic :as logic]))

(enable-console-print!)

(def board-size 3)

(defn new-board [n]
  (vec (repeat n (vec (repeat n :blank)))))

(defonce app-state
  (atom {:text "Tic Tac Toe"
         :board (new-board board-size)
         :game-status :in-progress}))

(defn new-game [n]
  (-> @app-state
      (assoc :board (new-board n))
      (assoc :game-status :in-progress)))

(defn blank [x y]
  [:rect
   {:width 0.9
    :height 0.9
    :fill "#6B6F8A"
    :x (+ 0.05 x)
    :y (+ 0.05 y)
    :on-click  (fn [] (if (logic/game-running? @app-state)
                        (swap! app-state (partial logic/play @app-state x y))))}])

(defn board []
  (into
   [:svg
    {:view-box (str "0 0 " board-size " " board-size)
     :width 500
     :height 500}]
   (for [i (range (count (:board @app-state)))
         j (range (count (:board @app-state))) ]
     (case (get-in @app-state [:board j i])
       :blank [blank i j]
       :player [circle i j]
       :computer [cross i j]))))

(defn app []
  [:div
   [cross 1 1]
   [:h1 (:text @app-state)]
   [:h2 (case (:game-status @app-state)
          :won "You won!"
          :lost "You lost.."
          :draw "It's a draw!"
          "")]
   [:p
    [:button
     {:on-click (fn new-game-click [e]
                  (swap! app-state #(new-game board-size)))}
     "New Game"]]
   [board]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state assoc :text "Tic Tac Toe")
)

(ns tictactoe.core
  (:require [reagent.core :as reagent :refer [atom]]
            [tictactoe.shapes :refer [circle cross]]))

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

(defn computer-move [board]
  (let [available-moves (for [i (range board-size)
                              j (range board-size)
                              :when (= (get-in board [j i]) :blank)]
                          [j i])
        move (rand-nth available-moves)]
    (assoc-in board move :computer)))

(defn straight? [owner board [x y] [dx dy]]
  (every? true?
          (for [i (range (count board))]
            (= (get-in board [(+ (* dy i) y)
                              (+ (* dx i) x)])
               owner))))

(defn win? [owner board]
  (some true?
        (for [i (range board-size)
              j (range board-size)
              dir [[1 0] [0 1] [1 1] [1 -1]]]
          (straight? owner board [i j] dir))))

(defn full? [board]
  (empty? (filter #(= :blank %) (apply concat board))))

(defn status [board]
  (cond
    (win? :player board) :won
    (win? :computer board) :lost
    (full? board) :draw
    :else :in-progress))

(defn update-status [state]
  (assoc state :game-status (status (:board state))))

(defn game-running? [state]
  (or (nil? (:game-status state)) (= (:game-status state) :in-progress)))

(defn computer-move-if-possible [state]
  (if (game-running? state)
    (update state :board computer-move)
    state))

(defn play [state x y]
  (when (game-running? state)
    (-> state
        (assoc-in [:board y x] :player)
        update-status
        (computer-move-if-possible)
        update-status)))

(defn blank [x y]
  [:rect
   {:width 0.9
    :height 0.9
    :fill "#546"
    :x (+ 0.05 x)
    :y (+ 0.05 y)
    :on-click  (fn [] (if (game-running? @app-state)
                        (swap! app-state (partial play @app-state x y))))}])

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
   [:button {:on-click #(prn @app-state)} "Print State"]
   [board]
   [:p
    [:button
     {:on-click (fn new-game-click [e]
                  (swap! app-state #(new-game 3)))}
     "New Game"]]])

(reagent/render-component [app]
                          (. js/document (getElementById "app")))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state assoc :text "Tic Tac Toe")
)

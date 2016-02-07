(ns tictactoe.logic)

(defn game-running? [state]
  (or (nil? (:game-status state)) (= (:game-status state) :in-progress)))

(defn computer-move [board]
  (let [available-moves (for [i (range (count board))
                              j (range (count board))
                              :when (= (get-in board [j i]) :blank)]
                          [j i])
        move (rand-nth available-moves)]
    (assoc-in board move :computer)))

(defn computer-move-if-possible [state]
  (if (game-running? state)
    (update state :board computer-move)
    state))

(defn straight? [owner board [x y] [dx dy]]
  (every? true?
          (for [i (range (count board))]
            (= (get-in board [(+ (* dy i) y)
                              (+ (* dx i) x)])
               owner))))

(defn win? [owner board]
  (some true?
        (for [i (range (count board))
              j (range (count board))
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

(defn play [state x y]
  (when (game-running? state)
    (-> state
        (assoc-in [:board y x] :player)
        update-status
        (computer-move-if-possible)
        update-status)))

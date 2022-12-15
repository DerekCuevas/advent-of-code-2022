(ns aoc-2022.days.twelve
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]))

(defn find-coord [grid value]
  (first (filter #(= value (get-in grid %))
                 (u/grid-coords grid))))

(defn parse-input [input]
  (let [grid (mapv #(mapv int %) input)
        start (find-coord grid (int \S))
        end (find-coord grid (int \E))]
    {:grid (-> grid
               (assoc-in start (int \a))
               (assoc-in end (int \z)))
     :start start
     :end end}))

(defn shortest-distance [start coord->neighbors end?]
  (loop [seen #{start}
         queue (u/queue [[start 0]])]
    (let [[current distance] (or (first queue) [nil nil])]
      (cond
        (nil? current) nil
        (end? current) distance
        :else (let [neighbors
                    (->> (coord->neighbors current)
                         (filter #(not (contains? seen %))))]
                (recur
                 (apply conj seen neighbors)
                 (apply conj
                        (pop queue)
                        (map #(vector % (inc distance)) neighbors))))))))

(defn neighbors [grid coord allowed?]
  (->> coord
       (u/grid-neighbors false)
       (map #(vector % (get-in grid %)))
       (filter (fn [[_ neighbor-height]]
                 (let [current-height (get-in grid coord)]
                   (and (some? neighbor-height)
                        (allowed? neighbor-height current-height)))))
       (map first)))

(defn a-to-z-neighbors [grid coord]
  (neighbors grid coord #(<= %1 (inc %2))))

(defn part-one [input]
  (let [{:keys [grid start end]} (parse-input input)]
    (shortest-distance start #(a-to-z-neighbors grid %) #(= % end))))

(defn z-to-a-neighbors [grid coord]
  (neighbors grid coord #(>= %1 (dec %2))))

(defn part-two [input]
  (let [{:keys [grid end]} (parse-input input)]
    (shortest-distance
     end
     #(z-to-a-neighbors grid %)
     #(= (get-in grid %) (int \a)))))

;; (h/aoc [2022 12] part-one part-two)

(ns aoc-2022.days.five
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]))

(defn parse-stack-row [s]
  (let [row (into [] s)]
    (mapv #(let [val (get row %)]
             (if (= \space val) nil val))
          (range 1 (count row) 4))))

(defn parse-stacks [ss]
  (->> ss
       (mapv parse-stack-row)
       (u/transpose)
       (mapv #(apply list (filter some? %)))))

(defn parse-move-instruction [s]
  (let [[_ & matches] (re-matches #"move (\d+) from (\d+) to (\d+)" s)
        [amount src dest] (mapv u/parse-int matches)]
    {:amount amount :src src :dest dest}))

(defn parse-input [input]
  (let [[stacks-strs instructions-strs] (split-with seq input)]
    {:stacks (parse-stacks (butlast stacks-strs))
     :instructions (map parse-move-instruction (rest instructions-strs))}))

(defn move-one [stacks src dest]
  (-> stacks
      (update src pop)
      (update dest #(conj % (first (get stacks src))))))

(defn move [stacks {:keys [amount src dest]}]
  (reduce (fn [s _] (move-one s (dec src) (dec dest))) stacks (range amount)))

(defn part-one [input]
  (let [{:keys [stacks instructions]} (parse-input input)]
    (->> instructions
         (reduce move stacks)
         (map first)
         (str/join))))

(defn part-two [input] false)

;; (h/aoc [2022 5] part-one part-two)

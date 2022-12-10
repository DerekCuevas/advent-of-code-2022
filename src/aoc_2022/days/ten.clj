(ns aoc-2022.days.ten
  (:require [aoc-2022.helper :as h]
            [clojure.string :as str]
            [aoc-2022.utils :as u]
            [clojure.core.match :as m]))

(defn parse-instruction [s]
  (let [[opcode value] (str/split s #" ")]
    {:opcode (keyword opcode) :value (when (some? value) (u/parse-int value))}))

(defn process-instruction [cycle register instruction]
  (m/match instruction
    {:opcode :noop} [[(inc cycle) register]]
    {:opcode :addx :value dx} [[(inc cycle) register]
                               [(+ 2 cycle) (+ register dx)]]))

(defn cpu [instructions]
  (reduce (fn [{[cycle register] :state history :history} instruction]
            (let [results (process-instruction cycle register instruction)]
              {:state (last results) :history (apply conj history results)}))
          {:state [1 1] :history [[1 1]]}
          instructions))

(defn part-one [input]
  (let [states
        (->> input
             (map parse-instruction)
             (cpu)
             (:history))]
    (->> (range 20 (inc 220) 40)
         (map #(nth states (dec %)))
         (map #(apply * %))
         (reduce +))))

(defn part-two [input] false)

(h/aoc [2022 10] part-one part-two)

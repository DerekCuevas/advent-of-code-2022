(ns aoc-2022.days.ten
  (:require [aoc-2022.helper :as h]
            [clojure.string :as str]
            [aoc-2022.utils :as u]
            [clojure.core.match :as m]
            [babashka.fs :as fs]))

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

(def crt-width 40)
(def crt-height 6)

(defn sprite-visible? [col register]
  (and (>= col (dec register)) (<= col (inc register))))

(defn update-crt [crt [cycle register]]
  (let [cycle-idx (dec cycle)
        row (quot cycle-idx crt-width)
        col (rem cycle-idx crt-width)]
    (if (sprite-visible? col register)
      (assoc-in crt [row col] "#")
      crt)))

(defn render [crt]
  (let [formatted (map str/join crt)]
    (fs/write-lines ".aoc/2022/day/10/output.txt" formatted)
    formatted))

(defn part-two [input]
  (->> input
       (map parse-instruction)
       (cpu)
       (:history)
       (reduce update-crt (u/grid crt-width crt-height "."))
       (render)))

;; (h/aoc [2022 10] part-one part-two)

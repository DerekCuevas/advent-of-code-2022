(ns aoc-2022.days.eleven
  (:require [aoc-2022.helper :as h]
            [aoc-2022.utils :as u]
            [clojure.string :as str]))

(defn parse-id [s]
  (->> s
       (re-matches #"Monkey (\d+):")
       (second)
       (u/parse-int)))

(defn parse-starting-items [s]
  (-> s
      (str/split #": ")
      (second)
      (str/split #", ")
      (#(mapv u/parse-int %))))

(defn parse-operation [s]
  (letfn [(parse-operand [hs]
            (if (= "old" hs) "%" hs))]
    (let [[_ lhs op rhs] (re-matches #"Operation: new = (\d+|old) (\+|\*|\-) (\d+|old)" (str/trim s))
          expression (format "#(%s %s %s)" op (parse-operand lhs) (parse-operand rhs))]
      (eval (read-string expression)))))

(defn parse-test-condition [s]
  (let [[_ amount] (re-matches #"Test: divisible by (\d+)" (str/trim s))]
    (eval (read-string (format "#(zero? (rem %s %s))" "%" amount)))))

(defn parse-branch [s]
  (let [[_ key value] (re-matches #"If (true|false): throw to monkey (\d+)" (str/trim s))]
    {(read-string key) (u/parse-int value)}))

(defn parse-monkey [s]
  (let [[id-str items-str operation-str test-str branch-a-str branch-b-str] s]
    {:id (parse-id id-str)
     :items (parse-starting-items items-str)
     :operation (parse-operation operation-str)
     :test {:condition (parse-test-condition test-str)
            :branches (merge (parse-branch branch-a-str) (parse-branch branch-b-str))}}))

(defn parse-input [input]
  (->> input
       (partition-by empty?)
       (filter #(seq (first %)))
       (map parse-monkey)))

(defn part-one [input] false)

(defn part-two [input] false)

(parse-input (h/aoc-example-input 2022 11))

(h/aoc [2022 11] part-one part-two)

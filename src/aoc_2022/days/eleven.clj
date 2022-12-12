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
       (map parse-monkey)
       (vec)))

(defn route-monkey-items [{:keys [items operation test]}]
  (->> items
       (map #(quot (operation %) 3))
       (map (fn [item] [((:branches test) ((:condition test) item)) item]))))

(defn play-turn [monkey monkeys]
  (as-> monkey $
    (route-monkey-items $)
    (reduce (fn [mks [id item]] (update-in mks [id :items] #(conj % item))) monkeys $)
    (assoc-in $ [(:id monkey) :items] [])))

(defn play-round [monkeys]
  (reduce
   (fn [{:keys [monkeys totals]} id]
     (let [monkey (nth monkeys id)]
       {:monkeys (play-turn monkey monkeys)
        :totals (update totals id #(+ (count (:items monkey)) (or % 0)))}))
   {:monkeys monkeys :totals {}}
   (range (count monkeys))))

(defn play-rounds [num-rounds monkeys]
  (reduce
   (fn [{:keys [monkeys totals]} _]
     (let [{next-monkeys :monkeys round-totals :totals} (play-round monkeys)]
       {:monkeys next-monkeys :totals (merge-with + totals round-totals)}))
   {:monkeys monkeys :totals {}}
   (range num-rounds)))

(defn part-one [input]
  (->> input
       (parse-input)
       (play-rounds 20)
       (:totals)
       (vals)
       (sort)
       (take-last 2)
       (apply *)))

(defn part-two [input] false)

;; (h/aoc [2022 11] part-one part-two)
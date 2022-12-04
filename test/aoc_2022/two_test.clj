(ns aoc-2022.two-test
  (:require [clojure.test :refer :all]
            [aoc-2022.days.two :as two]
            [aoc-2022.helper :as h]))

(def input (h/aoc-input 2022 2))

(deftest day-two-test
  (testing "part one"
    (is (= (two/part-one input) 12586)))
  (testing "part two"
    (is (= (two/part-two input) 13193))))

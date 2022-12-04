(ns aoc-2022.four-test
  (:require [clojure.test :refer :all]
            [aoc-2022.days.four :as four]
            [aoc-2022.helper :as h]))

(def input (h/aoc-input 2022 4))

(deftest day-four-test
  (testing "part one"
    (is (= (four/part-one input) 560)))
  (testing "part two"
    (is (= (four/part-two input) 839))))

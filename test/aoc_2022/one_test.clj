(ns aoc-2022.one-test
  (:require [clojure.test :refer :all]
            [aoc-2022.days.one :as one]
            [aoc-2022.helper :as h]))

(def input (h/aoc-input 2022 1))

(deftest day-one-test
  (testing "part one"
    (is (= (one/part-one input) 67633)))
  (testing "part two"
    (is (= (one/part-two input) 199628))))

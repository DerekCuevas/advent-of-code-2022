(ns aoc-2022.three-test
  (:require [clojure.test :refer :all]
            [aoc-2022.days.three :as three]
            [aoc-2022.helper :as h]))

(def input (h/aoc-input 2022 3))

(deftest day-three-test
  (testing "part one"
    (is (= (three/part-one input) 7821)))
  (testing "part two"
    (is (= (three/part-two input) 2752))))

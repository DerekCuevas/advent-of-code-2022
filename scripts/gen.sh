DATA='{"year": 2022, "day": 6, "day-str": "six"}'

hbs --data $DATA ./templates/day.clj.hbs --stdout > ./src/aoc_2022/days/six.clj
hbs --data $DATA ./templates/day_test.clj.hbs --stdout > ./test/six_test.clj

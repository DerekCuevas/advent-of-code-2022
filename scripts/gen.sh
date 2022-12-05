while getopts y:d:n: flag
do
    case "${flag}" in
        y) year=${OPTARG};;
        d) day=${OPTARG};;
        n) name=${OPTARG};;
    esac
done
echo "Generating files for $year day $day...";

template_vars="{\"year\": $year, \"day\": $day, \"name\": \"$name\"}"

day_file="./src/aoc_2022/days/${name}.clj"
day_test_file="./test/aoc_2022/${name}_test.clj"

hbs --data "$template_vars" ./templates/day.clj.hbs --stdout > "$day_file"
hbs --data "$template_vars" ./templates/day_test.clj.hbs --stdout > "$day_test_file"

echo "$day_file\n$day_test_file"

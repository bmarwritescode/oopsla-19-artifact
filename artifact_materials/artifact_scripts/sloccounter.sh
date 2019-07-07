BENCHMARK_PATH=../benchmarks
OUTPUT_PATH=../artifact_results/sloccounts

sloccount $BENCHMARK_PATH/SuffixArray/ > $OUTPUT_PATH/SuffixArray.txt
sloccount $BENCHMARK_PATH/HashMap1/ > $OUTPUT_PATH/HashMap1.txt
sloccount $BENCHMARK_PATH/HashMap2/ > $OUTPUT_PATH/HashMap2.txt
sloccount $BENCHMARK_PATH/PasswordManager/ > $OUTPUT_PATH/PasswordManager.txt
sloccount $BENCHMARK_PATH/CipherFactory/ > $OUTPUT_PATH/CipherFactory.txt
sloccount $BENCHMARK_PATH/Kafka/ > $OUTPUT_PATH/Kafka.txt
sloccount $BENCHMARK_PATH/EasyCSV/ > $OUTPUT_PATH/EasyCSV.txt
sloccount $BENCHMARK_PATH/RomList/ > $OUTPUT_PATH/RomList.txt
sloccount $BENCHMARK_PATH/Comparator/ > $OUTPUT_PATH/Comparator.txt

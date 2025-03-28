package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.*;

@State(Scope.Benchmark)
public class StreamBenchmark {
    private List<Integer> numbers;

    @Setup
    public void setup() {
        numbers = new ArrayList<>(10_000_000);
        Random random = new Random();
        for (int i = 0; i < 10_000_000; i++) {
            numbers.add(random.nextInt(100) + 1);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long sequentialSum() {
        return numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public double sequentialAverage() {
        return numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public double sequentialStdDev() {
        double mean = sequentialAverage();
        double sumSquaredDiff = numbers.stream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .sum();
        return Math.sqrt(sumSquaredDiff / numbers.size());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Integer> sequentialMultiplyByTwo() {
        return numbers.stream()
                .map(x -> x * 2)
                .collect(Collectors.toList());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Integer> sequentialFilter() {
        return numbers.stream()
                .filter(x -> x % 2 == 0 && x % 3 == 0)
                .collect(Collectors.toList());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long parallelSum() {
        return numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public double parallelAverage() {
        return numbers.parallelStream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public double parallelStdDev() {
        double mean = parallelAverage();
        double sumSquaredDiff = numbers.parallelStream()
                .mapToDouble(x -> Math.pow(x - mean, 2))
                .sum();
        return Math.sqrt(sumSquaredDiff / numbers.size());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Integer> parallelMultiplyByTwo() {
        return numbers.parallelStream()
                .map(x -> x * 2)
                .collect(Collectors.toList());
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public List<Integer> parallelFilter() {
        return numbers.parallelStream()
                .filter(x -> x % 2 == 0 && x % 3 == 0)
                .collect(Collectors.toList());
    }
}
package pl.sky0x.math;

import pl.sky0x.math.function.MathematicalFunction;
import pl.sky0x.math.function.MathematicalFunctionComposer;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public enum Operation {

    SUMMATION('+', 2, Double::sum),
    SUBTRACTION('-', 2, (a, b) -> a - b),
    MULTIPLICATION('*', 1, (a, b) -> a * b),
    DIVISION('/', 1, (a, b) -> {
                    if(b == 0) throw new UnsupportedOperationException("Value b must be greater than 0.");
                    return a / b;
                }),
    EXPONENTIATION('^', 0, Math::pow),
    NON_OPERATION('|', -1, (a,b) -> 0.0);

    private final char operation;
    private final int value;
    private final BiFunction<Double, Double, Double> function;

    Operation(char operation, int value, BiFunction<Double, Double, Double> function) {
        this.operation = operation;
        this.value = value;
        this.function = function;
    }

    public static boolean hasKey(char key) {
        return Arrays.stream(values())
                .anyMatch(o -> o.getOperation() == key);
    }

    public static Operation findByKey(char key) {
        return Arrays.stream(values())
                .filter(o -> o.getOperation() == key)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Operation " + key + " doesn't exists"));
    }

    public static Collection<Operation> findByValue(int value) {
        return Arrays.stream(values())
                .filter(o -> o.getOperation() == value)
                .collect(Collectors.toList());
    }

    public static List<Operation> sortByValue(Collection<Operation> operations) {
        return operations.stream()
                .sorted(Comparator.comparingInt(Operation::getValue))
                .collect(Collectors.toList());
    }

    public double useOperation(double a, double b) {
        return function.apply(a, b);
    }

    public MathematicalFunction useOperation(MathematicalFunction fxa, MathematicalFunction fxb) {
        return (x) -> function.apply(fxa.apply(x), fxb.apply(x));
    }

    public char getOperation() {
        return operation;
    }

    public int getValue() {
        return value;
    }

}

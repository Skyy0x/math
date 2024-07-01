package pl.sky0x.math.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Domain {

    private final Map<Function<Double, Boolean>, MathematicalFunction> functionMap;

    public Domain() {
        this.functionMap = new HashMap<>();
    }

    public static Domain defineFunction(MathematicalFunction function) {
        return defineFunction(Domain::defaultStatement, function);
    }

    public static Domain defineFunction(Function<Double, Boolean> statement, MathematicalFunction function) {
        return new Domain().addFunction(statement, function);
    }

    private static boolean defaultStatement(double value) {
        return Double.NEGATIVE_INFINITY < value;
    }

    private Domain addFunction(Function<Double, Boolean> statement, MathematicalFunction function) {
        if (functionMap.containsKey(statement)) {
            throw new IllegalArgumentException("Function already defined for the given statement");
        }
        functionMap.put(statement, function);
        return this;
    }

    public MathematicalFunction retrieveFunction(double value) {
        return functionMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().apply(value))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(x -> Double.NaN);
    }
}

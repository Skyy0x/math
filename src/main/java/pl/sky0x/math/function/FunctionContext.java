package pl.sky0x.math.function;

import pl.sky0x.math.Operation;
import pl.sky0x.math.exception.BadTypedFunctionException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionContext {

    private final List<MathematicalFunction> functions;
    private final List<Operation> operations;

    public FunctionContext() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    public FunctionContext(List<MathematicalFunction> functions, List<Operation> operations) {
        this.functions = functions;
        this.operations = operations;
    }

    public MathematicalFunction composeFunction() {
        final List<Operation> sortedOperations = Operation.sortByValue(operations);

        Map<Integer, MathematicalFunction> functions = IntStream.range(0, this.functions.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), this.functions::get));

        for (Operation sortedOperation : sortedOperations) {
            int index = operations.indexOf(sortedOperation);

            operations.remove(index);

            MathematicalFunction fxa = functions.get(index);
            MathematicalFunction fxb = functions.get(index + 1);

            MathematicalFunction appliedFunction = sortedOperation.useOperation(fxa, fxb);

            System.out.println("XD" + appliedFunction.apply(4.0));
            
            functions = functions.entrySet().stream()
                    .filter(e -> e.getKey() != index + 1)
                    .collect(Collectors.toMap(
                            e -> e.getKey() == index ? e.getKey() : e.getKey() > index ? e.getKey() - 1 : e.getKey(),
                            e -> e.getKey() == index ? appliedFunction : e.getValue(),
                            (a, b) -> a,
                            HashMap::new
                    ));
        }

        return functions.entrySet()
                .stream()
                .findFirst()
                .orElseThrow(BadTypedFunctionException::new)
                .getValue();
    }

    public List<MathematicalFunction> getFunctions() {
        return functions;
    }

    public FunctionContext addFunction(MathematicalFunction function) {
        this.getFunctions().add(function);
        return this;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public FunctionContext addOperation(Operation operation) {
        this.getOperations().add(operation);
        return this;
    }

}

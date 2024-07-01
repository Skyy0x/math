package pl.sky0x.math.function;

import pl.sky0x.math.Operation;

public class Composer {

    private final FunctionContext functionContext;

    private final char variable;

    private MathematicalFunction temporalFunction;
    private int index;
    private int startIndex;

    public Composer(char variable) {
        this.functionContext = new FunctionContext();

        this.variable = variable;

        this.index = 0;
        this.startIndex = 0;
    }

    public FunctionContext getFunctionContext() {
        return functionContext;
    }

    public void addOperation(Operation operation) {
        this.getFunctionContext().addOperation(operation);
    }

    public void addFunction(MathematicalFunction function) {
        this.getFunctionContext().addFunction(function);
    }

    public void setCachedFunction(MathematicalFunction function) {
        this.temporalFunction = function;
    }

    public MathematicalFunction getCachedFunction() {
        return this.temporalFunction;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getStartIndex() {
        return this.startIndex;
    }

    public char getVariable() {
        return variable;
    }
}

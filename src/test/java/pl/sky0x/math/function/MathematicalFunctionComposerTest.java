package pl.sky0x.math.function;

import org.junit.jupiter.api.Test;
import pl.sky0x.math.Operation;
import pl.sky0x.math.exception.BadTypedFunctionException;

import static org.junit.jupiter.api.Assertions.*;

public class MathematicalFunctionComposerTest {

    @Test
    void decomposeFunction_ShouldDecomposeValidFunction() {
        String inputFunction = "sin(x) + cos(x)";

        FunctionContext context = MathematicalFunctionComposer.decomposeFunction(inputFunction);

        FunctionContext expectedContext = new FunctionContext()
                .addFunction(Math::sin)
                .addFunction(Math::cos)
                .addOperation(Operation.SUMMATION);

        assertAll("Validate decomposed function context",
                () -> assertEquals(expectedContext.getFunctions().size(), context.getFunctions().size(), "Number of functions should match"),
                () -> assertEquals(expectedContext.getOperations().size(), context.getOperations().size(), "Number of operations should match"),
                () -> {
                    for (int i = 0; i < context.getFunctions().size(); i++) {
                        MathematicalFunction function = context.getFunctions().get(i);
                        MathematicalFunction expectedFunction = expectedContext.getFunctions().get(i);
                        assertEquals(expectedFunction.apply(5.0), function.apply(5.0), "Function output should match expected value");
                    }
                },
                () -> {
                    for (int i = 0; i < context.getOperations().size(); i++) {
                        Operation operation = context.getOperations().get(i);
                        Operation expectedOperation = expectedContext.getOperations().get(i);
                        assertEquals(expectedOperation, operation, "Operation should match expected operation");
                    }
                }
        );
    }

    @Test
    void getFunctionFromText_ShouldReturnValidFunction() {
        String inputFunction = "sin(x) + cos(x)";

        MathematicalFunction mathematicalFunction = MathematicalFunctionComposer.getFunctionFromText(inputFunction);

        double expectedValue = Math.sin(5) + Math.cos(5);

        assertEquals(expectedValue, mathematicalFunction.apply(5.0),
                "Function output should match expected value");
    }

    @Test
    void decomposeFunction_ShouldHandleEmptyInput() {
        String inputFunction = "";

        FunctionContext context = MathematicalFunctionComposer.decomposeFunction(inputFunction);

        assertAll("Validate empty input handling",
                () -> assertTrue(context.getFunctions().isEmpty(), "No functions should be extracted from empty input"),
                () -> assertTrue(context.getOperations().isEmpty(), "No operations should be extracted from empty input")
        );}

    @Test
    void getFunctionFromText_ShouldHandleInvalidFunction() {
        String inputFunction = "test";

        assertThrows(BadTypedFunctionException.class, () ->
                        MathematicalFunctionComposer.getFunctionFromText(inputFunction),
                "Exception should be thrown for invalid function"
        );
    }

}

package pl.sky0x.math.function;

import pl.sky0x.math.handler.FunctionHandler;
import pl.sky0x.math.handler.HandlerManager;

import java.util.*;

public class MathematicalFunctionComposer {

    private static final HandlerManager HANDLER_MANAGER = new HandlerManager()
            .registerDefaults();

    protected MathematicalFunctionComposer() {

    }

    public static FunctionContext decomposeFunction(String textFunction) {
        return decomposeFunction('x', textFunction);
    }

    public static FunctionContext decomposeFunction(char variable, String textFunction) {
        Composer composer = new Composer(variable);

        Collection<FunctionHandler> handlers = HANDLER_MANAGER.getHandlers();

        for (int i = 0; i < textFunction.length(); i++) {
            composer.setIndex(i);

            for (FunctionHandler handler : handlers) {
                if (handler.composeFunction(composer, textFunction)) {
                    break;
                }
            }

            i = composer.getIndex();
        }

        return composer.getFunctionContext();
    }

    public static MathematicalFunction getFunctionFromText(String function) {
        return decomposeFunction(function).composeFunction();
    }

    public static MathematicalFunction getFunctionFromText(char variable, String function) {
        return decomposeFunction(variable, function).composeFunction();
    }

}

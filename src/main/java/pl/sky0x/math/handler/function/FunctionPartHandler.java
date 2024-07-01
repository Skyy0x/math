package pl.sky0x.math.handler.function;

import pl.sky0x.math.function.Composer;
import pl.sky0x.math.handler.FunctionHandler;

public interface FunctionPartHandler extends FunctionHandler {

    boolean composeFunction(Composer composer, char character,  String function);

    @Override
    default boolean composeFunction(Composer composer, String function) {
        return composeFunction(composer, function.charAt(composer.getIndex()), function);
    }
}

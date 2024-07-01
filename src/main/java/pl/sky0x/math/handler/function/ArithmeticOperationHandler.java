package pl.sky0x.math.handler.function;

import pl.sky0x.math.Operation;
import pl.sky0x.math.function.Composer;

public class ArithmeticOperationHandler implements FunctionPartHandler {

    @Override
    public boolean composeFunction(Composer composer, char character, String function) {
        if(!Operation.hasKey(character)) {
            return false;
        }
        composer.addOperation(Operation.findByKey(character));
        return true;
    }
}

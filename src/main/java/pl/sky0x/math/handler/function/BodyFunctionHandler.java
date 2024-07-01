package pl.sky0x.math.handler.function;

import pl.sky0x.math.Operation;
import pl.sky0x.math.function.Composer;
import pl.sky0x.math.function.MathematicalFunction;
import pl.sky0x.math.function.MathematicalFunctionComposer;

public class BodyFunctionHandler implements FunctionPartHandler {

    @Override
    public boolean composeFunction(Composer composer, char character, String textFunction) {
        if (character != '(') {
            return false;
        }

        int index = composer.getIndex();
        int endIndex = findMatchingParenthesis(textFunction, index);
        String subTextFunction = textFunction.substring(index + 1, endIndex);

        if (index > 0 && Character.isLetter(textFunction.charAt(index - 1))) {
            if(composer.getCachedFunction() != null) {
                MathematicalFunction subFunction =  MathematicalFunctionComposer.getFunctionFromText(subTextFunction);

                MathematicalFunction finalPrevFunction = composer.getCachedFunction();
                MathematicalFunction function = (x) -> finalPrevFunction.apply(subFunction.apply(x));

                composer.addFunction(function);
            } else {
                composer.addFunction(MathematicalFunctionComposer.getFunctionFromText(subTextFunction));
            }
        } else {
            composer.addOperation(Operation.MULTIPLICATION);
            composer.addFunction(MathematicalFunctionComposer.getFunctionFromText(subTextFunction));
        }

        composer.setIndex(endIndex);
        return true;
    }

    public static int findMatchingParenthesis(String function, int startIndex) {
        int count = 1;
        for (int i = startIndex + 1; i < function.length(); i++) {
            char c = function.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("No matching parenthesis found.");
    }
}

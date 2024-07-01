package pl.sky0x.math.handler.function;

import pl.sky0x.math.Operation;
import pl.sky0x.math.function.Composer;
import pl.sky0x.math.function.MathematicalFunction;

public class BasicFunctionHandler implements FunctionPartHandler {
    @Override
    public boolean composeFunction(Composer composer, char character, String textFunction) {
        int index = composer.getIndex();
        int endIndex = composer.getIndex();

        if(isDouble(String.valueOf(character))) {
            int startIndex = endIndex;
            while(endIndex < textFunction.length() && isDouble(String.valueOf(textFunction.charAt(endIndex)))) {
                endIndex++;
            }

            composer.setIndex(endIndex - 1);

            String part = textFunction.substring(startIndex, endIndex);

            composer.addFunction((x) -> Double.parseDouble(part));
            return true;
        }

        if(character == composer.getVariable()) {
            if(!(endIndex < 1)) {
                if(isDouble(String.valueOf(textFunction.charAt(composer.getIndex() - 1)))) {
                    composer.addOperation(Operation.MULTIPLICATION);
                }
            };

            composer.addFunction((x) -> x);

            return true;
        }

        while (endIndex < textFunction.length() && Character.isLetterOrDigit(textFunction.charAt(endIndex))) {
            endIndex++;
        }

        String part;
        if(Operation.hasKey(textFunction.charAt(index))) {
            part = textFunction.substring(index + 1, endIndex);
        } else {
            part = textFunction.substring(index, endIndex);
        }

        if(part.isEmpty()) {
            return false;
        }

        MathematicalFunction basicFunction = getBasicFunction(part);

        if (basicFunction != null) {
            composer.setCachedFunction(basicFunction);
            composer.setIndex(endIndex-1);
            return true;
        }
        return false;
    }

    public MathematicalFunction getBasicFunction(String function) {
        return switch (function) {
            case "sin" -> Math::sin;
            case "cos" -> Math::cos;
            case "tan" -> Math::tan;
            case "asin" -> Math::asin;
            case "acos" -> Math::acos;
            case "atan" -> Math::atan;
            case "sinh" -> Math::sinh;
            case "cosh" -> Math::cosh;
            case "tanh" -> Math::tanh;
            case "exp" -> Math::exp;
            case "log" -> Math::log;
            case "sqrt" -> Math::sqrt;
            default -> null;
        };
    }

    public boolean isDouble(String text) {
        return text.matches("-?\\d+(\\.\\d+)?");
    }

}

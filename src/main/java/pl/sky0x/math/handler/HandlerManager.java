package pl.sky0x.math.handler;

import pl.sky0x.math.handler.function.ArithmeticOperationHandler;
import pl.sky0x.math.handler.function.BasicFunctionHandler;
import pl.sky0x.math.handler.function.BodyFunctionHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HandlerManager {

    private final Map<String, FunctionHandler> handlers;

    public HandlerManager() {
        this.handlers = new HashMap<>();
    }

    public HandlerManager registerHandler(String name, FunctionHandler handler) {
        this.handlers.put(name, handler);
        return this;
    }

    public HandlerManager registerDefaults() {
        return this.registerHandler("arithmetic", new ArithmeticOperationHandler())
                .registerHandler("basic", new BasicFunctionHandler())
                .registerHandler("body", new BodyFunctionHandler());
    }

    public FunctionHandler getHandler(String name) {
        return this.handlers.get(name);
    }

    public Collection<FunctionHandler> getHandlers() {
        return this.handlers.values();
    }
}

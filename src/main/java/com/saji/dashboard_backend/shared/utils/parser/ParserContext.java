package com.saji.dashboard_backend.shared.utils.parser;

public class ParserContext {
    private final ParsingStrategy[] strategies;

    public ParserContext() {
        strategies = new ParsingStrategy[] {
                new BooleanParser(),
                new IntegerParser(),
                new DoubleParser(),
                new StringParser()
        };
    }

    public Object parseValue(String value) {
        for (ParsingStrategy strategy : strategies) {
            Object result = strategy.parse(value);
            if (result != null) {
                return result;
            }
        }
        return null; // In case no parser was able to handle the value
    }
}
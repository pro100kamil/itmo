package common.loggers;

public class StandardLogger implements Logger {

    @Override
    public void write(String text) {
        System.out.println(text);
    }

    @Override
    public void writeError(String text) {
        System.err.println(text);
    }
}

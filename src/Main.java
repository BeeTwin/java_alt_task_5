public class Main extends Thread {

    public int someMethod(String number) {
        return Integer.parseInt(number);
    }

    public void run() {
        try {
            System.out.println(someMethod("hello"));
        } catch (RuntimeException e) {
            throw new IllegalStateException("cannot perform operation", e);
        }
    }

    public static void execute() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        Main m = new Main();
        m.setUncaughtExceptionHandler((t, e) -> {
            Throwable mergedException = ExceptionUtil.merge(e, stackTrace);
            mergedException.printStackTrace();
        });
        m.start();
    }

    public static void main(String[] args) throws Throwable {
        Main.class.getDeclaredMethod("execute").invoke(null);
    }
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionUtil {
    public static Throwable merge(Throwable throwable, StackTraceElement... threadStackTrace) {
        var chain = getThrowableList(throwable);

        for (var cause : chain) {
            var oldStackTrace = cause.getStackTrace();

            var newStackTrace = new ArrayList<StackTraceElement>();
            newStackTrace.addAll(Arrays.asList(oldStackTrace));
            newStackTrace.addAll(Arrays.asList(threadStackTrace));

            cause.setStackTrace(newStackTrace.toArray(new StackTraceElement[0]));
        }

        return throwable;
    }

    private static List<Throwable> getThrowableList(Throwable throwable) {
        final List<Throwable> list = new ArrayList<>();
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable);
            throwable = throwable.getCause();
        }
        return list;
    }
}

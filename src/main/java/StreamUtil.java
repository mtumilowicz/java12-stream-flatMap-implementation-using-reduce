import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2019-06-19.
 */
final class StreamUtil {
    static <T, R> Stream<R> flatMap(Stream<T> stream, Function<T, Stream<R>> mapper) {
        return stream.reduce(Stream.empty(), (acc, x) -> Stream.concat(acc, mapper.apply(x)), Stream::concat);
    }
}

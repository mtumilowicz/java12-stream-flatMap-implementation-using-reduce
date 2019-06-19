import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by mtumilowicz on 2019-06-19.
 */
public class StreamUtilTest {

    @Test
    public void flatMap_emptyStream() {
//        given
        var empty = Stream.empty();
        Function<Object, Stream<Object>> triple = x -> Stream.of(x, x, x);

//        when
        Stream<Object> tripled = StreamUtil.flatMap(empty, triple);

//        then
        assertThat(tripled.count(), is(0L));
    }

    @Test
    public void flatMap_single() {
//        given
        var empty = Stream.of(1);
        Function<Integer, Stream<Integer>> triple = x -> Stream.of(x, x, x);

//        when
        var tripledStream = StreamUtil.flatMap(empty, triple);

//        then
        assertThat(tripledStream.collect(Collectors.toList()), is(List.of(1, 1, 1)));
    }

    @Test
    public void flatMap_multiple() {
//        given
        var empty = Stream.of(1, 2, 3);
        Function<Integer, Stream<Integer>> triple = x -> Stream.of(x, x, x);

//        when
        var tripledStream = StreamUtil.flatMap(empty, triple);

//        then
        assertThat(tripledStream.collect(Collectors.toList()), is(List.of(1, 1, 1, 2, 2, 2, 3, 3, 3)));
    }
}
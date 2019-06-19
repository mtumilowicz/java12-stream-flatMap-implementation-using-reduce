[![Build Status](https://travis-ci.com/mtumilowicz/java12-stream-flatMap-implementation-using-reduce.svg?branch=master)](https://travis-ci.com/mtumilowicz/java12-stream-flatMap-implementation-using-reduce)
# java12-stream-flatMap-implementation-using-reduce

We will show how to implement stream's `flatMap` using only `reduce`.

Please refer also: https://github.com/mtumilowicz/java11-stream-map-filter-implementation-using-reduce

# project description
In the `Stream API` we have three reduce methods:
1. `Optional<T> reduce(BinaryOperator<T> accumulator)`
1. `T reduce(T identity, BinaryOperator<T> accumulator)`
1. `U reduce(U identity,
            BiFunction<U, ? super T, U> accumulator,
            BinaryOperator<U> combiner)`
                    
First two are inadequate as they return `Optional<T>` or
return `T`, which is the same type as declared in the given stream - but as an output we want a new type `U ~ Stream<T>`).

So we take third method and provide requested implementations:
```
static <T, R> Stream<R> flatMap(Stream<T> stream, Function<T, Stream<R>> mapper) {
    return stream.reduce(Stream.empty(), (acc, x) -> Stream.concat(acc, mapper.apply(x)), Stream::concat);
}
```

# tests
* in all of the following tests we will use: `Function<Object, Stream<Object>> triple = x -> Stream.of(x, x, x);`
* empty stream
    ```
    //        given
            var empty = Stream.empty();
            Function<Object, Stream<Object>> triple = x -> Stream.of(x, x, x);
    
    //        when
            Stream<Object> tripled = StreamUtil.flatMap(empty, triple);
    
    //        then
            assertThat(tripled.count(), is(0L));
    ```
* single value
    ```
    //        given
            var empty = Stream.of(1);
            Function<Integer, Stream<Integer>> triple = x -> Stream.of(x, x, x);
    
    //        when
            var tripledStream = StreamUtil.flatMap(empty, triple);
    
    //        then
            assertThat(tripledStream.collect(Collectors.toList()), is(List.of(1, 1, 1)));
    ```
* multiple values
    ```
    //        given
            var empty = Stream.of(1, 2, 3);
            Function<Integer, Stream<Integer>> triple = x -> Stream.of(x, x, x);
    
    //        when
            var tripledStream = StreamUtil.flatMap(empty, triple);
    
    //        then
            assertThat(tripledStream.collect(Collectors.toList()), is(List.of(1, 1, 1, 2, 2, 2, 3, 3, 3)));
    ```
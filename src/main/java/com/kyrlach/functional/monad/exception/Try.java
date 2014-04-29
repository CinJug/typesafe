package com.kyrlach.functional.monad.exception;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An algebraic representation of a value whos computation may result in an exception
 *
 * @param <A> the type of value represented
 */
public abstract class Try<A> {
	
	/**
	 * A type constructor that lifts a computation () -> Either<{bottom}, A> to 
	 * a computation () -> Try<A> and applies that computation
	 *
	 * @param <A> the result type
	 * @param body a computation that may potentially fail
	 * @return Success<A> \/ Failure<A>
	 */
	public static <A> Try<A> Try(final Supplier<A> body) {
		try {
			return new Success<A>(body.get());
		} catch(Throwable t) {
			return new Failure<A>(t);
		}
	}
	
	/**
	 * Simulates ML style pattern matching on the sum type Try<A> by applying the success
	 * function when this Try represents a success, or applying the failure function when
	 * this Try represents a failure.
	 *
	 * @param <B> the result type
	 * @param success a function to apply if this try is a success
	 * @param failure a function to apply if this try is a failure
	 * @return the result of the supplied function
	 */
	public abstract <B> B match(final Function<A, B> success, final Function<Throwable, B> failure);
	
	/**
	 * Lifts a function A -> B to Try<A> -> Try<B>, applying it if this try represents a Success<A>, or
	 * giving you back the Failure unmodified
	 *
	 * @param <B> the domain of the function f
	 * @param f the function to lift
	 * @return a Try<B> as a result of applying the function f
	 */
	public <B> Try<B> map(final Function<A, B> f) {
		return this.match(a -> new Success<B>(f.apply(a)), error -> new Failure<B>(error));
	}
	
	/**
	 * Lifts a function A -> Try<B> to Try<A> -> Try<B>, applying it if this try represents a Success<A>,
	 * or giving back the Failure unmodified
	 *
	 * @param <B> the domain of the function f
	 * @param f the function to lift
	 * @return a Try<B> as a result of applying the function f
	 */
	public <B> Try<B> flatMap(final Function<A, Try<B>> f) {
		return this.match(a -> f.apply(a), error -> new Failure<B>(error));
	}
}

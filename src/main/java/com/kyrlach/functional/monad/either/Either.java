package com.kyrlach.functional.monad.either;

import static com.kyrlach.functional.monad.either.Left.Left;
import static com.kyrlach.functional.monad.either.Right.Right;

import java.util.function.Function;

/**
 * Represents a disjoint union {@code A \u228E B} where {@code A} generally represents the undesirable outcome
 * and {@code B} represents the desired outcome.
 *
 * @param <A> the left (failure) type
 * @param <B> the right (success) type
 */
public abstract class Either<A, B> {
	
	/**
	 * Simulates ML style pattern matching on the disjoint union by applying the function {@code left} if this
	 * either represents a left value ({@code A}), or applying the function right if this either represents a
	 * right value ({@code B}), yielding some result ({@code C}).
	 *
	 * @param <C> the result type
	 * @param left a function to apply when this is a Left
	 * @param right a function to apply when this is a Right
	 * @return a computed result
	 */
	public abstract <C> C match(final Function<A, C> left, final Function<B, C> right);
	
	/**
	 * Lifts the function {@code B -> C} to {@code Either<A, B> -> Either<A, C>}, applying it to this either
	 * if it is a Right, or giving you back the Left unmodified.
	 *
	 * @param <C> the domain of the function {@code f}
	 * @param f the function to lift
	 * @return an {@code Either<A, C>} as a result of applying the lifted function
	 */
	public <C> Either<A, C> map(final Function<B, C> f) {
		return this.match(a -> Left(a), b -> Right(f.apply(b)));
	}
	
	/**
	 * Lifts the function {@code B -> Either<A, C>} to {@code Either<A, B> -> Either<A, C>} applying it to this either
	 * if it is a Right, or giving you back the Left unmodified.
	 *
	 * @param <C> the domain of the function {@code f}
	 * @param f the function to lift
	 * @return an {@code Either<A, C>} as a result of applying the lifted function
	 */
	public <C> Either<A, C> flatMap(final Function<B, Either<A, C>> f) {
		return this.match(a -> Left(a), b -> f.apply(b));
	}
	
	/**
	 * A left-biased map
	 *
	 * @param <C> the domain of the function {@code f}
	 * @param f the function to lift
	 * @return an {@code Either<C, B>} as a result of applying the lifted function
	 */
	public <C> Either<C, B> mapLeft(final Function<A, C> f) {
		return this.match(a -> Left(f.apply(a)), b -> Right(b));
	}
	
	/**
	 * A left-biased flatMap
	 *
	 * @param <C> the domain of the function {@code f}
	 * @param f the function to lift
	 * @return an {@code Either<C, B>} as a result of applying the lifted function
	 */
	public <C> Either<C, B> flatMapLeft(final Function<A, Either<C, B>> f) {
		return this.match(a -> f.apply(a), b -> Right(b));
	}	
}

package com.kyrlach.functional.monad.either;

import java.util.function.Function;

/**
 * Represents a value of type A lifted to Either<A,B>
 *
 * @param <A> the left (failure) type
 * @param <B> the right (success) type
 */
public final class Left<A, B> extends Either<A, B> {
	
	/**
	 * A type constructor that lifts an A to Either<A, B>
	 *
	 * @param <A> the left (failure) type
	 * @param <B> the right (success) type
	 * @param a the value to lift
	 * @return an Either<A, B> representing a Left<A, B>
	 */
	public static <A, B> Left<A, B> Left(A a) {
		return new Left<A, B>(a);
	}
	
	/** The lifted value. */
	private final A value;
	
	/**
	 * This constructor is private to ensure preservation of algebraic properties.
	 *
	 * @param a the value to lift
	 */
	private Left(final A a) {
		value = a;
	}

	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.either.Either#match(java.util.function.Function, java.util.function.Function)
	 */
	@Override
	public <C> C match(Function<A, C> left, Function<B, C> right) {
		return left.apply(value);
	}
}

package com.kyrlach.functional.monad.either;

import java.util.function.Function;

/**
 * Represents a value of type {@code B} lifted to {@code Either<A,B>}.
 *
 * @param <A> the left (failure) type
 * @param <B> the right (success) type
 */
public final class Right<A, B> extends Either<A, B> {
	
	/**
	 * A type constructor that lifts a {@code B} to {@code Either<A, B>}.
	 *
	 * @param <A> the left (failure) type
	 * @param <B> the right (success) type
	 * @param b the value to lift
	 * @return an {@code Either<A, B>} representing a {@code Right<A, B>}
	 */
	public static <A, B> Right<A, B> Right(final B b) {
		return new Right<A, B>(b);
	}
	
	/** The lifted value. */
	private final B value;
	
	/**
	 * This constructor is private to ensure that algebraic properties are preserved 
	 *
	 * @param b the b
	 */
	private Right(B b) {
		value = b;
	}

	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.either.Either#match(java.util.function.Function, java.util.function.Function)
	 */
	@Override
	public <C> C match(Function<A, C> left, Function<B, C> right) {
		return right.apply(value);
	}
}

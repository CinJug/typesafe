package com.kyrlach.functional.monad.exception;

import java.util.function.Function;

/**
 * This represents the result of {@code  () -> A \u228E Throwable} lifted to {@code Try<A>}.
 *
 * @param <A> the type of value produced by {@code () -> A \u228E Throwable} when successful
 */
public class Success<A> extends Try<A> {
	
	/** The lifted value. */
	private final A value;
	
	/**
	 * This constructor is package protected to preserve algebraic consistency
	 *
	 * @param a the a
	 */
	Success(A a) {
		value = a;
	}

	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.exception.Try#match(java.util.function.Function, java.util.function.Function)
	 */
	@Override
	public <B> B match(final Function<A, B> success, final Function<Throwable, B> failure) {
		return success.apply(value);
	}
}

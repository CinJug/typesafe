package com.kyrlach.functional.monad.exception;

import java.util.function.Function;

/**
 * Represents an error occurring from a {@code () -> A \u228E Throwable}.
 *
 * @param <A> the type of value that would have resulted from a successful computation
 */
public final class Failure<A> extends Try<A> {
	
	/** The error that arose from the computation {@code () -> A \u228E Throwable} */
	private final Throwable error;
	
	/**
	 * This constructor is package protected to preserve algebraic consistency
	 *
	 * @param t the t
	 */
	Failure(Throwable t) {
		error = t;
	}

	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.exception.Try#match(java.util.function.Function, java.util.function.Function)
	 */
	@Override
	public <B> B match(Function<A, B> success, Function<Throwable, B> failure) {
		return failure.apply(error);
	}
}

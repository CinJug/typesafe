package com.kyrlach.functional.monad.maybe;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a value lifted to {@code Maybe<A>}
 *
 * @param <A> the type of value lifted
 */
public final class Just<A> extends Maybe<A> {

	/** The lifted value */
	private final A value;
	
	/**
	 * This constructor is package protected to preserve algebraic consistency
	 *
	 * @param value the a
	 */
	Just(final A value)
	{
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.maybe.Maybe#match(java.util.function.Function, java.util.function.Supplier)
	 */
	@Override
	public <B> B match(final Function<A, B> just, final Supplier<B> nothing) {
		return just.apply(value);
	}

}
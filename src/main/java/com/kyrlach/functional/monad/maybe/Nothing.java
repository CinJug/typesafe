package com.kyrlach.functional.monad.maybe;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This represents the absence of a value lifted to Maybe<A>
 *
 * @param <A> the type of value to lift
 */
public final class Nothing<A> extends Maybe<A> {
	
	/**
	 * This constructor is package procted to preserve algebraic consistency
	 */
	Nothing() {}

	/* (non-Javadoc)
	 * @see com.kyrlach.functional.monad.maybe.Maybe#match(java.util.function.Function, java.util.function.Supplier)
	 */
	@Override
	public <B> B match(Function<A, B> just, Supplier<B> nothing) {
		return nothing.get();
	}		
}

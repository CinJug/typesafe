package com.kyrlach.functional.monad.maybe;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A sum type that represents a value that may be present, or may not be
 *
 * @param <A> the type of the optional value
 */
public abstract class Maybe<A> {
	
	/**
	 * A type constructor that lifts an A to a Maybe<A> 
	 *
	 * @param <A> the type of value to lift
	 * @param b the value to lift
	 * @return a Maybe<A> representing an optionally present value
	 */
	public static <A> Maybe<A> toMaybe(final A b)
	{
		if(b == null) {
			return new Nothing<A>();
		} else {
			return new Just<A>(b);
		}
	}
	
	/**
	 * Simulates ML style pattern matching on the union, applying the function just when this Maybe<A>
	 * is a Just<A>, or applying the function nothing when this Maybe<A> is a Nothing<A>
	 *
	 * @param <B> the result type
	 * @param just the function to apply when this is a Just<A>
	 * @param nothing the function to apply when this is a Nothing<a>
	 * @return the result of applying the appropriate function
	 */
	public abstract <B> B match(final Function<A, B> just, final Supplier<B> nothing);
	
	/**
	 * Lifts a function A -> B to Maybe<A> -> Maybe<B> and applies the function to this Maybe<A>. This
	 * yields a Just<B> when this is a Just<A>, or a Nothing<B> when this is a Nothing<A>
	 *
	 * @param <B> the domain of f
	 * @param f the function to lift
	 * @return the Maybe<B> as a result of applying the lifted function f
	 */
	public <B> Maybe<B> map(final Function<A, B> f)
	{
		return this.match(a -> toMaybe(f.apply(a)), () -> new Nothing<B>());
	}
	
	/**
	 * Lifts a function A -> Maybe<B> to Maybe<A> -> Maybe<B> and applies the function to this Maybe<A>.
	 * This yields a Just<B> when this is a Just<A>, or a Nothing<B> when this is a Nothing<A>
	 *
	 * @param <B> the domain of f
	 * @param f the function to lift
	 * @return the Maybe<A> as a result of applying the lifted function f
	 */
	public <B> Maybe<B> flatMap(final Function<A, Maybe<B>> f)
	{
		return this.match(a -> f.apply(a), () -> new Nothing<B>());
	}
	
	/**
	 * Yields the lifted value when this is a Just<A>, or the value provided by the supplier defaultValue
	 * when this is a Nothing<A> 
	 *
	 * @param defaultValue a supplier that provides an A to use when this is a Nothing<A>
	 * @return either the lifted value or the value supplied by defaultValue
	 */
	public A GetOrElse(final Supplier<A> defaultValue)
	{
		return this.match(a -> a, defaultValue);
	}
}

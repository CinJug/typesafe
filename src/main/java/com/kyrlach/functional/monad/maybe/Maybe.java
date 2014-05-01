package com.kyrlach.functional.monad.maybe;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A sum type that represents a value that may be present, or may not be.
 *
 * @param <A> the type of the optional value
 */
public abstract class Maybe<A> {
	
	/**
	 * A type constructor that lifts an {@code A} to a {@code Maybe<A>}.
	 *
	 * @param <A> the type of value to lift
	 * @param b the value to lift
	 * @return a {@code Maybe<A>} representing an optionally present value
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
	 * Simulates ML style pattern matching on the union, applying the function just when this {@code Maybe<A>}
	 * is a {@code Just<A>}, or applying the function nothing when this {@code Maybe<A>} is a {@code Nothing<A>}.
	 *
	 * @param <B> the result type
	 * @param just the function to apply when this is a {@code Just<A>}
	 * @param nothing the function to apply when this is a {@code Nothing<A>}
	 * @return the result of applying the appropriate function
	 */
	public abstract <B> B match(final Function<A, B> just, final Supplier<B> nothing);
	
	/**
	 * Lifts a function {@code A -> B} to {@code Maybe<A> -> Maybe<B>} and applies the function to this {@code Maybe<A>}. This
	 * yields a {@code Just<B>} when this is a {@code Just<A>}, or a {@code Nothing<B>} when this is a {@code Nothing<A>}.
	 *
	 * @param <B> the domain of {@code f}
	 * @param f the function to lift
	 * @return the {@code Maybe<B>} as a result of applying the lifted function {@code f}
	 */
	public <B> Maybe<B> map(final Function<A, B> f)
	{
		return this.match(a -> toMaybe(f.apply(a)), () -> new Nothing<B>());
	}
	
	/**
	 * Lifts a function {@code A -> Maybe<B>} to {@code Maybe<A> -> Maybe<B>} and applies the function to this {@code Maybe<A>}.
	 * This yields a {@code Just<B>} when this is a {@code Just<A>}, or a {@code Nothing<B>} when this is a {@code Nothing<A>}.
	 *
	 * @param <B> the domain of {@code f}
	 * @param f the function to lift
	 * @return the {@code Maybe<A>} as a result of applying the lifted function {@code f}
	 */
	public <B> Maybe<B> flatMap(final Function<A, Maybe<B>> f)
	{
		return this.match(a -> f.apply(a), () -> new Nothing<B>());
	}
	
	/**
	 * Yields the lifted value when this is a {@code Just<A>}, or the value provided by the supplier defaultValue
	 * when this is a {@code Nothing<A>}.
	 *
	 * @param defaultValue a supplier that provides an {@code A} to use when this is a {@code Nothing<A>}
	 * @return either the lifted value or the value supplied by {@code defaultValue}
	 */
	public A getOrElse(final Supplier<A> defaultValue)
	{
		return this.match(a -> a, defaultValue);
	}
}

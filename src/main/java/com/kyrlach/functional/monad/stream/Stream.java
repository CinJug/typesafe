package com.kyrlach.functional.monad.stream;

import static com.kyrlach.functional.monad.maybe.Maybe.toMaybe;

import java.util.function.Supplier;

import com.kyrlach.functional.monad.maybe.Maybe;

/**
 * This class represents a lazily evaluated sequence of values of type {@code A}.
 *
 * @param <A> the type of values contained in this lazy sequence
 */
public final class Stream<A> {
	
	/**
	 * This is a type constructor for an empty lazy sequence of type {@code A}.
	 *
	 * @param <A> the type of values that could be contained in this sequence
	 * @return an empty, lazy sequence
	 */
	public static <A> Stream<A> Empty() {
		return new Stream<A>(null, () -> Empty()); 
	}
	
	/**
	 * This is a type constructor for a lazy sequence of type {@code A} that prepends
	 * a value of type {@code A} to a lazy sequence of type {@code A}.
	 *
	 * @param <A> the type of value to prepend
	 * @param head the value you want to prepend to the sequence
	 * @param tail the sequence you want to prepend to
	 * @return a new lazy sequence with the value prepended
	 */
	public static <A> Stream<A> Cons(final A head, final Supplier<Stream<A>> tail) {
		return new Stream<A>(head, tail);
	}
		
	/** The head of this lazy sequence as a {@code Maybe}, since this sequence could be empty. */
	public final Maybe<A> headOption;
	
	/** The tail of this lazy sequence, which always returns an empty sequence for an empty sequence. */
	private final Supplier<Stream<A>> tail;
			
	/**
	 * This constructor is private to preserve algebraic consistency
	 *
	 * @param head the head
	 * @param tail the tail
	 */
	private Stream(A head, Supplier<Stream<A>> tail) {
		headOption = toMaybe(head);
		this.tail = tail;
	}
	
	/**
	 * Returns the tail of the lazy sequence, forcing evaluation of the next element.
	 *
	 * @return the tail
	 */
	public Stream<A> getTail() {
		return tail.get();
	}
}

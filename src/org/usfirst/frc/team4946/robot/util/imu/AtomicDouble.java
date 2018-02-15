package org.usfirst.frc.team4946.robot.util.imu;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble extends Number {
	private static final long serialVersionUID = 1L;

	AtomicLong bits;

	public AtomicDouble() {
		this(0);
	}

	public AtomicDouble(double initVal) {
		bits = new AtomicLong(Double.doubleToLongBits(initVal));
	}

	/**
	 * Gets the current value.
	 *
	 * @return the current value
	 */
	public final double get() {
		return Double.longBitsToDouble(bits.get());
	}

	/**
	 * Sets to the given value.
	 *
	 * @param newValue
	 *            the new value
	 */
	public final void set(double newValue) {
		bits.set(Double.doubleToLongBits(newValue));
	}

	/**
	 * Eventually sets to the given value.
	 *
	 * @param newValue
	 *            the new value
	 * @since 1.6
	 */
	public final void lazySet(double newValue) {
		bits.lazySet(Double.doubleToLongBits(newValue));
	}

	/**
	 * Atomically sets to the given value and returns the old value.
	 *
	 * @param newValue
	 *            the new value
	 * @return the previous value
	 */
	public final double getAndSet(double newValue) {
		return bits.getAndSet(Double.doubleToLongBits(newValue));
	}

	/**
	 * Atomically sets the value to the given updated value if the current value
	 * {@code ==} the expected value.
	 *
	 * @param expect
	 *            the expected value
	 * @param update
	 *            the new value
	 * @return {@code true} if successful. False return indicates that the actual
	 *         value was not equal to the expected value.
	 */
	public final boolean compareAndSet(double expect, double update) {
		return bits.compareAndSet(Double.doubleToLongBits(expect), Double.doubleToLongBits(update));
	}

	/**
	 * Atomically sets the value to the given updated value if the current value
	 * {@code ==} the expected value.
	 *
	 * <p>
	 * <a href="package-summary.html#weakCompareAndSet">May fail spuriously and does
	 * not provide ordering guarantees</a>, so is only rarely an appropriate
	 * alternative to {@code compareAndSet}.
	 *
	 * @param expect
	 *            the expected value
	 * @param update
	 *            the new value
	 * @return {@code true} if successful
	 */
	public final boolean weakCompareAndSet(double expect, double update) {
		return bits.weakCompareAndSet(Double.doubleToLongBits(expect), Double.doubleToLongBits(update));
	}

	/**
	 * Atomically increments by one the current value.
	 *
	 * @return the previous value
	 */
	public final double getAndIncrement() {
		return Double.longBitsToDouble(bits.getAndIncrement());
	}

	/**
	 * Atomically decrements by one the current value.
	 *
	 * @return the previous value
	 */
	public final double getAndDecrement() {
		return Double.longBitsToDouble(bits.getAndDecrement());
	}

	/**
	 * Atomically adds the given value to the current value.
	 *
	 * @param delta
	 *            the value to add
	 * @return the previous value
	 */
	public final double getAndAdd(double delta) {
		return Double.longBitsToDouble(bits.getAndAdd(Double.doubleToLongBits(delta)));
	}

	/**
	 * Atomically increments by one the current value.
	 *
	 * @return the updated value
	 */
	public final double incrementAndGet() {
		return Double.longBitsToDouble(bits.incrementAndGet());
	}

	/**
	 * Atomically decrements by one the current value.
	 *
	 * @return the updated value
	 */
	public final double decrementAndGet() {
		return Double.longBitsToDouble(bits.decrementAndGet());
	}

	/**
	 * Atomically adds the given value to the current value.
	 *
	 * @param delta
	 *            the value to add
	 * @return the updated value
	 */
	public final double addAndGet(double delta) {
		return Double.longBitsToDouble(bits.addAndGet(Double.doubleToLongBits(delta)));
	}

	@Override
	public int intValue() {
		return (int) get();
	}

	@Override
	public long longValue() {
		return (long) get();
	}

	@Override
	public float floatValue() {
		return (float) get();
	}

	@Override
	public double doubleValue() {
		return get();
	}

	/**
	 * Returns the String representation of the current value.
	 * 
	 * @return the String representation of the current value
	 */
	public String toString() {
		return Double.toString(get());
	}

}

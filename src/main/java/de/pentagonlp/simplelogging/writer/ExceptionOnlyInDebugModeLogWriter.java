package de.pentagonlp.simplelogging.writer;

import de.pentagonlp.simplelogging.Log;
import de.pentagonlp.simplelogging.LogWriter;

/**
 * Super class for all writers that only print {@link Throwable Throwables} in
 * debug mode. This feature can be turned on and off using
 * {@link ExceptionOnlyInDebugModeLogWriter#setExceptionOnlyInDebugMode(boolean)}.
 * 
 * @author PentagonLP
 */
public abstract class ExceptionOnlyInDebugModeLogWriter implements LogWriter {

	/**
	 * Default value whether {@link Throwable Throwables} should only be written in
	 * debug mode
	 * 
	 * @see ExceptionOnlyInDebugModeLogWriter#setExceptionOnlyInDebugMode(boolean)
	 */
	private static final boolean DEFAULTEXCEPTIONONLYINDEBUGMODE = true;

	/**
	 * If {@code true}, {@link Throwable Throwables} are only written in debug mode.
	 * If {@code false}, {@link Throwable Throwables} are always written
	 * 
	 * @see ExceptionOnlyInDebugModeLogWriter#setExceptionOnlyInDebugMode(boolean)
	 */
	private boolean exceptiononlyindebugmode;

	/**
	 * Creates a new {@link ExceptionOnlyInDebugModeLogWriter}, implementing a check
	 * whether {@link Throwable Throwables} should only be written in debug mode.
	 */
	public ExceptionOnlyInDebugModeLogWriter() {
		this(DEFAULTEXCEPTIONONLYINDEBUGMODE);
	}

	/**
	 * Creates a new {@link ExceptionOnlyInDebugModeLogWriter}, implementing a check
	 * whether {@link Throwable Throwables} should only be written in debug mode.
	 * {@code exceptiononlyindebugmode} describes, whether the check is active or
	 * {@link Throwable Throwables} should also be written if {@link Log} id not in
	 * debug mode.
	 * 
	 * @param exceptiononlyindebugmode {@code true} if the check should be enabled,
	 *                                 {@code false} if disabled
	 * @see ExceptionOnlyInDebugModeLogWriter#setExceptionOnlyInDebugMode(boolean)
	 */
	public ExceptionOnlyInDebugModeLogWriter(boolean exceptiononlyindebugmode) {
		this.exceptiononlyindebugmode = exceptiononlyindebugmode;
	}

	/**
	 * Override {@link LogWriter#printStackTrace(Throwable)}, to implement the check
	 * if needed.
	 * 
	 * @param t the {@link Throwable} to print the {@code StackTrace} of
	 * @see {@link ExceptionOnlyInDebugModeLogWriter#printStackTraceAfterCheck(Exception)}
	 *      <i>the method called if the check was successful and the
	 *      {@code StackTrace} should be printed by the {@link LogWriter} extending
	 *      {@link ExceptionOnlyInDebugModeLogWriter}</i>
	 */
	@Override
	public final void printStackTrace(Throwable e) {
		if (!Log.isDebugmode() && exceptiononlyindebugmode)
			return;
		printStackTraceAfterCheck(e);
	}

	/**
	 * The method called if the check was successful and the {@code StackTrace}
	 * should be printed by the {@link LogWriter} extending
	 * {@link ExceptionOnlyInDebugModeLogWriter}
	 * 
	 * @param t the {@link Throwable} to print the {@code StackTrace} of
	 */
	protected abstract void printStackTraceAfterCheck(Throwable t);

	/**
	 * Get whether the check is active.
	 * 
	 * @return {@code boolean}: If {@code true}, {@link Throwable Throwables} are
	 *         only written in debug mode. If {@code false}, {@link Throwable
	 *         Throwables} are always written
	 * @see ExceptionOnlyInDebugModeLogWriter#setExceptionOnlyInDebugMode(boolean)
	 */
	public boolean getExceptionOnlyInDebugMode() {
		return exceptiononlyindebugmode;
	}

	/**
	 * Set whether the check is active.
	 * 
	 * @param exceptiononlyindebugmode If {@code true}, {@link Throwable Throwables}
	 *                                 are only written in debug mode. If
	 *                                 {@code false}, {@link Throwable Throwables}
	 *                                 are always written
	 * @see ExceptionOnlyInDebugModeLogWriter#getExceptionOnlyInDebugMode()
	 */
	public void setExceptionOnlyInDebugMode(boolean exceptiononlyindebugmode) {
		this.exceptiononlyindebugmode = exceptiononlyindebugmode;
	}

}

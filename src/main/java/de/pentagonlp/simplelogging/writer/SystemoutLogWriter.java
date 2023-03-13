package de.pentagonlp.simplelogging.writer;

import de.pentagonlp.simplelogging.ansi.AnsiColor;

/**
 * {@link de.pentagonlp.simplelogging.LogWriter LogWriter} to write logged
 * messages to the {@link System#out} {@link java.io.PrintStream PrintStream}
 * 
 * @author PentagonLP
 */
public class SystemoutLogWriter extends ExceptionOnlyInDebugModeLogWriter {

	/**
	 * Creates a new {@link SystemoutLogWriter}, using the default setting whether
	 * {@link ExceptionOnlyInDebugModeLogWriter} is active and only writes
	 * {@link Exception Exceptions} in debug mode
	 */
	public SystemoutLogWriter() {
		super();
	}

	/**
	 * Creates a new {@link SystemoutLogWriter}, specifying the setting whether
	 * {@link ExceptionOnlyInDebugModeLogWriter} is active and only writes
	 * {@link Exception Exceptions} in debug mode
	 * 
	 * @param exceptiononlyindebugmode if {@code true}, {@link Exception Exceptions}
	 *                                 are only written in debug mode. If
	 *                                 {@code false}, {@link Exception Exceptions}
	 *                                 are always written
	 */
	public SystemoutLogWriter(boolean exceptiononlyindebugmode) {
		super(exceptiononlyindebugmode);
	}

	/**
	 * Write a logged message to the {@link System#out} {@link java.io.PrintStream
	 * PrintStream}
	 * 
	 * @param logtext the message to write, as a {@link String}
	 */
	@Override
	public void write(String logtext) {
		System.out.println(logtext);
	}

	/**
	 * Get whether he {@link System#out} {@link java.io.PrintStream PrintStream}
	 * supports ansi. Determined by calling {@link AnsiColor#isSystemOutAnsi()}.
	 * 
	 * @return {@code true} if ansi colors are supported, {@code false} if ansi
	 *         colors are not supported
	 */
	@Override
	public boolean isAnsi() {
		return AnsiColor.isSystemOutAnsi();
	}

	/**
	 * Print the {@code StackTrace} of a {@link Throwable}, after
	 * {@link ExceptionOnlyInDebugModeLogWriter} has decided its criteria are
	 * fulfilled
	 * 
	 * @param e the {@link Throwable} to print the {@code StackTrace} of
	 */
	@Override
	protected void printStackTraceAfterCheck(Throwable t) {
		t.printStackTrace();
	}

}

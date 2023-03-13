package de.pentagonlp.simplelogging;

/**
 * Interface for a writer to write log messages to a log output.
 * 
 * @author PentagonLP
 */
public interface LogWriter {

	/**
	 * Write a {@link String} to the log output
	 * 
	 * @param logtext the {@link String} to write to the log output.
	 */
	public void write(String logtext);

	/**
	 * Get whether the log output supports ansi.
	 * 
	 * @return {@code true} if it supports ansi, {@code false} if it doesn't
	 */
	public boolean isAnsi();

	/**
	 * Write a {@code StackTrace} to the log output, given by an {@link Throwable}
	 * 
	 * @param t the {@link Throwable} from which to write the {@code StackTrace}
	 */
	public void printStackTrace(Throwable t);

}

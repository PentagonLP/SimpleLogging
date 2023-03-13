package de.pentagonlp.simplelogging;

/**
 * {@link LogFormatter} and {@link LogWriter}, combined in one object. Method
 * calls are a bit different for the methods normally defined in
 * {@link LogFormatter} as {@link LogFormatter} does already know whether
 * {@link LogWriter} supports ansi (Just call
 * {@link LogFormatterAndWriter#isAnsi()}).
 * 
 * @author PentagonLP
 */
public interface LogFormatterAndWriter extends LogWriter {

	/**
	 * Format a logged message given by a {@link LogInfo} object in the format
	 * defined by the {@link LogFormatter}. No need for {@code isansi} parameter as
	 * {@link LogFormatter} already knows whether {@link LogWriter} supports ansi.
	 * 
	 * @param info the {@link LogInfo} object, containing all information about the
	 *             message to be formatted
	 * @return the message formatted in the default format, to be written in the
	 *         output
	 */
	public String format(LogInfo info);

	/**
	 * Get the message which is written to the log, if a logged action didn't go
	 * through because the program runs in sandbox mode No need for {@code isansi}
	 * parameter as {@link LogFormatter} already knows whether {@link LogWriter}
	 * supports ansi.
	 * 
	 * @see Log#setSandboxmode(boolean)
	 */
	public String getSandboxWarning();

	/**
	 * Get the message which is written to the log, if sandbox mode is enabled on
	 * startup, or was enabled or disabled during execution of the program. No need
	 * for {@code isansi} parameter as {@link LogFormatter} already knows whether
	 * {@link LogWriter} supports ansi.
	 * 
	 * @param enabled {@code true} if sandbox mode was enabled, {@code false} if
	 *                sandbox mode was disabled
	 * @return the sandbox mode enable/disable message
	 */
	public String getStartupSandboxWarning(boolean enabled);

	/**
	 * Get the message which is written to the log, if debug mode is enabled on
	 * startup, or was enabled or disabled during execution of the program. No need
	 * for {@code isansi} parameter as {@link LogFormatter} already knows whether
	 * {@link LogWriter} supports ansi.
	 * 
	 * @param enabled {@code true} if debug mode was enabled, {@code false} if debug
	 *                mode was disabled
	 * @return the debug mode enable/disable message
	 */
	public String getStartupDebugWarning(boolean enabled);

}

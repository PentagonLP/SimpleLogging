package de.pentagonlp.simplelogging;

/**
 * Interface for {@link Logger} to format a message to be logged.
 */
public interface LogFormatter {

	/**
	 * Format a logged message given by a {@link LogInfo} object in the format
	 * defined by the {@link LogFormatter}.
	 * 
	 * @param info   the {@link LogInfo} object, containing all information about
	 *               the message to be formatted
	 * @param isAnsi whether the output the formatted message is written to supports
	 *               ansi formatting codes. If {@code false}, no ansi formatting
	 *               codes will be used in the formatted message.
	 * @return the message formatted in the default format, to be written in the
	 *         output
	 */
	public String format(LogInfo info, boolean isAnsi);

	/**
	 * Get the message which is written to the log, if a logged action didn't go
	 * through because the program runs in sandbox mode
	 * 
	 * @param isAnsi whether the output the sandbox warning message is written to
	 *               supports ansi formatting codes. If {@code false}, no ansi
	 *               formatting codes will be used in the sandbox warning message.
	 * @see Log#setSandboxmode(boolean)
	 */
	public String getSandboxWarning(boolean isAnsi);

	/**
	 * Get the message which is written to the log, if sandbox mode is enabled on
	 * startup, or was enabled or disabled during execution of the program.
	 * 
	 * @param isAnsi  whether the output the sandbox mode enable/disable message is
	 *                written to supports ansi formatting codes. If {@code false},
	 *                no ansi formatting codes will be used in the sandbox mode
	 *                enable/disable message.
	 * @param enabled {@code true} if sandbox mode was enabled, {@code false} if
	 *                sandbox mode was disabled
	 * @return the sandbox mode enable/disable message
	 */
	public String getStartupSandboxWarning(boolean isAnsi, boolean enabled);

	/**
	 * Get the message which is written to the log, if debug mode is enabled on
	 * startup, or was enabled or disabled during execution of the program.
	 * 
	 * @param isAnsi  whether the output the debug mode enable/disable message is
	 *                written to supports ansi formatting codes. If {@code false},
	 *                no ansi formatting codes will be used in the debug mode
	 *                enable/disable message.
	 * @param enabled {@code true} if debug mode was enabled, {@code false} if debug
	 *                mode was disabled
	 * @return the debug mode enable/disable message
	 */
	public String getStartupDebugWarning(boolean isAnsi, boolean enabled);

}

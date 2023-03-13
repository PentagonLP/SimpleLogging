package de.pentagonlp.simplelogging.formatter;

import java.util.Calendar;

import de.pentagonlp.simplelogging.Level;
import de.pentagonlp.simplelogging.Log;
import de.pentagonlp.simplelogging.LogFormatter;
import de.pentagonlp.simplelogging.LogInfo;
import de.pentagonlp.simplelogging.ansi.AnsiColor;
import de.pentagonlp.simplelogging.tools.DateformatterStorage;

/**
 * {@link LogFormatter} for the default format for log entries. Used by
 * {@link Log} if no other log formatter is configured.
 * 
 * @author PentagonLP
 */
public class DefaultLogFormatter implements LogFormatter {

	/**
	 * Format a logged message given by a {@link LogInfo} object in the default
	 * format.
	 * 
	 * @param info   the {@link LogInfo} object, containing all information about
	 *               the message to be formatted
	 * @param isAnsi whether the output the formatted message is written to supports
	 *               ansi formatting codes. If {@code false}, no ansi formatting
	 *               codes will be used in the formatted message.
	 * @return the message formatted in the default format, to be written in the
	 *         output
	 */
	@Override
	public String format(LogInfo info, boolean isAnsi) {
		// Debug messages are only printed if the program runs in debug mode
		if (!Log.isDebugmode() && info.getLevel().getName().equals("DEBUG"))
			return null;

		Calendar cal = Calendar.getInstance();
		if (isAnsi)
			// Message with ansi formatting
			return AnsiColor.WHITE + "["
					+ DateformatterStorage.DATEWITHDOTHOURMINUTESECONDWITHCOLON24.format(cal.getTime()) + " - "
					+ info.getLevel().getColoredName() + AnsiColor.WHITE + "] > " + info.getLevel().getColor()
					+ replaceIfNotNull(info.getClassname(),
							AnsiColor.ITALIC + info.getClassname() + AnsiColor.ITALIC_OFF + ": ")
					+ info.getMsg() + AnsiColor.WHITE;
		else
			// Message without ansi formatting
			return "[" + DateformatterStorage.DATEWITHDOTHOURMINUTESECONDWITHCOLON24.format(cal.getTime()) + " - "
					+ info.getLevel().getName() + "] > "
					+ replaceIfNotNull(info.getClassname(), info.getClassname() + ": ") + info.getMsg();
	}

	/**
	 * Get the default message which is written to the log, if a logged action
	 * didn't go through because the program runs in sandbox mode
	 * 
	 * @param isAnsi whether the output the sandbox warning message is written to
	 *               supports ansi formatting codes. If {@code false}, no ansi
	 *               formatting codes will be used in the sandbox warning message.
	 */
	@Override
	public String getSandboxWarning(boolean isAnsi) {
		return format(new LogInfo("The last logged action didn't go through, because we are in sandbox mode.",
				Level.WARNING, "Logger"), isAnsi);
	}

	/**
	 * Get the default message which is written to the log, if sandbox mode is
	 * enabled on startup, or was enabled or disabled during execution of the
	 * program.
	 * 
	 * @param isAnsi  whether the output the sandbox mode enable/disable message is
	 *                written to supports ansi formatting codes. If {@code false},
	 *                no ansi formatting codes will be used in the sandbox mode
	 *                enable/disable message.
	 * @param enabled {@code true} if sandbox mode was enabled, {@code false} if
	 *                sandbox mode was disabled
	 * @return the sandbox mode enable/disable message
	 */
	@Override
	public String getStartupSandboxWarning(boolean isAnsi, boolean enabled) {
		if (enabled)
			return format(new LogInfo(Log.getProgramname() + " runs in sandbox mode!", Level.WARNING, "Logger"),
					isAnsi);
		else
			return format(
					new LogInfo(Log.getProgramname() + " no longer runs in sandbox mode!", Level.WARNING, "Logger"),
					isAnsi);
	}

	/**
	 * Get the default message which is written to the log, if debug mode is enabled
	 * on startup, or was enabled or disabled during execution of the program.
	 * 
	 * @param isAnsi  whether the output the debug mode enable/disable message is
	 *                written to supports ansi formatting codes. If {@code false},
	 *                no ansi formatting codes will be used in the debug mode
	 *                enable/disable message.
	 * @param enabled {@code true} if debug mode was enabled, {@code false} if debug
	 *                mode was disabled
	 * @return the debug mode enable/disable message
	 */
	@Override
	public String getStartupDebugWarning(boolean isAnsi, boolean enabled) {
		if (enabled)
			return format(new LogInfo(Log.getProgramname() + " runs in debug mode!", Level.WARNING, "Logger"), isAnsi);
		else
			return format(new LogInfo(Log.getProgramname() + " no longer runs in debug mode!", Level.WARNING, "Logger"),
					isAnsi);
	}

	/**
	 * Replace a {@link String} with another {@link String} if the {@link String} to
	 * replace is not {@code null}
	 * 
	 * @param stringtotest      the {@link String} to replace. If {@code null}, an
	 *                          empty {@link String} is returned. If not
	 *                          {@code null}, {@code replacementstring} is returned.
	 * @param replacementstring the {@link String} returned if {@code stringtotest}
	 *                          is not null
	 * @return If {@code stringtotest} is {@code null}, an empty {@link String} is
	 *         returned. If {@code stringtotest} is not {@code null},
	 *         {@code replacementstring} is returned.
	 */
	private static String replaceIfNotNull(String stringtotest, String replacementstring) {
		if (stringtotest == null)
			return "";
		return replacementstring;
	}

}

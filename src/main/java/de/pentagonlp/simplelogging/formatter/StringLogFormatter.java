package de.pentagonlp.simplelogging.formatter;

import java.util.Calendar;

import de.pentagonlp.simplelogging.LogInfo;
import de.pentagonlp.simplelogging.ansi.AnsiColor;
import de.pentagonlp.simplelogging.tools.DateformatterStorage;

/**
 * {@link org.craftyserver.general.log.LogFormatter LogFormatter} that takes in
 * a {@link String} with different variables in it as a format. <br>
 * A list of usable variables can be found at
 * {@link StringLogFormatter#format(LogInfo, boolean)}.
 * <p>
 * <b>Variable format:</b> <br>
 * {@code %varname%}
 * <p>
 * <b>Example:</b> <br>
 * {@code StringLogFormatter.format(new LogInfo("%date%", mylevel, myclassname), myansisupportinfo);}
 * <br>
 * <i>(outputs the current date)
 *
 * @author PentagonLP
 * @see {@link StringLogFormatter#format(LogInfo, boolean)} for a list of usable
 *      variables
 */
public class StringLogFormatter extends DefaultLogFormatter {

	/**
	 * Example for a format. Using this as the format will output an almost similar
	 * log message as the {@link DefaultLogFormatter}.
	 */
	public static final String DEFAULTFORMAT = "%ansi:WHITE%[%date%|%time% - %levelcolor%%level%%ansi:WHITE%] > %levelcolor%%ansi:ITALIC%%classname%%ansi:ITALIC_OFF%: %msg%%ansi:WHITE%";

	/**
	 * The format {@link String} used by the {@link StringLogFormatter}
	 * 
	 * @see {@link StringLogFormatter#format(LogInfo, boolean)} for a list of
	 *      variables that can be used in the format {@link String}.
	 */
	private final String format;

	/**
	 * Creates a {@link StringLogFormatter}.
	 * 
	 * @param format the {@link String} specifying the format of a log message by
	 *               the {@link StringLogFormatter}
	 * @throws IllegalArgumentException if the format {@link String} is {@code null}
	 * 
	 * @see {@link StringLogFormatter#format(LogInfo, boolean)} for a list of
	 *      variables that can be used in the format {@link String}.
	 */
	public StringLogFormatter(String format) {
		if (format == null)
			throw new IllegalArgumentException("Format String can not be null!");
		this.format = format;
	}

	/**
	 * Format a logged message given by a {@link LogInfo} object in the format
	 * specified by the format {@link String}.
	 * <p>
	 * The following variable can be used in the format {@link String}:
	 * <p>
	 * {@code %date%} - <i>Outputs the current date in a {@code dd.mm.yyyy}
	 * format</i> <br>
	 * {@code %time%} - <i>Outputs the current time in a {@code HH:mm:ss} format</i>
	 * <br>
	 * {@code %level%} - <i>Outputs the name of the log messages'
	 * {@link org.craftyserver.general.log.Level Level}</i> <br>
	 * {@code %levelcolor%} - <i>Only if output supports ansi formatting codes:
	 * Outputs the ansi formatting code for the
	 * {@link org.craftyserver.general.log.Level Levels}' ansi color. If ansi
	 * formatting codes are not supported, {@code %levelcolor%} will be replaced
	 * with an empty {@link String}.</i> <br>
	 * {@code %classname%} - <i>Outputs the name of the class, registered in
	 * {@link org.craftyserver.general.log.Log Log}, that called for the message to
	 * be logged. If no classname is registered for the class, {@code %classname%}
	 * will be replaced with an empty {@link String}.</i> <br>
	 * {@code %msg%} - <i>Outputs the given message to be logged</i> <br>
	 * {@code %ansi:[stylename]%} - <i>Only if output supports ansi formatting
	 * codes: Outputs a specific ansi formatting code, e.g. {@code %ansi:RED%} will
	 * set the textcolor to red. If ansi formatting codes are not supported,
	 * {@code %ansi:[stylename]%} will be replaced with an empty {@link String}.</i>
	 * 
	 * @param info   the {@link LogInfo} object, containing all information about
	 *               the message to be formatted
	 * @param isAnsi whether the output the formatted message is written to supports
	 *               ansi formatting codes. If {@code false}, no ansi formatting
	 *               codes will be used in the formatted message.
	 * @return the message formatted in the format specified by the format
	 *         {@link String}, to be written in the output
	 * 
	 */
	@Override
	public final String format(LogInfo info, boolean isAnsi) {
		String result = new String(format);
		Calendar cal = Calendar.getInstance();

		result = result.replaceAll("%date%", DateformatterStorage.DATEWITHDOT.format(cal.getTime()));
		result = result.replaceAll("%time%", DateformatterStorage.HOURMINUTESECONDWITHCOLON24.format(cal.getTime()));

		if (info.getClassname() != null)
			result = result.replaceAll("%classname%", info.getClassname());
		else
			result = result.replaceAll("%classname%", "");

		result = result.replaceAll("%level%", info.getLevel().getName());

		if (!isAnsi)
			result = result.replaceAll("%levelcolor%", "");
		else
			result = result.replaceAll("%levelcolor%", info.getLevel().getColor().toString());

		result = result.replaceAll("%msg%", info.getMsg());

		for (AnsiColor color : AnsiColor.values()) {
			if (isAnsi)
				result = result.replaceAll("%ansi:" + color.getName() + "%", color.toString());
			else
				result = result.replaceAll("%ansi:" + color.getName() + "%", "");
		}

		return result;
	}

}

package de.pentagonlp.simplelogging.ansi;

/**
 * Enum of different ansi colors and formats
 * <p>
 * <b>Usage:</b> <br>
 * {@code System.out.println(AnsiColors.MY_ANSI_COLOR + "my formatted text");}
 * <br>
 * <b>Example:</b> <br>
 * {@code System.out.println(AnsiColors.GREEN + "This text will be printed in green!");}
 * 
 * @author PentagonLP
 */
// TODO implement more formatting codes, full list: https://stackoverflow.com/questions/4842424/list-of-ansi-color-escape-sequences
public enum AnsiColor {

	/**
	 * This format code is the {@code null} object and will change absolutely
	 * nothing.
	 */
	NOCHANGE(""),
	/**
	 * This format code will turn off all attributes.
	 */
	RESET("\u001B[0m"),
	/**
	 * This format code will set the text color to black.
	 */
	BLACK("\u001B[30m"),
	/**
	 * This format code will set the text color to red.
	 */
	RED("\u001B[31m"),
	/**
	 * This format code will set the text color to green.
	 */
	GREEN("\u001B[32m"),
	/**
	 * This format code will set the text color to yellow.
	 */
	YELLOW("\u001B[33m"),
	/**
	 * This format code will set the text color to blue.
	 */
	BLUE("\u001B[34m"),
	/**
	 * This format code will set the text color to purple.
	 */
	PURPLE("\u001B[35m"),
	/**
	 * This format code will set the text color to cyan.
	 */
	CYAN("\u001B[36m"),
	/**
	 * This format code will set the text color to white.
	 */
	WHITE("\u001B[37m"),

	/**
	 * This format code will change the text style to italic.
	 */
	ITALIC("\u001B[3m"),
	/**
	 * This format code will turn the italic text style off.
	 */
	ITALIC_OFF("\u001B[23m");

	/**
	 * Specifies if ansi colors for {@codeSystem.out} are enabled by default
	 */
	private static final boolean DEFAULTSYSTEMOUTANSI = false;
	/**
	 * Specifies if ansi colors for {@codeSystem.out} are enabled. Can be changed
	 * using {@link AnsiColor#setSystemOutAnsi(boolean)}.
	 */
	private static boolean SystemOutAnsi = DEFAULTSYSTEMOUTANSI;

	/**
	 * Format code for the color, given at initialization. Can be read using
	 * {@link AnsiColor#getEscapeCode()} or {@link AnsiColor#toString()}.
	 */
	private final String escapecode;

	/**
	 * Creates a new {@link AnsiColor}, with a specific escape code.
	 * 
	 * @param escapecode the escape code of the text style or color.
	 */
	private AnsiColor(String escapecode) {
		this.escapecode = escapecode;
	}

	/**
	 * Get the escape code of the color. Putting this in a {@link String} will
	 * change the text style or color for all following characters. <br>
	 * Text style can be reset by using {@link AnsiColor#RESET}.
	 * 
	 * @return the escape code of the text style or color
	 */
	public String getEscapeCode() {
		return escapecode;
	}

	/**
	 * Get the escape code of the color. Putting this in a {@link String} will
	 * change the text style or color for all following characters. <br>
	 * Text style can be reset by using {@link AnsiColor#RESET}. <br>
	 * Note that {@code AnsiColor.toString()} <b>does not return the name of the
	 * enum element</b>, but the escape code of the text style or color.
	 * <p>
	 * It is better to call {@link AnsiColor#getEscapeCode()}, as they do the same
	 * thing and lesser method calls will take place. This is method only exists to
	 * easily use the escape code in a {@link String}, e.g. by using
	 * <p>
	 * {@code System.out.println(AnsiColors.MY_ANSI_COLOR + "my formatted text");}
	 * 
	 * @return the escape code of the text style or color
	 * 
	 * @see {@link AnsiColor#getName()} to get the name of the enum element
	 */
	@Override
	public String toString() {
		return getEscapeCode();
	}

	/**
	 * Get the name of the enum element.
	 * <p>
	 * Note that {@link AnsiColor#toString()} does not return the name of the enum
	 * element, but the escape code of the text style or color.
	 * 
	 * @return the name of the enum element
	 */
	public String getName() {
		return super.toString();
	}

	/**
	 * Get whether ansi colors are supported by {@code System.out}
	 * 
	 * @return {@code true} if ansi colors are supported <br>
	 *         {@code false} if ansi colors are not supported
	 */
	public static boolean isSystemOutAnsi() {
		return SystemOutAnsi;
	}

	/**
	 * Set whether ansi colors are supported by {@code System.out}
	 * 
	 * @param systemOutAnsi {@code true} if ansi colors are supported, {@code false}
	 *                      if ansi colors are not supported
	 */
	public static void setSystemOutAnsi(boolean systemOutAnsi) {
		SystemOutAnsi = systemOutAnsi;
	}

	/**
	 * Only return the color given by {@code color} if ansi colors are supported by
	 * {@code System.out}. If not, the empty formatting code
	 * {@link AnsiColor#NOCHANGE} is returned.
	 * 
	 * @param color the color which is returned if ansi colors are supported
	 */
	public static AnsiColor getAnsiIfSystemOutAnsi(AnsiColor color) {
		if (!SystemOutAnsi)
			return NOCHANGE;
		return color;
	}
}

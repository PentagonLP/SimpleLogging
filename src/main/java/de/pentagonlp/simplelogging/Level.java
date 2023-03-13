package de.pentagonlp.simplelogging;

import de.pentagonlp.simplelogging.ansi.AnsiColor;

/**
 * The levels used for logging.
 * 
 * @author PentagonLP
 */
public class Level {

	/**
	 * Logging level {@code FATAL} should be used if a major error occurs that lead
	 * to a shutdown of the entire program.
	 */
	public static final Level FATAL = new Level("FATAL", 100, AnsiColor.RED);
	/**
	 * Logging level {@code ERROR} should be used if an error occurs that leads to a
	 * feature of the program not working.
	 */
	public static final Level ERROR = new Level("ERROR", 200, AnsiColor.PURPLE);
	/**
	 * Logging level {@code WARNING} should be used if an error occurs, but the a
	 * fallback solution is available to be executed.
	 */
	public static final Level WARNING = new Level("WARNING", 300, AnsiColor.YELLOW);
	/**
	 * Logging level {@code INFO} should be used if no error is reported, but rather
	 * some information about what the program is currently doing is logged.
	 */
	public static final Level INFO = new Level("INFO", 400);
	/**
	 * Logging level {@code DEBUG} should be used if no error is reported, but
	 * rather some information about what the program is currently doing, but is not
	 * relevant to the user in normal operation, is logged.
	 */
	public static final Level DEBUG = new Level("DEBUG", 500, AnsiColor.GREEN);

	/**
	 * Name of the Level
	 */
	private final String name;
	/**
	 * Priority compared to other {@link Level Levels}, as a number
	 */
	private final int intLevel;

	/**
	 * The {@link AnsiColor} used for logged messages for this {@link Level},
	 * assuming the log output supports ansi formatting codes
	 */
	private AnsiColor color;

	/**
	 * Creates a new {@link Level} with the default {@link AnsiColor},
	 * {@link AnsiColor#WHITE}.
	 * 
	 * @param name     the written name of the {@link Level}
	 * @param intLevel the priority of this {@link Level}, compared to other
	 *                 {@link Level Levels}, as a number
	 */
	public Level(String name, int intLevel) {
		this.name = name;
		this.color = AnsiColor.WHITE;
		this.intLevel = intLevel;
	}

	/**
	 * Creates a new {@link Level} with a given {@link AnsiColor}.
	 * 
	 * @param name     the written name of the {@link Level}
	 * @param intLevel the priority of this {@link Level}, compared to other
	 *                 {@link Level Levels}, as a number
	 * @param color    the {@link AnsiColor} used for logged messages for this
	 *                 {@link Level}, assuming the log output supports ansi
	 *                 formatting codes
	 */
	public Level(String name, int intLevel, AnsiColor color) {
		this.name = name;
		this.color = color;
		this.intLevel = intLevel;
	}

	/**
	 * Get the written name of the {@link Level}
	 * 
	 * @return the written name of the {@link Level}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the written name of the {@link Level}, formatted with the
	 * {@link AnsiColor} used for logged messages for this {@link Level}.
	 * 
	 * @return the colored, written name of the {@link Level}
	 */
	public String getColoredName() {
		return color + name;
	}

	/**
	 * Get the priority of this {@link Level}, compared to other {@link Level
	 * Levels}, as a number.
	 */
	public int getIntLevel() {
		return intLevel;
	}

	/**
	 * Get the {@link AnsiColor} used for logged messages for this {@link Level},
	 * assuming the log output supports ansi formatting codes.
	 * 
	 * @return the {@link AnsiColor} used for logged messages for this {@link Level}
	 */
	public AnsiColor getColor() {
		return color;
	}

	/**
	 * Set the {@link AnsiColor} used for logged messages for this {@link Level},
	 * assuming the log output supports ansi formatting codes.
	 * 
	 * @param color the {@link AnsiColor} that shall be used for logged messages for
	 *              this {@link Level}
	 */
	public void setColor(AnsiColor color) {
		this.color = color;
	}

}

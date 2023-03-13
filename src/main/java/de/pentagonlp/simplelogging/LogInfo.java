package de.pentagonlp.simplelogging;

/**
 * Object containing all information about a message about to be logged.
 * 
 * @author PentagonLP
 */
public class LogInfo {

	/**
	 * The messages {@link Level}
	 */
	private final Level level;
	/**
	 * The message itself, as a {@link String}
	 */
	private final String msg;
	/**
	 * The name of the class that called for the message to be logged, retrieved by
	 * calling {@link Class#getName()}. Can also be a translated name
	 * ({@link Log#registerClassName(String, String)}).
	 */
	private final String classname;

	/**
	 * Creates a new {@link LogInfo} object and sets all its parameters.
	 * 
	 * @param msg       the message, as a {@link String}
	 * @param level     the messages {@link Level}
	 * @param classname the name of the class that called for the message to be
	 *                  logged, retrieved by calling {@link Class#getName()}. Can
	 *                  also be a translated name
	 *                  ({@link Log#registerClassName(String, String)}).
	 */
	public LogInfo(String msg, Level level, String classname) {
		this.level = level;
		this.msg = msg;
		this.classname = classname;
	}

	/**
	 * Get the messages {@link Level}
	 * 
	 * @return the messages {@link Level}
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Get the message itself, as a {@link String}
	 * 
	 * @return the message itself, as a {@link String}
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Get the name of the class that called for the message to be logged, retrieved
	 * by calling {@link Class#getName()}. Can also be a translated name
	 * ({@link Log#registerClassName(String, String)}).
	 * 
	 * @return the name of the class that called for the message to be logged
	 */
	public String getClassname() {
		return classname;
	}

}

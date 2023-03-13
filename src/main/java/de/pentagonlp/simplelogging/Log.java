package de.pentagonlp.simplelogging;

import java.util.ArrayList;
import java.util.HashMap;

import de.pentagonlp.simplelogging.tools.StackTrace;

/**
 * Main class of the logging API, houses all logging methods used in a
 * {@code static} context
 * 
 * @see {@link Log#setSandboxmode(boolean)} <i>for more information about
 *      sandbox mode</i> <br>
 *      {@link Log#setDebugmode(boolean)} <i>for more information about debug
 *      mode</i> <br>
 *      {@link Log#setProgramname(String)} <i>for more information about why the
 *      logging api needs the programs' name</i>
 * 
 * @author PentagonLP
 */
public class Log {

	/**
	 * {@link HashMap} for translation of class names to proper, manually given
	 * names to better distinguish classes able to log things
	 */
	private final static HashMap<String, String> CLASSTRANSLATIONS = new HashMap<>();

	/**
	 * Specifies whether the program starts in debug mode by default
	 * 
	 * @see {@link Log#setDebugmode(boolean)}
	 */
	private final static boolean DEFAULTDEBUGMODE = false;
	/**
	 * Specifies whether the program starts in sandbox mode by default
	 * 
	 * @see {@link Log#setSandboxmode(boolean)}
	 */
	private final static boolean DEFAULTSANDBOXMODE = false;
	/**
	 * The default program name
	 * 
	 * @see {@link Log#setProgramname(String)}
	 */
	private final static String DEFAULTPROGRAMNAME = "Program";
	/**
	 * Specifies whether the default {@link Logger} is added to the logger list upon
	 * initialization of the logging API
	 */
	private final static boolean ADDDEFAULTLOGGERATINIT = true;

	/**
	 * List of default {@link Logger Loggers} that are asked to log a message when
	 * {@link Log#log(Level, String, boolean)} is called
	 */
	private static ArrayList<Logger> defaultlogger = null;

	/**
	 * Specifies whether the program currently runs in debug mode
	 * 
	 * @see {@link Log#setDebugmode(boolean)}
	 */
	private static boolean debugmode = DEFAULTDEBUGMODE;
	/**
	 * Specifies whether the program currently runs in sandbox mode
	 * 
	 * @see {@link Log#setSandboxmode(boolean)}
	 */
	private static boolean sandboxmode = DEFAULTSANDBOXMODE;
	/**
	 * The programs' name
	 * 
	 * @see {@link Log#setProgramname(String)}
	 */
	private static String programname = DEFAULTPROGRAMNAME;

	/**
	 * Call all {@link Logger Loggers} on the {@link Log#getDefaultlogger()} list to
	 * log a log message, of a specified {@link Level}. Specify if warning should be
	 * given that the logged action was not actually executed if logger is in
	 * sandbox mode.
	 * 
	 * @param level          the {@link Level} of the logged message
	 * @param msg            the logged message, as a {@link String}
	 * @param sandboxwarning If {@code true}, a warning will be given that the
	 *                       logged action was not actually executed if logger is in
	 *                       sandbox mode. If {@code false}, no warning will be
	 *                       given.
	 */
	public static void log(Level level, String msg, boolean sandboxwarning) {
		initLoggerIfNeeded();
		for (Logger logger : defaultlogger) {
			logger.log(level, msg, sandboxwarning);
		}
	}

	/**
	 * Call all {@link Logger Loggers} on the {@link Log#getDefaultlogger()} list to
	 * log a log message, of a specified {@link Level}.
	 * 
	 * @param level the {@link Level} of the logged message
	 * @param msg   the logged message, as a {@link String}
	 * @see Log#log(Level, String, boolean)
	 */
	public static void log(Level level, String msg) {
		initLoggerIfNeeded();
		for (Logger logger : defaultlogger) {
			logger.log(level, msg);
		}
	}

	/**
	 * Call all {@link Logger Loggers} on the {@link Log#getDefaultlogger()} list to
	 * log a log message, of the default {@link Level} of the {@link Logger}.
	 * Specify if warning should be given that the logged action was not actually
	 * executed if logger is in sandbox mode.
	 * 
	 * @param msg            the logged message, as a {@link String}
	 * @param sandboxwarning If {@code true}, a warning will be given that the
	 *                       logged action was not actually executed if logger is in
	 *                       sandbox mode. If {@code false}, no warning will be
	 *                       given.
	 * @see Log#log(Level, String, boolean)
	 */
	public static void log(String msg, boolean sandboxwarning) {
		initLoggerIfNeeded();
		for (Logger logger : defaultlogger) {
			logger.log(msg, sandboxwarning);
		}
	}

	/**
	 * Call all {@link Logger Loggers} on the {@link Log#getDefaultlogger()} list to
	 * log a log message, of the default {@link Level} of the {@link Logger}.
	 * 
	 * @param msg the logged message, as a {@link String}
	 * @see Log#log(Level, String, boolean)
	 */
	public static void log(String msg) {
		initLoggerIfNeeded();
		for (Logger logger : defaultlogger) {
			logger.log(msg);
		}
	}

	/**
	 * Call all {@link Logger Loggers} on the {@link Log#getDefaultlogger()} list to
	 * log a stack trace of a {@link Throwable}.
	 * 
	 * @param t the {@link Throwable} to log
	 */
	public static void printStackTrace(Throwable t) {
		initLoggerIfNeeded();
		for (Logger logger : defaultlogger) {
			logger.printStackTrace(t);
		}
	}

	/**
	 * Set all settings of the logging API at once
	 * 
	 * @param _sandboxmode whether the logging API runs in sandbox mode
	 * @param _debugmode   whether the logging API runs in debug mode
	 * @param _programname the name of the program
	 * 
	 * @see {@link Log#setSandboxmode(boolean)} <br>
	 *      {@link Log#setDebugmode(boolean)} <br>
	 *      {@link Log#setProgramname(String)}
	 */
	public static void setSettings(boolean _sandboxmode, boolean _debugmode, String _programname) {
		sandboxmode = _sandboxmode;
		debugmode = _debugmode;
		programname = _programname;
	}

	/**
	 * Get the {@link HashMap} for translation of {@link Class} names to proper,
	 * manually given names to better distinguish classes able to log things.
	 * {@link Class#getName()} is used as the key, manually given name is the value
	 * 
	 * @return {@link HashMap} for translation of {@link Class} names to proper,
	 *         manually given names
	 */
	public static HashMap<String, String> getClasstranslations() {
		return CLASSTRANSLATIONS;
	}

	/**
	 * Register a name of a {@link Class} for translation of class names to proper,
	 * manually given names to better distinguish classes able to log things.
	 * 
	 * @param classpath {@link Class#getName()} of the class to register a name for
	 * @param name      the new name of the {@link Class} for translation of class
	 *                  names to proper, manually given names for classes able to
	 *                  log things.
	 */
	public static void registerClassName(String classpath, String name) {
		CLASSTRANSLATIONS.put(classpath, name);
	}

	/**
	 * Register a name of a {@link Class} for translation of class names to proper,
	 * manually given names to better distinguish classes able to log things. <br>
	 * This method should be called by the {@link Class} to register a name for, as
	 * {@link Class#getName()} is automatically determined from the stack trace.
	 * 
	 * @param name the new name of the {@link Class} for translation of class names
	 *             to proper, manually given names for classes able to log things.
	 * @see Log#registerClassName(String, String)
	 */
	public static void registerClassName(String name) {
		CLASSTRANSLATIONS.put(StackTrace.getFirstOutsideCall("log").getClassName(), name);
	}

	/**
	 * Translate a {@link Class} name to its proper, manually given name to better
	 * distinguish classes able to log things.
	 * 
	 * @param classpath the path of the {@link Class}, can be retrieved by calling
	 *                  {@link Class#getName()}
	 * @return the {@link Class Classes}' proper, manually given name
	 * @see Log#registerClassName(String, String) <i>to register a class name for
	 *      translation</i>
	 */
	public static String getClassName(String classpath) {
		return CLASSTRANSLATIONS.get(classpath);
	}

	/**
	 * Translate a {@link Class} name to its proper, manually given name to better
	 * distinguish classes able to log things. <br>
	 * The {@link Class Classes}' name is read from a {@link StackTraceElement}.
	 * 
	 * @param ste the {@link StackTraceElement} to read the {@link Class Classes}'
	 *            name from
	 * @return the {@link Class Classes}' proper, manually given name
	 * @see Log#registerClassName(String, String) <i>to register a class name for
	 *      translation</i>
	 */
	public static String getClassName(StackTraceElement ste) {
		return getClassName(ste.getClassName());
	}

	/**
	 * Translate a {@link Class} name to its proper, manually given name to better
	 * distinguish classes able to log things. <br>
	 * This method should be called by the {@link Class} to translate a name for, as
	 * the {@link Class#getName()} translated is automatically determined from the
	 * stack trace.
	 * 
	 * @return the {@link Class Classes}' proper, manually given name
	 * @see Log#registerClassName(String, String) <i>to register a class name for
	 *      translation</i>
	 */
	public static String getClassName() {
		return getClassName(StackTrace.getFirstOutsideCall("log"));
	}

	/**
	 * Remove a name of a {@link Class} for translation of class names to proper,
	 * manually given names to better distinguish classes able to log things.
	 * 
	 * @param classpath {@link Class#getName()} of the class to register a name for
	 */
	public static void removeClassName(String classpath) {
		CLASSTRANSLATIONS.remove(classpath);
	}

	/**
	 * Remove a name of a {@link Class} for translation of class names to proper,
	 * manually given names to better distinguish classes able to log things. <br>
	 * The {@link Class Classes}' name is read from a {@link StackTraceElement}.
	 * 
	 * @param ste the {@link StackTraceElement} to read the {@link Class Classes}'
	 *            name from
	 */
	public static void removeClassName(StackTraceElement ste) {
		removeClassName(ste.getClassName());
	}

	/**
	 * Remove a name of a {@link Class} for translation of class names to proper,
	 * manually given names to better distinguish classes able to log things. <br>
	 * This method should be called by the {@link Class} to translate a name for, as
	 * the {@link Class#getName()} translated is automatically determined from the
	 * stack trace.
	 */
	public static void removeClassName() {
		removeClassName(StackTrace.getFirstOutsideCall("log"));
	}

	/**
	 * Add a {@link Logger} to the list of default loggers called when
	 * {@link Log#log(Level, String, boolean)} is called
	 * 
	 * @param logger the {@link Logger} to add
	 * @see Log#getDefaultlogger()
	 */
	public static void addDefaultLogger(Logger logger) {
		initLoggerIfNeeded();
		defaultlogger.add(logger);
	}

	/**
	 * Get whether the logging API is in sandbox mode
	 * 
	 * @return {@code true} if in sandox mode, {@code false} if it isn't
	 * @see Log#setSandboxmode(boolean)
	 */
	public static boolean isSandboxmode() {
		return sandboxmode;
	}

	/**
	 * Set whether the logging API, and by extend the program, runs in sandbox mode
	 * <br>
	 * Sandbox mode should be used to test the program and prevent outside
	 * interactions, that could e.g. potentially delete important files or publish
	 * (if in testing mode, often false) information for everyone to see. <br>
	 * Such an action can still be logged, but a warning will be displayed that the
	 * action was only simulated and not really executed (See
	 * {@link Log#log(Level, String, boolean)})
	 * 
	 * @param sandboxmode {@code true} to enable sandbox mode, false to disable
	 *                    sandbox mode
	 * @see Log#isSandboxmode()
	 */
	public static void setSandboxmode(boolean sandboxmode) {
		Log.sandboxmode = sandboxmode;
	}

	/**
	 * Get whether the logging API is in debug mode
	 * 
	 * @return {@code true} if in debug mode, {@code false} if it isn't
	 * @see Log#setDebugmode(boolean)
	 */
	public static boolean isDebugmode() {
		return debugmode;
	}

	/**
	 * Set whether the logging API, and by extend the program, runs in debug mode
	 * <br>
	 * Debug mode should be used to test the program and log more actions then in
	 * usual operation. These logs give information of the internal structure and
	 * computations.
	 * 
	 * @param debugmode {@code true} to enable debug mode, false to disable debug
	 *                  mode
	 * @see Log#isDebugmode()
	 */
	public static void setDebugmode(boolean debugmode) {
		Log.debugmode = debugmode;
	}

	/**
	 * Get the previously registered name of the program
	 * 
	 * @return the name of the program
	 * @see Log#setProgramname(String)
	 */
	public static String getProgramname() {
		return programname;
	}

	/**
	 * Set the programs' name, used by the
	 * {@link org.craftyserver.general.log.formatter.DefaultLogFormatter} for debug
	 * mode and sandbox mode startup and change messages
	 * 
	 * @param programname the programs' name
	 */
	public static void setProgramname(String programname) {
		Log.programname = programname;
	}

	/**
	 * Get the list of default loggers called when
	 * {@link Log#log(Level, String, boolean)} is called
	 * 
	 * @return the list of default loggers
	 * @see Log#addDefaultLogger(Logger)
	 */
	public static ArrayList<Logger> getDefaultlogger() {
		return defaultlogger;
	}

	/**
	 * Set the list of default loggers called when
	 * {@link Log#log(Level, String, boolean)} is called
	 * 
	 * @param defaultlogger the list of default loggers
	 * @see Log#addDefaultLogger(Logger)
	 */
	public static void setDefaultlogger(ArrayList<Logger> defaultlogger) {
		Log.defaultlogger = defaultlogger;
	}

	/**
	 * Initialize the logging API, if not already done. Creates the
	 * {@link ArrayList} to store the default {@link Logger Loggers} in and, if
	 * configured that way, adds a new default format logger for {@link System#out}
	 * to the list. <br>
	 * Called every time {@link Log#log(Level, String, boolean)} is called and with
	 * that initializes on the first call of the method.
	 */
	private static void initLoggerIfNeeded() {
		if (defaultlogger == null) {
			defaultlogger = new ArrayList<>();
			if (ADDDEFAULTLOGGERATINIT)
				defaultlogger.add(new Logger());
		}
	}

}

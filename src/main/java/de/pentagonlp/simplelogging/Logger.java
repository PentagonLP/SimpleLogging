package de.pentagonlp.simplelogging;

import de.pentagonlp.simplelogging.formatter.DefaultLogFormatter;
import de.pentagonlp.simplelogging.tools.StackTrace;
import de.pentagonlp.simplelogging.writer.SystemoutLogWriter;

/**
 * Logger to first format and then write a message to the log, using a
 * {@link LogFormatter} and a {@link LogWriter}, sometimes combined in one
 * {@link LogFormatterAndWriter} object.
 * 
 * @author PentagonLP
 * @see {@link Logger#log(Level, String, boolean)} <i>the main logging
 *      method</i>
 */
public class Logger {

	/**
	 * The default for the default level for the {@link Logger}
	 * 
	 * @see Logger#setDefaultlevel(Level)
	 */
	private final static Level DEFAULTDEFAULTLEVEL = Level.INFO;
	/**
	 * Default value for {@link Logger} whether warning should be given that the
	 * logged action was not actually executed if logger is in sandbox mode, if no
	 * other value is given and no {@link Logger}-specific default is set.
	 * 
	 * @see Logger#setDefaultsandboxwarning(boolean)
	 */
	private final static boolean DEFAULTDEFAULTSANDBOXWARNING = false;
	/**
	 * Default value for {@link Logger} if {@link Class#getName()} should be
	 * included if no translation of the class name to a proper, manually given name
	 * is registered
	 * 
	 * @see Log#registerClassName(String, String)
	 */
	private final static boolean DEFAULTINCLUDENONTRANSLATEDCLASSNAME = false;
	/**
	 * The default initiation message, if not set otherwise
	 * 
	 * @see Logger#setInitiationmessage(String)
	 */
	private final static String DEFAULTINITIATIONMESSAGE = "------------------------------- New Logger Initiation! Program (re-)start? -------------------------------";

	/**
	 * The default level for the {@link Logger}, used if no other Level is given
	 * 
	 * @see Logger#setDefaultlevel(Level)
	 */
	private Level defaultlevel = DEFAULTDEFAULTLEVEL;
	/**
	 * Default value for {@link Logger} whether warning should be given that the
	 * logged action was not actually executed if logger is in sandbox mode, if no
	 * other value is given.
	 * 
	 * @see Logger#setDefaultsandboxwarning(boolean)
	 */
	private boolean defaultsandboxwarning = DEFAULTDEFAULTSANDBOXWARNING;
	/**
	 * Whether {@link Class#getName()} should be included if no translation of the
	 * class name to a proper, manually given name is registered.
	 * 
	 * @see Log#registerClassName(String, String)
	 */
	private boolean includenontranslatedclassname = DEFAULTINCLUDENONTRANSLATEDCLASSNAME;
	/**
	 * The default initiation message
	 * 
	 * @see Logger#setInitiationmessage(String)
	 */
	private String initiationmessage = DEFAULTINITIATIONMESSAGE;

	/**
	 * Specifies whether the debug message at initiation was already written. Only
	 * relevant if {@link Log} runs in debug mode.
	 */
	private boolean debugmessage = false;
	/**
	 * Specifies whether the sandbox message at initiation was already written. Only
	 * relevant if {@link Log} runs in sandbox mode.
	 */
	private boolean sandboxmessage = false;
	/**
	 * Specifies whether a message was already written in the log. {@code true} if
	 * the next message is the first message, {@code false} if not.
	 */
	private boolean firstlog = true;

	/**
	 * Place to store the {@link Logger Loggers} {@link LogFormatter} and
	 * {@link LogWriter} in an combined {@link LogFormatterAndWriter} object.
	 */
	private final LogFormatterAndWriter formatterandwriter;

	/**
	 * Create a new {@link Logger} from a given {@link LogFormatterAndWriter}.
	 * 
	 * @param formatterandwriter the {@link Logger Loggers} {@link LogFormatter} and
	 *                           {@link LogWriter} in an combined
	 *                           {@link LogFormatterAndWriter} object.
	 * @throws IllegalArgumentException if {@code formatterandwriter} is null
	 */
	public Logger(LogFormatterAndWriter formatterandwriter) {
		if (formatterandwriter == null)
			throw new IllegalArgumentException("'Formatter and writer' can not be null!");
		this.formatterandwriter = formatterandwriter;
	}

	/**
	 * Create a new {@link Logger} from a given {@link LogFormatter} and
	 * {@link LogWriter}.
	 * 
	 * @param formatter the {@link Logger Loggers} {@link LogFormatter}. If
	 *                  {@code null}, {@link DefaultLogFormatter} is used.
	 * @param writer    the {@link Logger Loggers} {@link LogWriter}. If
	 *                  {@code null}, {@link SystemoutLogWriter} is used.
	 */
	public Logger(LogFormatter formatter, LogWriter writer) {

		if (formatter == null)
			formatter = new DefaultLogFormatter();
		if (writer == null)
			writer = new SystemoutLogWriter();

		// Local vars must be final to be used in anonymous type
		final LogFormatter _formatter = formatter;
		final LogWriter _writer = writer;

		formatterandwriter = new LogFormatterAndWriter() {

			@Override
			public void write(String logtext) {
				_writer.write(logtext);
			}

			@Override
			public void printStackTrace(Throwable e) {
				_writer.printStackTrace(e);
			}

			@Override
			public boolean isAnsi() {
				return _writer.isAnsi();
			}

			@Override
			public String getStartupSandboxWarning(boolean enabled) {
				return _formatter.getStartupSandboxWarning(_writer.isAnsi(), enabled);
			}

			@Override
			public String getStartupDebugWarning(boolean enabled) {
				return _formatter.getStartupDebugWarning(_writer.isAnsi(), enabled);
			}

			@Override
			public String getSandboxWarning() {
				return _formatter.getSandboxWarning(_writer.isAnsi());
			}

			@Override
			public String format(LogInfo info) {
				return _formatter.format(info, _writer.isAnsi());
			}
		};
	}

	/**
	 * Create a new {@link Logger} from a given {@link LogFormatter}.
	 * {@link SystemoutLogWriter} is used as {@link LogWriter}.
	 * 
	 * @param formatter the {@link Logger Loggers} {@link LogFormatter}. If
	 *                  {@code null}, {@link DefaultLogFormatter} is used.
	 */
	public Logger(LogFormatter formatter) {
		this(formatter, null);
	}

	/**
	 * Create a new {@link Logger} from a given {@link LogWriter}.
	 * {@link DefaultLogFormatter} is used as a {@link LogFormatter}.
	 * 
	 * @param writer the {@link Logger Loggers} {@link LogWriter}. If {@code null},
	 *               {@link SystemoutLogWriter} is used.
	 */
	public Logger(LogWriter writer) {
		this(null, writer);
	}

	/**
	 * Creates a new {@link Logger}. {@link DefaultLogFormatter} is used as a
	 * {@link LogFormatter} and {@link SystemoutLogWriter} is used as
	 * {@link LogWriter}.
	 */
	public Logger() {
		this(null, null);
	}

	/**
	 * Log a message, of a specified {@link Level}. The message is first formatted
	 * and then written using the {@link Logger Loggers}
	 * {@link LogFormatterAndWriter}. Specify if warning should be given that the
	 * logged action was not actually executed if logger is in sandbox mode.
	 * 
	 * @param level          the {@link Level} of the logged message
	 * @param msg            the logged message, as a {@link String}
	 * @param sandboxwarning If {@code true}, a warning will be given that the
	 *                       logged action was not actually executed if logger is in
	 *                       sandbox mode. If {@code false}, no warning will be
	 *                       given.
	 */
	public void log(Level level, String msg, boolean sandboxwarning) {

		if (firstlog)
			writeifnotnull(initiationmessage);

		if (debugmessage != Log.isDebugmode())
			writeifnotnull(formatterandwriter.getStartupDebugWarning(Log.isDebugmode()));
		if (sandboxmessage != Log.isSandboxmode())
			writeifnotnull(formatterandwriter.getStartupSandboxWarning(Log.isSandboxmode()));

		debugmessage = Log.isDebugmode();
		sandboxmessage = Log.isSandboxmode();
		firstlog = false;

		String classpath = StackTrace.getFirstOutsideCall("log").getClassName();
		String classname = Log.getClassName(classpath);
		if (classname == null && includenontranslatedclassname)
			classname = classpath;

		writeifnotnull(formatterandwriter.format(new LogInfo(msg, level, classname)));
		if (Log.isSandboxmode() && sandboxwarning)
			writeifnotnull(formatterandwriter.getSandboxWarning());
	}

	/**
	 * Log a message, of a specified {@link Level}. The message is first formatted
	 * and then written using the {@link Logger Loggers}
	 * {@link LogFormatterAndWriter}. The default value is taken to decide if
	 * warning should be given that the logged action was not actually executed if
	 * logger is in sandbox mode.
	 * 
	 * @param level the {@link Level} of the logged message
	 * @param msg   the logged message, as a {@link String}
	 * @see {@link Logger#setDefaultsandboxwarning(boolean)} <br>
	 *      {@link Logger#log(Level, String, boolean)}
	 * 
	 */
	public void log(Level level, String msg) {
		log(level, msg, defaultsandboxwarning);
	}

	/**
	 * Log a message, with the default {@link Level} of the {@link Logger}. The
	 * message is first formatted and then written using the {@link Logger Loggers}
	 * {@link LogFormatterAndWriter}. Specify if warning should be given that the
	 * logged action was not actually executed if logger is in sandbox mode.
	 * 
	 * @param msg            the logged message, as a {@link String}
	 * @param sandboxwarning If {@code true}, a warning will be given that the
	 *                       logged action was not actually executed if logger is in
	 *                       sandbox mode. If {@code false}, no warning will be
	 *                       given.
	 * @see {@link Logger#setDefaultlevel(Level)} <br>
	 *      {@link Logger#log(Level, String, boolean)}
	 */
	public void log(String msg, boolean sandboxwarning) {
		log(defaultlevel, msg, sandboxwarning);
	}

	/**
	 * Log a message, with the default {@link Level} of the {@link Logger}. The
	 * message is first formatted and then written using the {@link Logger Loggers}
	 * {@link LogFormatterAndWriter}. The default value is taken to decide if
	 * warning should be given that the logged action was not actually executed if
	 * logger is in sandbox mode.
	 * 
	 * @param msg the logged message, as a {@link String}
	 * @see {@link Logger#setDefaultlevel(Level)} <br>
	 *      {@link Logger#setDefaultsandboxwarning(boolean)} <br>
	 *      {@link Logger#log(Level, String, boolean)}
	 */
	public void log(String msg) {
		log(defaultlevel, msg, defaultsandboxwarning);
	}

	/**
	 * Call the {@link Logger Loggers} {@link LogWriter} log a stack trace of an
	 * {@link Exception}.
	 * 
	 * @param t the {@link Throwable} to log
	 */
	public void printStackTrace(Throwable t) {
		formatterandwriter.printStackTrace(t);
	}

	/**
	 * Get the {@link Logger Loggers} default {@link Level}, used if no
	 * {@link Level} is given for a message to log.
	 * 
	 * @return the {@link Logger Loggers} default {@link Level}
	 * @see Logger#setDefaultlevel(Level)
	 */
	public Level getDefaultlevel() {
		return defaultlevel;
	}

	/**
	 * Set the {@link Logger Loggers} default {@link Level}, used if no
	 * {@link Level} is given for a message to log.
	 * 
	 * @param defaultlevel the {@link Logger Loggers} default {@link Level}
	 * @see Logger#getDefaultlevel()
	 */
	public void setDefaultlevel(Level defaultlevel) {
		this.defaultlevel = defaultlevel;
	}

	/**
	 * Get whether a warning should be given that a logged action was not actually
	 * executed if logger is in sandbox mode. Used if no other value is given.
	 * 
	 * @return whether a warning should be given that a logged action was not
	 *         actually executed if logger is in sandbox mode
	 * @see Logger#setDefaultsandboxwarning(boolean)
	 */
	public boolean getDefaultsandboxwarning() {
		return defaultsandboxwarning;
	}

	/**
	 * Set whether a warning should be given that a logged action was not actually
	 * executed if logger is in sandbox mode. Used if no other value is given.
	 * 
	 * @param {@code booleam} whether a warning should be given that a logged action
	 * was not actually executed if logger is in sandbox mode
	 * @see Logger#getDefaultsandboxwarning(boolean)
	 */
	public void setDefaultsandboxwarning(boolean defaultsandboxwarning) {
		this.defaultsandboxwarning = defaultsandboxwarning;
	}

	/**
	 * Get whether {@link Class#getName()} should be included if no translation of
	 * the class name to a proper, manually given name is registered.
	 * 
	 * @return whether {@link Class#getName()} should be included if no translation
	 *         of the class name is registered.
	 * @see {@link Log#registerClassName(String, String)}
	 *      {@link Logger#setIncludenontranslatedclassname(boolean)}
	 */
	public boolean getIncludenontranslatedclassname() {
		return includenontranslatedclassname;
	}

	/**
	 * Set whether {@link Class#getName()} should be included if no translation of
	 * the class name to a proper, manually given name is registered.
	 * 
	 * @param includenontranslatedclassname {@code boolean} whether
	 *                                      {@link Class#getName()} should be
	 *                                      included if no translation of the class
	 *                                      name is registered.
	 * @see {@link Log#registerClassName(String, String)}
	 *      {@link Logger#getIncludenontranslatedclassname(boolean)}
	 */
	public void setIncludenontranslatedclassname(boolean includenontranslatedclassname) {
		this.includenontranslatedclassname = includenontranslatedclassname;
	}

	/**
	 * Get the initiation message printed before the first message is logged.
	 * 
	 * @return the initiation message printed before the first message is logged
	 * @see Logger#setInitiationmessage(String)
	 */
	public String getInitiationmessage() {
		return initiationmessage;
	}

	/**
	 * Set the initiation message printed before the first message is logged.
	 * 
	 * @param initiationmessage the initiation message printed before the first
	 *                          message is logged
	 * @see Logger#getInitiationmessage(String)
	 */
	public void setInitiationmessage(String initiationmessage) {
		this.initiationmessage = initiationmessage;
	}

	/**
	 * Call the writer to write a given {@link String}. If {@code null} is given, do
	 * nothing.
	 * 
	 * @param string the {@link String} to write or {@code null}
	 */
	private void writeifnotnull(String string) {
		if (string != null)
			formatterandwriter.write(string);
	}

}

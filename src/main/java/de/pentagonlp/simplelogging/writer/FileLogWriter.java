package de.pentagonlp.simplelogging.writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * {@link de.pentagonlp.simplelogging.LogWriter LogWriter} to write logged
 * messages to a file
 * 
 * @author PentagonLP
 */
public class FileLogWriter extends ExceptionOnlyInDebugModeLogWriter {

	/**
	 * Default value whether the file supports ansi. Used if no other value is given
	 * 
	 * @see FileLogWriter#FileLogWriter(String)
	 */
	private static final boolean DEFAULTISANSI = true;

	/**
	 * Whether the file supports ansi. Can be set by
	 * FileLogWriter#FileLogWriter(String)
	 */
	private final boolean isansi;
	/**
	 * {@link PrintStream} to print new messages to to be stored in the {@link File}
	 */
	private PrintStream fileprintstream;

	/**
	 * Creates a new {@link FileLogWriter} with a given {@code filepath} of the
	 * output file. Specifies whether the file supports ansi.
	 * 
	 * @param filepath the filepath of the output file
	 * @param isansi   {@code true} if the file supports ansi, {@code false} if it
	 *                 doesn't
	 * @throws IllegalArgumentException if {@code filepath} is {@code null}
	 */
	public FileLogWriter(String filepath, boolean isansi) {
		this.isansi = isansi;
		if (filepath == null)
			throw new IllegalArgumentException("Filepath can not be null!");
		File outputfile = new File(filepath);
		try {
			outputfile.createNewFile();
		} catch (IOException e) {
			// No Error, just nevermind then...
		}
		try {
			fileprintstream = new PrintStream(new FileOutputStream(filepath, true));
		} catch (FileNotFoundException e) {
			// Cannot occur
		}
	}

	/**
	 * Creates a new {@link FileLogWriter} with a given {@code filepath} of the
	 * output file. Uses the default value to specify whether the file supports
	 * ansi.
	 * 
	 * @param filepath the filepath of the output file
	 * @throws IllegalArgumentException if {@code filepath} is {@code null}
	 * @see FileLogWriter#FileLogWriter(String, boolean)
	 */
	public FileLogWriter(String filepath) {
		this(filepath, DEFAULTISANSI);
	}

	/**
	 * Write a message to the {@link PrintStream} of the file, using
	 * {@link PrintStream#println(String)} to write it in a new line.
	 * 
	 * @param logtext the message to write
	 */
	@Override
	public void write(String logtext) {
		fileprintstream.println(logtext);
	}

	/**
	 * Get whether the file to be written to supports ansi.
	 * 
	 * @return {@code true} if it supports ansi, {@code false} if it doesn't
	 */
	@Override
	public boolean isAnsi() {
		return isansi;
	}

	/**
	 * Print the {@code StackTrace} of a {@link Throwable}, after
	 * {@link ExceptionOnlyInDebugModeLogWriter} has decided its criteria are
	 * fulfilled
	 * 
	 * @param e the {@link Throwable} to print the {@code StackTrace} of
	 */
	@Override
	protected void printStackTraceAfterCheck(Throwable t) {
		t.printStackTrace(fileprintstream);
	}

}

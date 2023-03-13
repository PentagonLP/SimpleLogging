package de.pentagonlp.simplelogging.tools;

import java.text.SimpleDateFormat;

/**
 * Storage of a few {@code static final} {@link SimpleDateFormat
 * SimpleDateFormats}, primarily used for logging.
 * 
 * @author PentagonLP
 */
public class DateformatterStorage {

	/**
	 * {@link SimpleDateFormat} initialized with the format
	 * {@code dd.MM.yyyy|HH:mm:ss}
	 */
	public static final SimpleDateFormat DATEWITHDOTHOURMINUTESECONDWITHCOLON24 = new SimpleDateFormat(
			"dd.MM.yyyy|HH:mm:ss");
	/**
	 * {@link SimpleDateFormat} initialized with the format {@code dd.MM.yyyy}
	 */
	public static final SimpleDateFormat DATEWITHDOT = new SimpleDateFormat("dd.MM.yyyy");
	/**
	 * {@link SimpleDateFormat} initialized with the format {@code yyyy-MM-dd}
	 */
	public static final SimpleDateFormat YEARMONTHDAYWITHDASHES = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * {@link SimpleDateFormat} initialized with the format {@code HH:mm:ss}
	 */
	public static final SimpleDateFormat HOURMINUTESECONDWITHCOLON24 = new SimpleDateFormat("HH:mm:ss");
	/**
	 * {@link SimpleDateFormat} initialized with the format {@code HH:mm}
	 */
	public static final SimpleDateFormat HOURMINUTEWITHCOLON24 = new SimpleDateFormat("HH:mm");

}

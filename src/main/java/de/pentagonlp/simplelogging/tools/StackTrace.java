package de.pentagonlp.simplelogging.tools;

/**
 * Class of {@code static} methods to access the method stack.
 * 
 * @author PentagonLP
 */
// TODO seams a bit buggy sometimes: Only some classes seam to work? Might also have something to do with Log.registerClassName().
public class StackTrace {

	/**
	 * Get the current method stack
	 * 
	 * @return the stack as an array of {@link StackTraceElement
	 *         StackTraceElements}.
	 */
	public static StackTraceElement[] getStack() {
		return new Exception().getStackTrace();
	}

	/**
	 * Get the first call outside of a specific package
	 * 
	 * @param packagename the name of the package to get the first method call
	 *                    outside of it
	 * @return the fist {@link StackTraceElement} outside of the package,
	 *         {@code null} if no call outside of the package occurred.
	 */
	public static StackTraceElement getFirstOutsideCall(String packagename) {
		for (StackTraceElement ste : getStack()) {
			if (!ste.getClassName().contains("." + packagename + "."))
				return ste;
		}
		return null;
	}

}

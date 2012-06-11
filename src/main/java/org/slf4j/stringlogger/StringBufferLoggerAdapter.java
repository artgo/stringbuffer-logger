package org.slf4j.stringlogger;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * StringBuffer simple concurrent implementation for in-memory logging for unit
 * testing purposes.
 * 
 * @author Artem Golubev
 * 
 */
public class StringBufferLoggerAdapter extends MarkerIgnoringBase {
	private static final long serialVersionUID = 1388707249552181410L;

	/**
	 * Mark the time when this class gets loaded into memory.
	 */
	public static final String LINE_SEPARATOR = "\n";

	public static enum LogLevels {
		FATAL, ERROR, WARN, INFO, DEBUG, TRACE
	}

	// private static final Object SYNC_OBJECT = new Object();
	private static final StringBuffer BUFFER = new StringBuffer();
	private static volatile LogLevels currentLevel = LogLevels.TRACE;

	public static void clearBuffer() {
		BUFFER.setLength(0);
	}

	/**
	 * Reset state to initial state
	 */
	public static void hardReset() {
		clearBuffer();
		setCurrentLogLevel(LogLevels.TRACE);
	}

	public static String getString() {
		return BUFFER.toString();
	}

	public static LogLevels getCurrentLogLevel() {
		return currentLevel;
	}

	public static void setCurrentLogLevel(LogLevels level) {
		currentLevel = level;
	}

	/**
	 * This is our internal implementation for logging regular
	 * (non-parameterized) log messages.
	 * 
	 * @param level
	 * @param message
	 * @param t
	 */
	private void log(final LogLevels level, final String message, final Throwable t) {
		if (level.ordinal() <= currentLevel.ordinal()) {
			// Let Java compiler figure out the best way to implement this
			String logString = "[" + Thread.currentThread().getName() + "] " + level + " " + name + " - " + message;
			if (t != null) {
				logString = logString + " " + t.toString();
			}
			logString = logString + LINE_SEPARATOR;

			BUFFER.append(logString);
		}
	}

	/**
	 * For formatted messages, first substitute arguments and then log.
	 * 
	 * @param level
	 * @param format
	 * @param arg1
	 * @param arg2
	 */
	private void formatAndLog(final LogLevels level, final String format, final Object arg1, final Object arg2) {
		FormattingTuple tp = MessageFormatter.format(format, arg1, arg2);
		log(level, tp.getMessage(), tp.getThrowable());
	}

	/**
	 * For formatted messages, first substitute arguments and then log.
	 * 
	 * @param level
	 * @param format
	 * @param argArray
	 */
	private void formatAndLog(final LogLevels level, final String format, final Object[] argArray) {
		FormattingTuple tp = MessageFormatter.arrayFormat(format, argArray);
		log(level, tp.getMessage(), tp.getThrowable());
	}

	private boolean isLevelEnabled(LogLevels level) {
		return currentLevel.ordinal() >= level.ordinal();
	}

	StringBufferLoggerAdapter(String name) {
		this.name = name;
	}

	public boolean isTraceEnabled() {
		return isLevelEnabled(LogLevels.TRACE);
	}

	public void trace(String msg) {
		log(LogLevels.TRACE, msg, null);
	}

	public void trace(String format, Object arg) {
		formatAndLog(LogLevels.TRACE, format, arg, null);
	}

	public void trace(String format, Object arg1, Object arg2) {
		formatAndLog(LogLevels.TRACE, format, arg1, arg2);
	}

	public void trace(String format, Object[] argArray) {
		formatAndLog(LogLevels.TRACE, format, argArray);
	}

	public void trace(String msg, Throwable t) {
		log(LogLevels.TRACE, msg, t);
	}

	public boolean isDebugEnabled() {
		return isLevelEnabled(LogLevels.DEBUG);
	}

	public void debug(String msg) {
		log(LogLevels.DEBUG, msg, null);
	}

	public void debug(String format, Object arg) {
		formatAndLog(LogLevels.DEBUG, format, arg, null);
	}

	public void debug(String format, Object arg1, Object arg2) {
		formatAndLog(LogLevels.DEBUG, format, arg1, arg2);
	}

	public void debug(String format, Object[] argArray) {
		formatAndLog(LogLevels.DEBUG, format, argArray);
	}

	public void debug(String msg, Throwable t) {
		log(LogLevels.DEBUG, msg, t);
	}

	public boolean isInfoEnabled() {
		return isLevelEnabled(LogLevels.INFO);
	}

	/**
	 * A simple implementation which always logs messages of level INFO
	 * according to the format outlined above.
	 */

	public void info(String msg) {
		log(LogLevels.INFO, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */

	public void info(String format, Object arg) {
		formatAndLog(LogLevels.INFO, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */

	public void info(String format, Object arg1, Object arg2) {
		formatAndLog(LogLevels.INFO, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * INFO according to the format outlined above.
	 */

	public void info(String format, Object[] argArray) {
		formatAndLog(LogLevels.INFO, format, argArray);
	}

	/**
	 * Log a message of level INFO, including an exception.
	 */

	public void info(String msg, Throwable t) {
		log(LogLevels.INFO, msg, t);
	}

	public boolean isWarnEnabled() {
		return isLevelEnabled(LogLevels.WARN);
	}

	/**
	 * A simple implementation which always logs messages of level WARN
	 * according to the format outlined above.
	 */

	public void warn(String msg) {
		log(LogLevels.WARN, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */

	public void warn(String format, Object arg) {
		formatAndLog(LogLevels.WARN, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */

	public void warn(String format, Object arg1, Object arg2) {
		formatAndLog(LogLevels.WARN, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * WARN according to the format outlined above.
	 */

	public void warn(String format, Object[] argArray) {
		formatAndLog(LogLevels.WARN, format, argArray);
	}

	/**
	 * Log a message of level WARN, including an exception.
	 */

	public void warn(String msg, Throwable t) {
		log(LogLevels.WARN, msg, t);
	}

	public boolean isErrorEnabled() {
		return isLevelEnabled(LogLevels.ERROR);
	}

	/**
	 * A simple implementation which always logs messages of level ERROR
	 * according to the format outlined above.
	 */

	public void error(String msg) {
		log(LogLevels.ERROR, msg, null);
	}

	/**
	 * Perform single parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */

	public void error(String format, Object arg) {
		formatAndLog(LogLevels.ERROR, format, arg, null);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */

	public void error(String format, Object arg1, Object arg2) {
		formatAndLog(LogLevels.ERROR, format, arg1, arg2);
	}

	/**
	 * Perform double parameter substitution before logging the message of level
	 * ERROR according to the format outlined above.
	 */

	public void error(String format, Object[] argArray) {
		formatAndLog(LogLevels.ERROR, format, argArray);
	}

	/**
	 * Log a message of level ERROR, including an exception.
	 */

	public void error(String msg, Throwable t) {
		log(LogLevels.ERROR, msg, t);
	}

	public String toString() {
		return getString();
	}
}
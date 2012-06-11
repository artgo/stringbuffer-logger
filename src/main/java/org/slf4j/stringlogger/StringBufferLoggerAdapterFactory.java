package org.slf4j.stringlogger;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class StringBufferLoggerAdapterFactory implements ILoggerFactory {
	private static final StringBufferLoggerAdapter SINGLETON_LOGGER = new StringBufferLoggerAdapter("");

	public StringBufferLoggerAdapterFactory() {
	}

	public Logger getLogger(String name) {
		return SINGLETON_LOGGER;
	}
}

package org.slf4j.stringlogger;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.stringlogger.StringBufferLoggerAdapter.LogLevels;


public class StringBufferLoggerAdapterTest {
	private static final String TEST2_STR = "Test2";
	private static final String TEST1_STR = "Test1";
	private static StringBufferLoggerAdapter logger;
	
    @BeforeClass
    public static void oneTimeSetUp() {
    	logger = new StringBufferLoggerAdapter("test");        
    }

    @AfterClass
    public static void oneTimeTearDown() {
        logger = null;
    }

    @Before
    public void setUp() {
    	StringBufferLoggerAdapter.hardReset();
    }
    
	@Test
	public void testGetStringContainsAllMessages() {
		logger.trace(TEST1_STR);
		logger.trace(TEST2_STR);
		
		String resultString = StringBufferLoggerAdapter.getString();
		
		assertNotNull(resultString);
		
		assertTrue(resultString.indexOf(TEST1_STR) >= 0);
		assertTrue(resultString.indexOf(TEST2_STR) >= 0);
	}

	@Test
	public void testSetCurrentLogLevelChangeLoggingLevel() {
		logger.trace(TEST1_STR);

		StringBufferLoggerAdapter.setCurrentLogLevel(LogLevels.DEBUG);
		
		logger.trace(TEST2_STR);
		
		String resultString = StringBufferLoggerAdapter.getString();

		assertNotNull(resultString);
		
		assertTrue(resultString.indexOf(TEST1_STR) >= 0);
		assertTrue(resultString.indexOf(TEST2_STR) < 0);
	}

	@Test
	public void testIsTraceEnabledIsChangedIfLoggingLevelIsChanged() {
		assertTrue(logger.isTraceEnabled());

		StringBufferLoggerAdapter.setCurrentLogLevel(LogLevels.DEBUG);
		
		assertFalse(logger.isTraceEnabled());
	}

	@Test
	public void testLogThrownMessage() {
		Exception e = new TestException(TEST1_STR);
		
		logger.trace("", e);
		
		String resultString = StringBufferLoggerAdapter.getString();
		
		assertNotNull(resultString);
		
		assertTrue(resultString.indexOf(TEST1_STR) >= 0);
	}

	@Test
	public void testLogThrownExceptionClass() {
		Exception e = new TestException();
		
		logger.trace("", e);
		
		String resultString = StringBufferLoggerAdapter.getString();
		
		assertNotNull(resultString);
		
		assertTrue(resultString.indexOf(TestException.class.getName()) >= 0);
	}
}

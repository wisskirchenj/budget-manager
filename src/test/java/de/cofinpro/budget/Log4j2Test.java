package de.cofinpro.budget;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.spi.LoggerContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
class Log4j2Test {

    @Test
    void log4jActiveAndErrorTest() {
        Object loggerPath = org.apache.logging.log4j.Logger.class
                .getResource("/org/apache/logging/log4j/Logger.class");
        assertNotNull(loggerPath);
        Object appenderPath = org.apache.logging.log4j.Logger.class
                .getResource("/org/apache/logging/log4j/core/Appender.class");
        assertNotNull(appenderPath);
    }

    @Test
    void log4j2xmlInCPAndPrintTest() {
        Object resourcePath = org.apache.logging.log4j.Logger.class.getResource("/log4j2.xml");
        assertNotNull(resourcePath);
    }

    @Test
    void logLevelSetToInfoTest() {
        LoggerContext context = LogManager.getContext(false);
        Level rootLogLevel = context.getLogger(LogManager.ROOT_LOGGER_NAME).getLevel();
        assertEquals("INFO", rootLogLevel.toString());
    }
}
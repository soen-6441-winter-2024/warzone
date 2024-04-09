package ca.concordia.app.warzone.logging;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.io.File;
import java.nio.charset.StandardCharsets;
import static org.apache.commons.io.FileUtils.readFileToString;

class LogEntryBufferTest {

    private static final String LOG_FILE_NAME = "src/test/resources/warzone.log";
    private static final String EXPECTED_LOG_FILE_NAME = "src/test/resources/expected_warzone.log";
    private LogEntryBuffer s_LogEntryBuffer;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        FileUtils.deleteQuietly(new File(LOG_FILE_NAME));
        s_LogEntryBuffer = new LogEntryBuffer();
    }

    @Test
    void testLogFileExists() {
        s_LogEntryBuffer.addObserver(new LogWriter(LOG_FILE_NAME));
        s_LogEntryBuffer.addEntry("this is a text adding an entry");

        File logFile = new File(LOG_FILE_NAME);
        Assertions.assertTrue(logFile.exists(),"Log file should exist");
    }


}

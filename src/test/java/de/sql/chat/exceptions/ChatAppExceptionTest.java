package de.sql.chat.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChatAppExceptionTest {

    @Test
    void testChatAppExceptionWithErrorCode() {
        ErrorCode errorCode = ErrorCode.SESSION_ERROR;
        ChatAppException exception = new ChatAppException(errorCode);

        assertNotNull(exception);
        assertEquals(errorCode, exception.getErrorCode());
    }

    @Test
    void testChatAppExceptionWithErrorCodeAndCustomMessage() {
        ErrorCode errorCode = ErrorCode.SESSION_ERROR;
        String customMessage = "Custom error message";
        ChatAppException exception = new ChatAppException(errorCode, customMessage);

        assertNotNull(exception);
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(customMessage, exception.getMessage());
    }

    @Test
    void testChatAppExceptionWithErrorCodeAndNullCustomMessage() {
        ErrorCode errorCode = ErrorCode.SESSION_ERROR;
        ChatAppException exception = new ChatAppException(errorCode, null);

        assertNotNull(exception);
        assertEquals(errorCode, exception.getErrorCode());
        assertNotNull(exception.getMessage());
    }
}
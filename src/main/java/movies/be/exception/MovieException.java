/*
 * @ (#) MovieException.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.exception;


public class MovieException extends RuntimeException {

    /**
     * Constructs a new MovieException with the specified detail message.
     *
     * @param message the detail message
     */
    public MovieException(String message) {
        super(message);
    }

    /**
     * Constructs a new MovieException with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public MovieException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new MovieException with the specified cause.
     *
     * @param cause the cause of the exception
     */
    public MovieException(Throwable cause) {
        super(cause);
    }
}
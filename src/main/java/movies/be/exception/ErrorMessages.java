/*
 * @ (#) ErrorMessages.java 1.0 2025-05-05
 *
 * Copyright (c) 2025 IUH. All rights reserved
 */

package movies.be.exception;


public final class ErrorMessages {

    private ErrorMessages() {
        // Private constructor to prevent instantiation
    }

    // Movie-related error messages
    public static final String MOVIE_NOT_FOUND_MESSAGE = "Movie with ID %d does not exist";
    public static final String INVALID_MOVIE_DATA_MESSAGE = "Movie data cannot be null or title cannot be empty";
    public static final String PHIM_LE_VIDEO_URL_REQUIRED_MESSAGE = "PHIM_LE must have a video URL";
    public static final String PHIM_LE_NO_EPISODES_MESSAGE = "PHIM_LE should not have episodes";
    public static final String PHIM_BO_NO_VIDEO_URL_MESSAGE = "PHIM_BO should not have a direct video URL";
    public static final String PHIM_BO_EPISODES_REQUIRED_MESSAGE = "PHIM_BO must have at least one episode";
    public static final String INVALID_MOVIE_TYPE_MESSAGE = "Invalid movie type: %s";

    // Auth-related error messages
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";
    public static final String EMAIL_NOT_FOUND_MESSAGE = "Email %s not found";
    public static final String INVALID_VERIFICATION_CODE_MESSAGE = "Invalid or expired verification code";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid or expired token";
}
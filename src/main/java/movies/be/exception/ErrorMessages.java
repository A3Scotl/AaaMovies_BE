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


    // Auth-related error messages



    //Category-related error mess
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with ID %d not found";
    public static final String INVALID_CATEGORY_DATA_MESSAGE = "Invalid category data: name cannot be empty";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category with name %s already exists";
}
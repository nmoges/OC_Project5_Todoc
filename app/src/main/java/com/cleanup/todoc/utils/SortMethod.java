package com.cleanup.todoc.utils;

/**
 * <p>List of all possible sort methods for task</p>
 */
public enum SortMethod {
    /**
     * Sort alphabetical by name
     */
    ALPHABETICAL,
    /**
     * Inverted sort alphabetical by name
     */
    ALPHABETICAL_INVERTED,
    /**
     * Lastly created first
     */
    RECENT_FIRST,
    /**
     * First created first
     */
    OLD_FIRST,
    /**
     * By project
     */
    BY_PROJECT,
    /**
     * No sort
     */
    NONE
}

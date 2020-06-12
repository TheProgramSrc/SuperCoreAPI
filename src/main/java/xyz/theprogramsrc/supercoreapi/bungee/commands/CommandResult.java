package xyz.theprogramsrc.supercoreapi.bungee.commands;

/**
 * Representation of the possible results of a command
 */
public enum CommandResult {
    COMPLETED,
    INVALID_ARGS,
    NO_PERMISSION,
    NOT_SUPPORTED,
    NO_ACCESS;
}

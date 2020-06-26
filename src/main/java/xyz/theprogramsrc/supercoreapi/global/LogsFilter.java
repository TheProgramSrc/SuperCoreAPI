package xyz.theprogramsrc.supercoreapi.global;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

import java.util.Arrays;

public class LogsFilter extends AbstractFilter{

    private final FilterResult result;
    private final String[] filteredStrings;

    /**
     * Constructor of a log filter
     * @param result the default result for the filtered words
     * @param filteredStrings the strings to filter
     */
    public LogsFilter(FilterResult result, String... filteredStrings){
        this.result = result;
        this.filteredStrings = filteredStrings;
    }

    private Result process(String message){
        if(message != null){
            String msg = message.toLowerCase();
            if(Arrays.stream(this.filteredStrings).anyMatch(s-> msg.contains(s.toLowerCase()))){
                return Result.toResult(this.result.name(), Result.NEUTRAL);
            }
        }

        return Result.NEUTRAL;
    }

    @Override
    public Result filter(LogEvent event) {
        return this.process(event.getMessage().getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return this.process(msg.getFormattedMessage());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return this.process(msg.toString());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return this.process(msg);
    }

    public static enum FilterResult{
        DENY,
        NEUTRAL,
        NONE
    }

}

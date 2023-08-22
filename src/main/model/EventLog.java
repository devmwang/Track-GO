package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of alarm system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    // EFFECTS: Constructs a new instance of EventLog
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of EventLog
     */
    // EFFECTS: Returns the single instance of EventLog, instantiating it if not yet instantiated
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * Adds an event to the event log.
     * @param event the event to be added
     */
    // REQUIRES: event is not null
    // MODIFIES: this
    // EFFECTS: Adds the given event to the event log
    public void logEvent(Event event) {
        events.add(event);
    }

    /**
     * Clears the event log and logs the event.
     */
    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: Returns an iterator for the events in the event log
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
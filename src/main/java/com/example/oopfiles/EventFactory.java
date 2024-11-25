package com.example.oopfiles;

public class EventFactory {

    // Factory method to create different types of events
    public static Event createEvent(String eventType) {
        switch (eventType) {
            case "CONFERENCE":
                return new ConferenceEvent();
            case "CONCERT":
                return new ConcertEvent();
            case "WORKSHOP":
                return new WorkshopEvent();
            default:
                throw new IllegalArgumentException("Invalid event type");
        }
    }

    // Overloaded factory method to create an event with common initial values
    public static Event createEvent(String eventType, int eventID, String eventName, float budget, String eventTypeName, String eventDate, boolean status) {
        Event event = createEvent(eventType);
        event.assignAllValues(eventID, eventName, budget, eventTypeName, eventDate, status);
        return event;
    }
}

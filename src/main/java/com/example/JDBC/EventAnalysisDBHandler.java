package com.example.JDBC;

import com.example.oopfiles.EventAnalysis;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventAnalysisDBHandler {
    private static EventAnalysisDBHandler instance;
    public static synchronized EventAnalysisDBHandler getInstance() {
        if (instance == null) {
            instance = new EventAnalysisDBHandler();
        }
        return instance;
    }
    public List<EventAnalysis> generateEventAnalysis() {
        List<EventAnalysis> eventAnalysisList = new ArrayList<>();

        String query = "SELECT \n" +
                "    e.eventID,\n" +
                "    e.eventName,\n" +
                "    e.eventDate,\n" +
                "    v.venueName,\n" +
                "    COUNT(t.TicketID) AS ticketsSold,\n" +
                "    AVG(f.rating) AS avgRating\n" +
                "FROM \n" +
                "    event e\n" +
                "LEFT JOIN \n" +
                "    Ticket t ON e.eventID = t.eventID\n" +
                "LEFT JOIN \n" +
                "    feedback f ON e.eventID = f.eventID\n" +
                "LEFT JOIN \n" +
                "    venue v ON e.venueID = v.venueID\n" +
                "GROUP BY \n" +
                "    e.eventID, e.eventName, e.eventDate, v.venueName\n" +
                "ORDER BY \n" +
                "    e.eventID;";

        try (Connection connection = MyJDBC.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int eventID = resultSet.getInt("eventID");
                String eventName = resultSet.getString("eventName");
                double avgRating = resultSet.getDouble("avgRating");
                int ticketsSold = resultSet.getInt("ticketsSold");
                Date date = resultSet.getDate("eventDate");
                String venue = resultSet.getString("venueName");
                // Create EventAnalysis object and add it to the list
                EventAnalysis analysis = new EventAnalysis(eventID, eventName, avgRating, ticketsSold,venue,date);
                eventAnalysisList.add(analysis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eventAnalysisList;
    }
}

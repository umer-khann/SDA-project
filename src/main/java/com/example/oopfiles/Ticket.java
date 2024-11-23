package com.example.oopfiles;

public class Ticket {
    private String ticketId;
    private double price;
    private String seatNumber;
    private Payment p;

    // Constructor
    public Ticket(String ticketId, double price, String seatNumber) {
        this.ticketId = ticketId;
        this.price = price;
        this.seatNumber = seatNumber;
        p= new Payment();
    }

    // Getter and Setter methods
    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // Method
    public boolean validateTicket(String attendeeId) {
        // Logic for validating ticket
        System.out.println("Validating ticket for attendee ID: " + attendeeId);
        return true; // Placeholder
    }
}


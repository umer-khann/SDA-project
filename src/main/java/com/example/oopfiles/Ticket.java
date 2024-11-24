package com.example.oopfiles;

import com.example.JDBC.TicketDBHandler;

public class Ticket {
    private int ticketId;
    private double price;
    private String seatNumber;
    private Payment payment;
    private TicketDBHandler db;

    // Constructor
    public Ticket(int ticketId, double price, String seatNumber) {
        this.ticketId = ticketId;
        this.price = price;
        this.seatNumber = seatNumber;
        this.payment = new Payment();
        db= new TicketDBHandler();
    }

    // Getter and Setter methods
    public int getTicketId() {
        return ticketId;
    }

    public int getPaymentId(){
        return this.payment.getPaymentId();
    }


    public void setTicketId(int ticketId) {
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

    // Method to validate ticket
    public boolean validateTicket(String attendeeId) {
        // Logic for validating ticket (use attendeeID to verify ticket validity)
        System.out.println("Validating ticket for attendee ID: " + attendeeId);
        return true; // Placeholder
    }

    // Method to associate Payment with the ticket
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void insertTicketPurchaseNotification(int attendeeID, int ticketId)
    {
        this.ticketId=ticketId;
        TicketDBHandler.insertTicketPurchaseNotification(ticketId, attendeeID, "Ticket Purchased", "Unread");
    }

    public int AddTicketInformation(int attendeeID, int eventid, int paymentid) {
        return TicketDBHandler.insertTicket(payment.getAmount(), eventid,attendeeID,payment.getPaymentId());
    }
}

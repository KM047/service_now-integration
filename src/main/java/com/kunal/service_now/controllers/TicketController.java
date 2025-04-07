package com.kunal.service_now.controllers;

import com.kunal.service_now.dto.TicketDTO;
import com.kunal.service_now.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> createTicket(
            @RequestBody TicketDTO newTicket) {
        // Logic to create a ticket
        return ticketService.createTicket( newTicket);
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets(@RequestParam(required = false, name = "u_type") String ticketType) {
        // Logic to get all tickets
        return ticketService.getAllTickets(ticketType);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<?> getTicketById(@PathVariable String ticketId) {
        // Logic to get a ticket by ID
        return ticketService.getTicketById(ticketId);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<?> updateTicket(@PathVariable String ticketId, @RequestBody TicketDTO updatedTicket) {
        // Logic to update a ticket
        return ticketService.updateTicket(ticketId, updatedTicket);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<?> deleteTicket(@PathVariable String ticketId) {
        // Logic to delete a ticket
        return ticketService.deleteTicket(ticketId);
    }

}

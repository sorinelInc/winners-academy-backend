package com.winnersacademy.controller;

import com.winnersacademy.entity.Ticket;
import com.winnersacademy.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TicketController {


    private final TicketRepository ticketRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<Ticket>> getTickets() {
        List<Ticket> result = ticketRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ticket")
    public ResponseEntity<Ticket> getTicketByDate(@RequestParam(name = "ticketDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Ticket result = ticketRepository.findByDate(date);
        return ResponseEntity.ok(result);
    }


}

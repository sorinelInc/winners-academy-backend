package com.winnersacademy.repository;

import com.winnersacademy.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByDate(LocalDate date);

}

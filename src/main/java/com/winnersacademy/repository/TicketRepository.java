package com.winnersacademy.repository;

import com.winnersacademy.entity.Ticket;
import com.winnersacademy.model.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByTicketType(TicketType type);

    Ticket findByDateAndTicketType(LocalDate date, TicketType type);

}

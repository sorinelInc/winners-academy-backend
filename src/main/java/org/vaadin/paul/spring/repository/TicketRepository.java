package org.vaadin.paul.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vaadin.paul.spring.model.Ticket;

import java.sql.Date;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByDate(Date date);

}

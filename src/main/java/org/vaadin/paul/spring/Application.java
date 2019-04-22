package org.vaadin.paul.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner loadData(TicketRepository repository) {
        return (args) -> {

//            LocalDate today = LocalDate.now();

            Ticket todayTicket = Ticket.builder().date(new Date(System.currentTimeMillis()))
                    .totalOdds(3.45D)
                    .matchList(new ArrayList<>(Arrays.asList(Match.builder()
                                    .name("Frosinone")
                                    .build(),
                            Match.builder()
                                    .name("Internationaleee")
                                    .build()
                    )))
                    .build();


            Ticket yersterdayTicket = Ticket.builder().date(new Date(System.currentTimeMillis()))
                    .totalOdds(55.63)
                    .matchList(new ArrayList<>(Arrays.asList(Match.builder()
                            .name("Fregamo")
                            .build())))
                    .build();

            Ticket afterTicket = new Ticket();


            repository.save(todayTicket);
            repository.save(yersterdayTicket);
            repository.save(afterTicket);

        };
    }


}

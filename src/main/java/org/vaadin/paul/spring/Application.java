package org.vaadin.paul.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.vaadin.paul.spring.model.Match;
import org.vaadin.paul.spring.model.Result;
import org.vaadin.paul.spring.model.Ticket;
import org.vaadin.paul.spring.repository.TicketRepository;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner loadData(TicketRepository repository) {
        return (args) -> {

            Date today = Date.from(Instant.now());
            Date yesterday = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));

            Ticket todayTicket = Ticket.builder().date(today).build();
            Ticket yersterdayTicket = Ticket.builder().date(yesterday).build();


            List<Match> yesterdayMatches = Arrays.asList(
                    Match.builder()
                            .date(yesterday)
                            .name("Nicolaus Copernicus")
                            .tips("1X")
                            .result(Result.WON)
                            .odds(3.45D)
                            .build(),
                    Match.builder()
                            .date(yesterday)
                            .name("Asdsaasd sada")
                            .tips("1")
                            .result(Result.LOST)
                            .odds(1.25D)
                            .build(),
                    Match.builder()
                            .date(yesterday)
                            .name("sds Sdsd")
                            .tips("Over 2.5")
                            .result(Result.WON)
                            .odds(1.45D)
                            .build()
            );

            List<Match> todayMatches = Arrays.asList(
                    Match.builder()
                            .date(today)
                            .name("Nicolaus Copernicus")
                            .tips("1X")
                            .result(Result.WON)
                            .odds(3.45D)
                            .build()
                    );

            yesterdayMatches.forEach(yersterdayTicket::addMatch);
            todayMatches.forEach(todayTicket::addMatch);

            repository.save(yersterdayTicket);
            repository.save(todayTicket);

        };
    }


}

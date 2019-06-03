package com.winnersacademy;

import com.winnersacademy.entity.Match;
import com.winnersacademy.entity.Ticket;
import com.winnersacademy.model.Result;
import com.winnersacademy.repository.TicketRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner loadData(TicketRepository repository) {
        return (args) -> {

            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

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

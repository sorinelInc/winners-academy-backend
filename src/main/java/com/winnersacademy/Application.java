package com.winnersacademy;

import com.winnersacademy.entity.Match;
import com.winnersacademy.entity.Ticket;
import com.winnersacademy.entity.User;
import com.winnersacademy.model.Result;
import com.winnersacademy.model.TicketType;
import com.winnersacademy.model.UserType;
import com.winnersacademy.repository.TicketRepository;
import com.winnersacademy.repository.UserRepository;
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
    public CommandLineRunner loadData(TicketRepository ticketRepository,
                                      UserRepository userRepository) {
        return (args) -> {

            LocalDate today = LocalDate.now();
            LocalDate yesterday = today.minusDays(1);

            Ticket todayTicket = Ticket.builder()
                    .date(today)
                    .ticketType(TicketType.FREE)
                    .build();
            Ticket yersterdayTicket = Ticket.builder()
                    .date(yesterday)
                    .ticketType(TicketType.FREE)
                    .build();

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

            ticketRepository.save(yersterdayTicket);
            ticketRepository.save(todayTicket);

            User admin = User.builder()
                    .name("administrator")
                    .email("admin@yahoo.com")
                    .password("admin")
                    .type(UserType.ADMIN)
                    .build();

            User normalUser = User.builder()
                    .name("second_user")
                    .email("user@yahoo.com")
                    .password("user")
                    .type(UserType.NORMAL)
                    .build();


            for (int i = 0; i < 10; i++){
                User inner = User.builder()
                        .name("vip_user" + i)
                        .email("vip@yahoo.com" + i)
                        .password("vip")
                        .type(UserType.VIP)
                        .build();
                userRepository.save(inner);
            }

            User vipUser = User.builder()
                    .name("vip_user")
                    .email("vip@yahoo.com")
                    .password("vip")
                    .type(UserType.VIP)
                    .build();

            userRepository.save(admin);
            userRepository.save(normalUser);
            userRepository.save(vipUser);

        };
    }


}

package org.vaadin.paul.spring.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Match {

    @Id
    private Long matchId;

    @Column
    private String name;

    @Column
    private Double odds;

    @Column
    private Date matchDate;

    @Column
    private String tips;

    @Column
    private Result result;

}

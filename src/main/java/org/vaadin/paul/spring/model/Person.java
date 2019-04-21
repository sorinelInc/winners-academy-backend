package org.vaadin.paul.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class Person {

    private String name;

    private String tips;

    private Result result;

    private Date date;

    private Double odds;
}

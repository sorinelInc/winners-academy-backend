package org.vaadin.paul.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    private String companyName;
    private String contactName;
    private String contactEmail;

    private String address;
    private String city;
    private String zip;

    private String region;
    private String country;
    private String phone;
    private String fax;
}

package com.example.jobis.domain.company.domain;

import com.example.jobis.domain.company.domain.embedded.Address;
import com.example.jobis.domain.company.domain.embedded.Contact;
import com.example.jobis.domain.company.domain.embedded.Manager;
import com.example.jobis.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Company extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 40, nullable = false)
    private String companyName;

    @Column(length = 10, nullable = false, unique = true)
    private String businessNumber;

    @Column(nullable = false)
    private String password;

    @Column(length = 4000, nullable = false)
    private String companyIntroduce;

    @Embedded
    private Address address;

    @Embedded
    private Manager manager;

    @Embedded
    private Contact contact;

    @Builder
    public Company(String companyName, String businessNumber, String password, String companyIntroduce, Address address, Manager manager, Contact contact) {
        this.companyName = companyName;
        this.businessNumber = businessNumber;
        this.password = password;
        this.companyIntroduce = companyIntroduce;
        this.address = address;
        this.manager = manager;
        this.contact = contact;
    }
}

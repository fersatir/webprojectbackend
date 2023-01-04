package com.webproject.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="tbl_user")
public class User {

    @Id
    Long id;

    String userName;

    String password;
}

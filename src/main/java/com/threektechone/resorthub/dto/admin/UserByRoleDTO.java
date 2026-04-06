package com.threektechone.resorthub.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserByRoleDTO {
    private int customer;
    private int owner;
    private int staff;
}

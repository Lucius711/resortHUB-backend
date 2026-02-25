package com.threektechone.resorthub.models;
import java.util.List;

import com.threektechone.resorthub.enums.RoleName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true, length = 20)
    private RoleName roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role() {
    }

    public Role(RoleName roleName, List<User> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public int getRoleId() {
        return role_id;
    }

    public void setRoleId(int role_id) {
        this.role_id = role_id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
    
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role_id=" + role_id +
                ", roleName='" + roleName + '\'' +
                ", users=" + users +
                '}';
    }
}

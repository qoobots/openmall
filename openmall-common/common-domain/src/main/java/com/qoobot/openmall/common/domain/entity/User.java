package com.qoobot.openmall.common.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "mobile", length = 11)
    private String mobile;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "avatar", length = 255)
    private String avatar;

    @Column(name = "gender")
    private Integer gender = 0;

    @Column(name = "birthday", length = 20)
    private String birthday;

    @Column(name = "user_type", nullable = false)
    private Integer userType;

    @Column(name = "status", nullable = false)
    private Integer status = 0;

    @Column(name = "last_login_time")
    private LocalDateTime lastLoginTime;

    @Column(name = "last_login_ip", length = 50)
    private String lastLoginIp;

    @Column(name = "register_source", length = 50)
    private String registerSource;
}

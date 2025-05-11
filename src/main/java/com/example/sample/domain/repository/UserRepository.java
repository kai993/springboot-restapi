package com.example.sample.domain.repository;

import java.util.List;
import java.util.Optional;

import org.postgresql.shaded.com.ongres.scram.client.ScramClient.UsernameBuildStage;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.sample.application.controller.request.UserCreateRequest;
import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.entity.User;

@Repository
public class UserRepository {
    
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    public List<AllUser> findAll() {
        String sql = """
        select
          id,
          username
        from
          users
        """;
        return jdbcClient.sql(sql)
                .query(AllUser.class)
                .list();
    }

    public Optional<User> findById(Long id) {
        String sql = """
        select
            *
        from
            users
        where
            id = :id
        """;
        return jdbcClient.sql(sql)
                .param("id", id)
                .query(User.class)
                .optional();
    }

    public boolean create(UserCreateRequest req) {
        String sql = """
        insert into users (
            username,
            password,
            birthdate,
            first_name,
            last_name
        ) values (
            :username,
            :password,
            :birthdate,
            :first_name,
            :last_name
        )
        """;
        jdbcClient.sql(sql)
            .param("username", req.getUsername())
            .param("password", req.getPassword())
            // :birthdate is a LocalDate
            .param("birthdate", req.getBirthdate())
            .param("first_name", req.getFirstName())
            .param("last_name", req.getLastName())
            .update();

        return true;
    }

    public String update(String user) {
        return "update";
    }

    public String delete(String user) {
        return "delete";
    }
}

package com.example.sample.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

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

    public String create(String user) {
        return "create";
    }

    public String update(String user) {
        return "update";
    }

    public String delete(String user) {
        return "update";
    }
}

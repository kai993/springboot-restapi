package com.example.sample.domain.repository;

import java.util.List;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.sample.domain.entity.User;

@Repository
public class UserRepository {
    
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    
    public List<User> findAll() {
        String sql = """
        select
          id,
          username
        from
          users
        """;
        return jdbcClient.sql(sql)
                .query(User.class)
                .list();
    }

    public String findById(long id) {
        return "findById";
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

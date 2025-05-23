package com.example.sample.domain.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import com.example.sample.application.controller.request.UserCreateRequest;
import com.example.sample.application.controller.request.UserUpdateRequest;
import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.entity.User;
import com.example.sample.domain.exception.NotFoundUserException;

@Repository
public class UserRepository {
    Logger logger = LoggerFactory.getLogger(UserRepository.class);
    
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
            id,
            username,
            birthdate,
            first_name,
            last_name,
            created_at,
            updated_at
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
            .param("birthdate", req.getBirthdate())
            .param("first_name", req.getFirstName())
            .param("last_name", req.getLastName())
            .update();

        return true;
    }

    public Optional<User> update(Long id, UserUpdateRequest req) throws NotFoundUserException {
        if (!isExistUser(id)) {
            throw new NotFoundUserException("ユーザーが存在しません id : " + id);
        }

        Map<String, Object> params = new HashMap<>();
        boolean isUpdate = false;

        // ---------- SQL生成 ----------
        StringBuilder updateSql = new StringBuilder("update users set ");
        if (req.getUsername() != null) {
            updateSql.append("username = :username, ");
            params.put("username", req.getUsername());
            isUpdate = true;
        }

        if (req.getPassword() != null) {
            updateSql.append("password = :password, ");
            params.put("password", req.getPassword());
            isUpdate = true;
        }

        if (req.getBirthdate() != null) {
            updateSql.append("birthdate = :birthdate, ");
            params.put("birthdate", req.getBirthdate());
            isUpdate = true;
        }

        if (req.getFirstName() != null) {
            updateSql.append("first_name = :first_name, ");
            params.put("first_name", req.getFirstName());
            isUpdate = true;
        }

        if (req.getLastName() != null) {
            updateSql.append("last_name = :last_name, ");
            params.put("last_name", req.getLastName());
            isUpdate = true;
        }

        if (id < 0) {
            throw new IllegalArgumentException("idが不正です");
        }
        updateSql.append("updated_at = now() ");

        updateSql.append("where id = :id");
        params.put("id", id);
        // ---------- SQL生成 ----------

        if (!isUpdate) {
            // 何も更新しない
            return Optional.empty();
        }

        // 更新
        jdbcClient.sql(updateSql.toString())
            .params(params)
            .update();

        String selectSql = """
        select
            *
        from
            users
        where
            id = :id
        """;
        return jdbcClient.sql(selectSql)
                .param("id", id)
                .query(User.class)
                .optional();
    }

    public boolean deleteById(Long id) throws NotFoundUserException {
        if (!isExistUser(id)) {
            throw new NotFoundUserException("ユーザーが存在しません id : " + id);
        }

        String sql = """
        delete from users
        where id = :id
        """;
        int rowsAffected = jdbcClient.sql(sql)
                .param("id", id)
                .update();

        if (rowsAffected == 0) {
            logger.warn("更新しませんでした。id: {}", id);
        }

        logger.info("ユーザーを削除しました。id: {}", id);
        return true;
    }

    private boolean isExistUser(Long id) {
        Optional<User> existingUser = findById(id);
        if (existingUser.isPresent()) {
            return true;
        } else {
            return false;
        }
    }
}

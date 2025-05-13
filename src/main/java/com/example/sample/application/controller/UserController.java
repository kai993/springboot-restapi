package com.example.sample.application.controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.application.controller.request.UserCreateRequest;
import com.example.sample.application.controller.request.UserUpdateRequest;
import com.example.sample.application.controller.response.ErrorResponse;
import com.example.sample.application.controller.response.GetAllUsersResponse;
import com.example.sample.application.controller.response.SuccessResponse;
import com.example.sample.domain.entity.AllUser;
import com.example.sample.domain.exception.NotFoundUserException;
import com.example.sample.domain.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    /**
     * ユーザー一覧(id, username)を取得する
     *
     * @return ユーザー一覧
     */
    @GetMapping
    public GetAllUsersResponse findAll() {
        List<AllUser> users = service.findAll();
        return GetAllUsersResponse.builder()
                .totalCount(users.size())
                .users(users)
                .build();
    }

    /**
     * ユーザー情報を取得する
     *
     * @param id ユーザーID
     * @return ユーザー情報
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            // ユーザー取得
            return ResponseEntity.ok(service.findById(id));
        } catch (NotFoundUserException e) {
            // ユーザーが見つからない場合
            logger.warn("User not found: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("error-user-id-00001", "ユーザーが見つかりませんでした。", "user_id: " + id));
        } catch (Exception e) {
            // その他のエラー
            logger.error("Error occurred while fetching user: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error-user-id-00002", "システムエラーが発生しました。", ""));
        }
    }

    /**
      * ユーザーを作成する
      *
      * @param request ユーザー情報
      * @return ResponseEntity
      */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserCreateRequest request) {
        try {
            // ユーザー作成
            boolean isCreated = service.create(request);
            if (isCreated) {
                return ResponseEntity.ok(new SuccessResponse("ok"));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("error-user-id-00003", "ユーザーの登録に失敗しました", ""));
            }
        } catch (Exception e) {
            logger.error("ユーザーの登録に失敗しました", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error-user-id-00004", "ユーザーの登録に失敗しました", ""));
        }
    }

    /**
     * ユーザー情報を更新する
     *
     * @param id ユーザーID
     * @param request ユーザー情報
     * @return 更新後のユーザー情報
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest request) {
        try {
            // ユーザー情報更新
            return ResponseEntity.ok(service.save(id, request));
        } catch (SQLException e) {
            // 更新エラー
            logger.error("ユーザー情報の更新に失敗しました: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error-user-id-00005", "ユーザー情報の更新に失敗しました", ""));
        }
    }

    /**
     * ユーザーを削除する
     *
     * @param id ユーザーID
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.ok(new SuccessResponse("ok"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("error-user-id-00006", "ユーザーの削除に失敗しました", ""));
        }
    }
}


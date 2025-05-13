# Spring Boot REST API

## ディレクトリ構成

```bash
❯ exa --tree src/main
src/main
├── java
│  └── com
│     └── example
│        └── sample
│           ├── application/      # アプリケーション層
│           │  └── controller/
│           │     ├── request/
│           │     ├── response/
│           │     └── validation/
│           ├── config/           # 設定
│           ├── domain/           # ドメイン層
│           │  ├── exception/
│           │  ├── repository/
│           │  └── service/
│           ├── infrastructure/   # インフラストラクチャ層(データの永続化、外部APIとの通信など)
│           └── SampleServiceApplication.java
└── resources
   ├── application.yaml
   ├── static
   └── templates
```

## データベース

- データベース : db1
- 接続ユーザー : test
- テーブル : users

```sql
-- テーブル一覧
db1=# \dt
           リレーション一覧
 スキーマ | 名前  |  タイプ  | 所有者
----------+-------+----------+--------
 public   | tbl1  | テーブル | test
 public   | users | テーブル | test
(2 行)

-- テーブル作成
create table users (
    id serial primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    birthdate date not null,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

-- テストデータ
insert into users (username, password, birthdate, first_name, last_name)
values 
  ('john_doe', 'password123', '1990-01-01', 'John', 'Doe'),
  ('jane_smith', 'secret456', '1985-05-12', 'Jane', 'Smith'),
  ('alice_wonder', 'qwerty789', '1992-03-25', 'Alice', 'Wonderland'),
  ('bob_builder', 'builder321', '1988-07-19', 'Bob', 'Builder'),
  ('charlie_brown', 'snoopy111', '1979-11-30', 'Charlie', 'Brown'),
  ('david_tennant', 'doctorwho', '1971-04-18', 'David', 'Tennant'),
  ('emily_blunt', 'quiet123', '1983-02-23', 'Emily', 'Blunt'),
  ('frank_castle', 'punisher', '1980-06-15', 'Frank', 'Castle'),
  ('grace_hopper', 'navycode', '1906-12-09', 'Grace', 'Hopper'),
  ('harry_potter', 'hogwarts', '1990-07-31', 'Harry', 'Potter');

db1=# select * from users;
 id |   username    |  password   | birthdate  | first_name | last_name  |        created_at         |        updated_at
----+---------------+-------------+------------+------------+------------+---------------------------+---------------------------
  1 | john_doe      | password123 | 1990-01-01 | John       | Doe        | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  2 | jane_smith    | secret456   | 1985-05-12 | Jane       | Smith      | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  3 | alice_wonder  | qwerty789   | 1992-03-25 | Alice      | Wonderland | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  4 | bob_builder   | builder321  | 1988-07-19 | Bob        | Builder    | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  5 | charlie_brown | snoopy111   | 1979-11-30 | Charlie    | Brown      | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  6 | david_tennant | doctorwho   | 1971-04-18 | David      | Tennant    | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  7 | emily_blunt   | quiet123    | 1983-02-23 | Emily      | Blunt      | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  8 | frank_castle  | punisher    | 1980-06-15 | Frank      | Castle     | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
  9 | grace_hopper  | navycode    | 1906-12-09 | Grace      | Hopper     | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
 10 | harry_potter  | hogwarts    | 1990-07-31 | Harry      | Potter     | 2025-05-11 15:25:14.23981 | 2025-05-11 15:25:14.23981
(10 行)
```


## API一覧

http://localhost:8080/swagger-ui/index.html#/

```bash
# ユーザー一覧
❯ curl -s 'http://localhost:8080/api/v1/users' | jq -r '.users[0]'
{
  "id": 1,
  "username": "john_doe"
}

# ユーザー詳細
❯ curl -s 'http://localhost:8080/api/v1/users/11' | jq .
{
  "id": 11,
  "username": "test01",
  "password": "pass01",
  "birthdate": "1990-01-01",
  "firstName": "fname",
  "lastName": "lname",
  "createdAt": "2025-05-11T23:13:45.213836",
  "updatedAt": "2025-05-13T14:33:09.606345"
}

# ユーザー作成
❯ curl -X POST \
    -H 'Content-Type: application/json' \
    -d '{
      "username": "test01",
      "password": "pass01",
      "birthdate": "1990-01-01",
      "firstName": "fname",
      "lastName": "lname"
    }' \
    'http://localhost:8080/api/v1/users'
{
  "message": "ok"
}

# ユーザー更新
❯ curl -X PUT \
    -H 'Content-Type: application/json' \
    -d '{
      "username": "test02",
      "password": "pass02",
      "firstName": "ffname",
      "lastName": "llname",
      "birthdate": "1995-01-01"
    }' \
    'http://localhost:8080/api/v1/users/11' | jq .
{
  "id": 11,
  "username": "test02",
  "password": "pass02",
  "birthdate": "1995-01-01",
  "firstName": "ffname",
  "lastName": "llname",
  "createdAt": "2025-05-11T23:13:45.213836",
  "updatedAt": "2025-05-13T14:35:47.948522"
}

# ユーザー削除
❯ curl -s -X DELETE 'http://localhost:8080/api/v1/users/11' | jq .
```


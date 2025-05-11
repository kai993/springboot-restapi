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

## API一覧

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /home | home |
| GET    | /health | ヘルスチェック |



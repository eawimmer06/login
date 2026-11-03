# Login and registration app

Spring Boot 3.4.5, Java 21, Thymeleaf, Spring Security, Spring Data JPA, PostgreSQL.

## Features
- Register with email, username, password, and account type.
- Passwords are hashed with BCrypt. They are never stored as plaintext and are not reversibly encrypted.
- Incorrect login credentials return to `/login?error` and show an error message.
- Only authenticated users can access `/`; `/admin/**` requires the ADMIN role.
- Admin registration requires a server-side invite code.

## 1. Create the PostgreSQL database
Using psql or pgAdmin, create a database named `login_app`:
```sql
CREATE DATABASE login_app;
```
If your PostgreSQL username/password differ from `postgres`/`postgres`, set `DB_USERNAME` and `DB_PASSWORD`.

## 2. Set an admin invite code (optional)
To permit admin registration, set an environment variable before starting the app:
- Windows PowerShell: `$env:APP_ADMIN_INVITE_CODE = "use-a-long-random-secret"`
- macOS/Linux: `export APP_ADMIN_INVITE_CODE="use-a-long-random-secret"`

Without this value, regular USER registration works, but ADMIN registration is rejected.

## 3. Run
From the project root:
```bash
mvn spring-boot:run
```
Open http://localhost:8080/login

## Notes
- `spring.jpa.hibernate.ddl-auto=update` creates/updates the table for local development. Use database migrations for production.
- Do not commit real database credentials or admin invite codes.
- For production, also use HTTPS, rate limiting, email verification, password reset, and stronger operational secret management.

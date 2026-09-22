# Mentro — Project Specification

## 1. Overview
Mentro is a peer tutoring platform where tutors create **gigs** (fixed-capacity, one-month courses) and students **enroll**. Enrollment requires tutor confirmation before any money moves. Payments use a virtual wallet system (no real payment gateway for v1).

## 2. Roles
- **Student** — browses gigs, enrolls, manages wallet
- **Tutor** — creates/manages gigs, confirms or rejects enrollment requests
- **Admin** — monitors users, can read/delete profiles only (no booking/gig control)

## 3. Entities

| Entity | Purpose |
|---|---|
| User | Base account (id, name, email, password, role) |
| Student | Student-specific profile, linked 1:1 to User |
| Tutor | Tutor-specific profile, linked 1:1 to User |
| Wallet | Virtual balance, linked 1:1 to User |
| Gig | A tutor's course offering (title, subject, fee, capacity, duration) |
| Enrollment | Join between Student and Gig; tracks status and lifecycle |
| Transaction | Record of money movement, linked to Wallet and Enrollment |

## 4. Relationships (ERD)
```
User 1 —— 1 Student
User 1 —— 1 Tutor
User 1 —— 1 Wallet
Tutor 1 —— many Gig
Gig 1 —— many Enrollment
Student 1 —— many Enrollment
Wallet 1 —— many Transaction
Enrollment 1 —— 1 Transaction
```

## 5. Business Rules

### Students
1. Can register and log in.
2. Can view all gigs offered by tutors.
3. Can request to enroll in a gig (creates Enrollment with status `PENDING`) — no money is deducted at this point.
4. Can cancel their own enrollment while it is still `PENDING`, with no charge.
5. Cannot enroll in a gig that has reached capacity (based on `CONFIRMED` enrollments only).
6. Start with a fixed testing balance (e.g. 10,000 Rs) in their wallet.
7. Can enroll in a maximum of 3 gigs per month (from original notes — confirm still applies under gig model).

### Tutors
1. Can register and log in.
2. Can create, edit, and delete their own gigs (full CRUD), **except** a gig cannot be edited/deleted once it has active enrollments.
3. Each gig has a capacity of 4–5 students.
4. Each gig runs for one month by default.
5. Receive a notification/visibility of new enrollment requests.
6. Must confirm or reject each enrollment request:
   - **Confirm** → Enrollment status → `CONFIRMED`; wallet debit (student) and credit (tutor) occur immediately.
   - **Reject** → Enrollment status → `CANCELLED`; no money moves.
7. Can view other tutors' public gigs (browsing, not editing).
8. After a gig's one-month period ends, tutor may choose to renew it — students in the completed gig can then re-enroll (renewal); if not renewed, the gig simply ends.

### Admin
1. Has built-in credentials (not self-registered).
2. Can view all registered users.
3. Can read and delete user profiles only — **no access to gigs, enrollments, or transactions**.

### Enrollment Lifecycle (state machine)
```
PENDING → (tutor confirms) → CONFIRMED → (1 month passes) → COMPLETED
   |                              |
   | (student cancels             | (per current rule: no cancellation/refund
   |  OR tutor rejects)           |  once CONFIRMED — confirm this is final)
   v
CANCELLED (no money ever moved)
```

### Money Flow
- No charge on enrollment request (`PENDING`).
- On tutor confirmation: **immediate** debit from student wallet, **immediate** credit to tutor wallet (no escrow/hold — noted as a v2 improvement).
- No refunds after confirmation (open question — see Section 7).

### Capacity & Renewal
- A gig is "Full" once it has 5 `CONFIRMED` enrollments (not pending ones).
- After 1 month, a `CONFIRMED` enrollment moves to `COMPLETED`.
- Tutor may re-open the gig for renewal; previously enrolled students can re-enroll for another cycle.
- If tutor does not renew, the gig simply closes — no further action needed.

## 6. API Endpoints

### Auth
- `POST /auth/register`
- `POST /auth/login`

### Gigs
- `POST /gigs` — Tutor only
- `GET /gigs` — public/student browsing, supports filters e.g. `?subject=java&maxFee=1000`
- `GET /gigs/{id}`
- `PATCH /gigs/{id}` — Tutor only, owner-only, blocked if active enrollments exist
- `DELETE /gigs/{id}` — Tutor only, owner-only, blocked if active enrollments exist

### Enrollments
- `POST /gigs/{id}/enrollments` — Student only → creates `PENDING` enrollment
- `PATCH /enrollments/{id}/confirm` — Tutor only, owner-only (must own the gig)
- `PATCH /enrollments/{id}/reject` — Tutor only, owner-only
- `PATCH /enrollments/{id}/cancel` — Student only, own enrollment, only while `PENDING`
- `PATCH /enrollments/{id}/renew` — after `COMPLETED`, re-opens a new cycle
- `GET /enrollments/me` — Student's own enrollments
- `GET /gigs/{id}/enrollments` — Tutor only, owner-only — view requests for their gig

### Wallet
- `GET /wallet/me`
- `GET /wallet/me/transactions`

### Admin
- `GET /admin/users`
- `DELETE /admin/users/{id}`

### [FUTURE — not in v1]
- `GET /tutors/{id}` — public tutor profile page
- Real-time chat (WebSocket)
- Reviews/ratings
- Escrow-style held payments (release on gig completion instead of immediate)
- Payment gateway integration (real money)

## 7. Open Questions (resolve before/while building)
1. **Refund after confirmation** — does "no refund" mean the student literally cannot cancel once confirmed, or they can cancel but forfeit the money? Pick one and update Section 5.
2. **Pending-request spam** — should there be a cap on how many `PENDING` requests a gig can hold at once, to avoid a tutor being flooded for 5 real seats?
3. **Renewal mechanics** — is renewal a brand new Enrollment row, or does the existing Enrollment get an updated `expires_at`? Decide before writing the renewal endpoint.

## 8. Tech Stack
- Backend: Java, Spring Boot (Web, Data JPA, Security, Validation), Lombok
- Database: PostgreSQL
- Frontend: TBD (React or server-rendered — decide before Day 2 wraps up)
- Deployment: TBD (Render/Railway suggested for free-tier Postgres + backend hosting)

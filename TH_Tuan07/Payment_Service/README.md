# Payment Service (Payment + Notification)

Spring Boot service for Movie Ticket System event-driven flow.

## Responsibilities

- Consume `BOOKING_CREATED` event from Kafka
- Simulate payment (random success/fail)
- Persist payment into `payments`
- Publish `PAYMENT_COMPLETED` or `BOOKING_FAILED`
- Consume payment result events and write notification logs into `notification_logs`

## Event Flow

`BOOKING_CREATED -> PaymentService -> PAYMENT_COMPLETED | BOOKING_FAILED -> NotificationService`

## Kafka Topics

- `booking.created`
- `payment.completed`
- `booking.failed`

## APIs

- `GET /api/payments/booking/{bookingId}`
- `GET /api/notifications/booking/{bookingId}`

## Run

Set environment variables if needed:

- `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- `KAFKA_BOOTSTRAP_SERVERS`, `KAFKA_GROUP_ID`
- `KAFKA_LISTENER_AUTO_STARTUP` (default is `true`; set `false` when you want to disable listeners)

By default, Kafka listeners are enabled at boot for full event-driven flow.

Then start service:

```bash
./mvnw spring-boot:run
```


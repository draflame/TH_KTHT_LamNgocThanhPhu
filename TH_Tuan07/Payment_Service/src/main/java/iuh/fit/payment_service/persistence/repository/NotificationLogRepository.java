package iuh.fit.payment_service.persistence.repository;

import iuh.fit.payment_service.persistence.entity.NotificationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationLogRepository extends JpaRepository<NotificationLogEntity, Long> {

    List<NotificationLogEntity> findByBookingId(Long bookingId);
}


package iuh.fit.payment_service.persistence.repository;

import iuh.fit.payment_service.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    boolean existsByBookingId(Long bookingId);

    Optional<PaymentEntity> findByBookingId(Long bookingId);
}


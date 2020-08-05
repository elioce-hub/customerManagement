package hrs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerReservationInfoRepository extends CrudRepository<CustomerReservationInfo, Long> {


    Optional<CustomerReservationInfo> findByReservationId(Long reservationId);
}
package hrs;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerReservationInfoRepository extends CrudRepository<CustomerReservationInfo, Long> {


    CustomerReservationInfo findByReservationId(Long reservationId);
}

package hrs.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name="Reservationmanagement", url="${external.url.rm}")
public interface HotelReservationManagementService {

    @RequestMapping(method= RequestMethod.POST, path="/hotelReservationManagements/{email}")
    public void availableCouponCheck(@RequestBody HotelReservationManagement hotelReservationManagement);

}
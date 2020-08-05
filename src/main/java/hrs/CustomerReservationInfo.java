package hrs;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="CustomerReservationInfo_table")
public class CustomerReservationInfo {

        @Id
        private Long reservationId;
        private String email;
        private String customerName;
        private String status;


        public Long getReservationId() {
            return reservationId;
        }

        public void setReservationId(Long reservationId) {
            this.reservationId = reservationId;
        }
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

}

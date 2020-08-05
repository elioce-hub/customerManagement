package hrs;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="CustomerManagement_table")
public class CustomerManagement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String email;
    private Integer totalReservationCnt;
    private Integer cancelCnt;
    private Integer couponIssueReservationCnt;
    private String lastCouponIssueDate;
    private String couponStatus;
    private String customerName;
    private String couponPin;

    @PostUpdate
    public void onPostUpdate(){
        ConfirmationEmailSent confirmationEmailSent = new ConfirmationEmailSent();
        BeanUtils.copyProperties(this, confirmationEmailSent);
        confirmationEmailSent.publishAfterCommit();


        ReservationCancellationEmailSent reservationCancellationEmailSent = new ReservationCancellationEmailSent();
        BeanUtils.copyProperties(this, reservationCancellationEmailSent);
        reservationCancellationEmailSent.publishAfterCommit();


    }

    @PreUpdate
    public void onPreUpdate(){
        CouponIssued couponIssued = new CouponIssued();
        BeanUtils.copyProperties(this, couponIssued);
        couponIssued.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        hrs.external.HotelReservationManagement hotelReservationManagement = new hrs.external.HotelReservationManagement();
        // mappings goes here
        CustomerManagementApplication.applicationContext.getBean(hrs.external.HotelReservationManagementService.class)
            .availableCouponCheck(hotelReservationManagement);


    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getTotalReservationCnt() {
        return totalReservationCnt;
    }

    public void setTotalReservationCnt(Integer totalReservationCnt) {
        this.totalReservationCnt = totalReservationCnt;
    }
    public Integer getCancelCnt() {
        return cancelCnt;
    }

    public void setCancelCnt(Integer cancelCnt) {
        this.cancelCnt = cancelCnt;
    }
    public Integer getCouponIssueReservationCnt() {
        return couponIssueReservationCnt;
    }

    public void setCouponIssueReservationCnt(Integer couponIssueReservationCnt) {
        this.couponIssueReservationCnt = couponIssueReservationCnt;
    }
    public String getLastCouponIssueDate() {
        return lastCouponIssueDate;
    }

    public void setLastCouponIssueDate(String lastCouponIssueDate) {
        this.lastCouponIssueDate = lastCouponIssueDate;
    }
    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCouponPin() {
        return couponPin;
    }

    public void setCouponPin(String couponPin) {
        this.couponPin = couponPin;
    }




}

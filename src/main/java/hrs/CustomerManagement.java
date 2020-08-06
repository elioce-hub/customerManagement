package hrs;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="CustomerManagement_table")
public class CustomerManagement {

    @Id
    private String email;
    private Integer totalReservationCnt;
    private Integer cancelCnt;
    private Integer couponIssueReservationCnt;
    private String lastCouponIssueDate;
    private String couponStatus;
    private String customerName;
    private String couponPin;
    private String prcsDvsn; //처리구분 CREATE, CONFIRM, CANCEL, COUPON
    private Long emailReservationId;

    @PostUpdate
    public void onPostUpdate(){

        if("CONFIRM".equals(getPrcsDvsn())){

            ConfirmationEmailSent confirmationEmailSent = new ConfirmationEmailSent();
            BeanUtils.copyProperties(this, confirmationEmailSent);
            confirmationEmailSent.setId(getEmailReservationId());
            confirmationEmailSent.publishAfterCommit();
        }

        if("CANCEL".equals(getPrcsDvsn())){

            ReservationCancellationEmailSent reservationCancellationEmailSent = new ReservationCancellationEmailSent();
            BeanUtils.copyProperties(this, reservationCancellationEmailSent);
            reservationCancellationEmailSent.setId(getEmailReservationId());
            reservationCancellationEmailSent.publishAfterCommit();
        }
    }

    @PreUpdate
    public void onPreUpdate(){
        System.out.println("### onPreUpdate ###");

        if("COUPON".equals(getPrcsDvsn())){

            System.out.println("### CustomerManagement onPreUpdate IN ###");

            CouponIssued couponIssued = new CouponIssued();
            BeanUtils.copyProperties(this, couponIssued);

            //쿠폰번호생성
            couponIssued.setCouponIssueReservationCnt(getTotalReservationCnt());
            couponIssued.setCouponPin("C1234D22");
            couponIssued.setCouponStatus("ISSUED");
            couponIssued.setLastCouponIssueDate("20200805");
            
            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

            hrs.external.HotelReservationManagement hotelReservationManagement = new hrs.external.HotelReservationManagement();
            // mappings goes here
            hotelReservationManagement.setCouponPin(couponIssued.getCouponPin());

            CustomerManagementApplication.applicationContext.getBean(hrs.external.HotelReservationManagementService.class)
                .availableCouponCheck(hotelReservationManagement);

            // 테이블 업데이트 값 세팅
            setCouponIssueReservationCnt(couponIssued.getCouponIssueReservationCnt());
            setCouponPin(couponIssued.getCouponPin());
            setCouponStatus(couponIssued.getCouponStatus());
            setLastCouponIssueDate(couponIssued.getLastCouponIssueDate());

            couponIssued.publishAfterCommit();
        }
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
    public String getPrcsDvsn() {
        return prcsDvsn;
    }

    public void setPrcsDvsn(String prcsDvsn) {
        this.prcsDvsn = prcsDvsn;
    }
    public Long getEmailReservationId() {
        return emailReservationId;
    }

    public void setEmailReservationId(Long emailReservationId) {
        this.emailReservationId = emailReservationId;
    }


}

package hrs;

public class CouponIssued extends AbstractEvent {

    private Long id;
    private String email;
    private Integer couponIssueReservationCnt;
    private String lastCouponIssueDate;
    private String couponStatus;
    private String customerName;
    private String couponPin;

    public CouponIssued(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

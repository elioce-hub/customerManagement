package hrs.external;

public class HotelReservationManagement {

    private Long id;
    private Long reservationId;
    private String status;
    private String couponPin;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getReservationId() {
        return reservationId;
    }
    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCouponPin() {
        return couponPin;
    }
    public void setCouponPin(String couponPin) {
        this.couponPin = couponPin;
    }

}

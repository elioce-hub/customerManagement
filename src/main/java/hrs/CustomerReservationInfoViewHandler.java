package hrs;

import hrs.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerReservationInfoViewHandler {


    @Autowired
    private CustomerReservationInfoRepository customerReservationInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Reserved reserved) {
        try {
            if (reserved.isMe()) {
                // view 객체 생성
                CustomerReservationInfo customerReservationInfo = new CustomerReservationInfo();
                // view 객체에 이벤트의 Value 를 set 함
                customerReservationInfo.setEmail(reserved.getEmail());
                customerReservationInfo.setCustomerName(reserved.getCustomerName());
                customerReservationInfo.setReservationId(reserved.getId());
                customerReservationInfo.setStatus(reserved.getStatus());
                // view 레파지 토리에 save
                customerReservationInfoRepository.save(customerReservationInfo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationConfirmed_then_UPDATE_1(@Payload ReservationConfirmed reservationConfirmed) {
        try {
            if (reservationConfirmed.isMe()) {
                // view 객체 조회
                Optional<CustomerReservationInfo> customerReservationInfoOptional = customerReservationInfoRepository.findByReservationId(reservationConfirmed.getReservationId());
                if( customerReservationInfoOptional.isPresent()) {
                    CustomerReservationInfo customerReservationInfo = customerReservationInfoOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerReservationInfo.setStatus(reservationConfirmed.getStatus());
                    // view 레파지 토리에 save
                    customerReservationInfoRepository.save(customerReservationInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationConfirmedCanceled_then_UPDATE_2(@Payload ReservationConfirmedCanceled reservationConfirmedCanceled) {
        try {
            if (reservationConfirmedCanceled.isMe()) {
                // view 객체 조회
                Optional<CustomerReservationInfo> customerReservationInfoOptional = customerReservationInfoRepository.findByReservationId(reservationConfirmedCanceled.getReservationId());
                if( customerReservationInfoOptional.isPresent()) {
                    CustomerReservationInfo customerReservationInfo = customerReservationInfoOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    customerReservationInfo.setStatus(reservationConfirmedCanceled.getStatus());
                    // view 레파지 토리에 save
                    customerReservationInfoRepository.save(customerReservationInfo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
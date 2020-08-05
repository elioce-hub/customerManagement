package hrs;

import hrs.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    CustomerManagementRepository customerManagementRepository;

    @Autowired
    CustomerReservationInfoRepository customerReservationInfoRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationConfirmed_EmailSebding(@Payload ReservationConfirmed reservationConfirmed){
        //예약확정
        if(reservationConfirmed.isMe()){
            System.out.println("##### listener EmailSebding : " + reservationConfirmed.toJson());
            CustomerReservationInfo customerReservationInfo = customerReservationInfoRepository.findByReservationId(reservationConfirmed.getReservationId().longValue());

            if(customerReservationInfo != null){
                //고객예약데이터 조회
                String email = customerReservationInfo.getEmail();

                CustomerManagement customerManagement = customerManagementRepository.findByEmail(email);

                customerManagement.setTotalReservationCnt(customerManagement.getTotalReservationCnt()+1); //예약건수증가

                customerManagementRepository.save(customerManagement);
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationConfirmedCanceled_EmailSebding(@Payload ReservationConfirmedCanceled reservationConfirmedCanceled){
        //예약취소
        if(reservationConfirmedCanceled.isMe()){
            System.out.println("##### listener EmailSebding : " + reservationConfirmedCanceled.toJson());
            CustomerReservationInfo customerReservationInfo = customerReservationInfoRepository.findByReservationId(reservationConfirmedCanceled.getReservationId().longValue());

            if(customerReservationInfo != null){
                //고객예약데이터 조회
                String email = customerReservationInfo.getEmail();

                CustomerManagement customerManagement = customerManagementRepository.findByEmail(email);

                customerManagement.setTotalReservationCnt(customerManagement.getTotalReservationCnt()+1); //예약건수증가

                customerManagementRepository.save(customerManagement);
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_CustInfoCreate(@Payload Reserved reserved){
        //고객데이터 생성.
        if(reserved.isMe()){
            System.out.println("##### listener CustInfoCreate : " + reserved.toJson());
            CustomerManagement customerManagement = customerManagementRepository.findByEmail(reserved.getEmail());

            if(customerManagement != null){
                //신규고객
                customerManagement = new CustomerManagement();

                customerManagement.setEmail(reserved.getEmail());
                customerManagement.setCustomerName(reserved.getCustomerName());
                customerManagement.setCancelCnt(0);
                customerManagement.setTotalReservationCnt(0);

                customerManagementRepository.save(customerManagement);
            }
        }
    }
}

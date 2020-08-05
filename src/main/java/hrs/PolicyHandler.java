package hrs;

import hrs.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
            Optional<CustomerReservationInfo> customerReservationInfoOptional = customerReservationInfoRepository.findByReservationId(reservationConfirmed.getReservationId().longValue());

            if(customerReservationInfoOptional.isPresent()){
                CustomerReservationInfo customerReservationInfo = customerReservationInfoOptional.get();
                String email = customerReservationInfo.getEmail();

                CustomerManagement customerManagement = customerManagementRepository.findByEmail(email);
                if(customerManagement != null){
                    customerManagement.setTotalReservationCnt(customerManagement.getTotalReservationCnt()+1); //예약건수증가
                    customerManagement.setPrcsDvsn("CONFIRM");
                    customerManagement.setEmailReservationId(reservationConfirmed.getReservationId().longValue());
                    customerManagementRepository.save(customerManagement);
                }
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationConfirmedCanceled_EmailSebding(@Payload ReservationConfirmedCanceled reservationConfirmedCanceled){
        //예약취소
        if(reservationConfirmedCanceled.isMe()){
            System.out.println("##### listener EmailSebding : " + reservationConfirmedCanceled.toJson());
            Optional<CustomerReservationInfo> customerReservationInfoOptional = customerReservationInfoRepository.findByReservationId(reservationConfirmedCanceled.getReservationId().longValue());

            if(customerReservationInfoOptional.isPresent()){
                CustomerReservationInfo customerReservationInfo = customerReservationInfoOptional.get();
                String email = customerReservationInfo.getEmail();

                CustomerManagement customerManagement = customerManagementRepository.findByEmail(email);
                if(customerManagement != null){
                    customerManagement.setCancelCnt(customerManagement.getCancelCnt()+1); //예약건수증가
                    customerManagement.setPrcsDvsn("CANCEL");
                    customerManagement.setEmailReservationId(reservationConfirmedCanceled.getReservationId().longValue());
                    customerManagementRepository.save(customerManagement);
                }
            }
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_CustInfoCreate(@Payload Reserved reserved){
        //고객데이터 생성.
        if(reserved.isMe()){
            System.out.println("##### listener CustInfoCreate : " + reserved.toJson());
            CustomerManagement customerManagement = customerManagementRepository.findByEmail(reserved.getEmail());

            if(customerManagement == null){
                //신규고객
                customerManagement = new CustomerManagement();

                customerManagement.setEmail(reserved.getEmail());
                customerManagement.setCustomerName(reserved.getCustomerName());
                customerManagement.setCancelCnt(0);
                customerManagement.setTotalReservationCnt(0);
                customerManagement.setPrcsDvsn("CREATE");
                System.out.println("##### listener CustInfoCreate customerManagement : " + customerManagement.toString());

                customerManagementRepository.save(customerManagement);
            }
        }
    }
}

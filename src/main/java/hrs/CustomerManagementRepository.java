package hrs;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerManagementRepository extends PagingAndSortingRepository<CustomerManagement, String>{

    CustomerManagement findByEmail(String email);
}
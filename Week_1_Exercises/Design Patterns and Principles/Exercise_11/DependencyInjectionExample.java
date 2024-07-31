package Dependency_Injection_Example;

public class DependencyInjectionExample {
    public static void main(String[] args) {
        // Create the repository instance
        CustomerRepository customerRepository = new CustomerRepositoryImpl();

        // Create the service instance with the repository injected
        CustomerService customerService = new CustomerService(customerRepository);

        // Use the service to find customer details
        String customerDetails = customerService.getCustomerDetails(1);
        System.out.println(customerDetails);
    }

    // Define Repository Interface
    public interface CustomerRepository {
        String findCustomerById(int id);
    }

    // Implement Concrete Repository
    public static class CustomerRepositoryImpl implements CustomerRepository {
        @Override
        public String findCustomerById(int id) {
            // For demonstration purposes, return a dummy customer
            return "Customer with ID: " + id;
        }
    }

    // Define Service Class
    public static class CustomerService {
        private CustomerRepository customerRepository;

        // Constructor Injection
        public CustomerService(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }

        public String getCustomerDetails(int id) {
            return customerRepository.findCustomerById(id);
        }
    }
}

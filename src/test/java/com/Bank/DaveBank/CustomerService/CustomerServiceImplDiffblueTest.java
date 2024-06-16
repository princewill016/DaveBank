package com.Bank.DaveBank.CustomerService;

import com.Bank.DaveBank.CustomerDTO.CreditDebit;
import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerEntity.Customer;
import com.Bank.DaveBank.CustomerRepository.CustomerRepository;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import com.Bank.DaveBank.CustomerUtils.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CustomerServiceImpl.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerServiceImplDiffblueTest {
   @MockBean
   private CustomerRepository customerRepository;

   @Autowired
   private CustomerServiceImpl customerServiceImpl;

   @MockBean
   private EmailService emailService;

   @MockBean
   private PasswordEncoder passwordEncoder;

   /**
    * Method under test: {@link CustomerServiceImpl#createAccount(CustomerDto)}
    */
   @Test
   void testCreateAccount() {
      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      when(customerRepository.findByEmail(Mockito.any())).thenReturn(ofResult);

      // Act
      BankResponse actualCreateAccountResult = customerServiceImpl.createAccount(new CustomerDto("Jane", "Doe", "Other Name", "Gender", "jane.doe@example.org", "iloveyou", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144"));

      // Assert
      verify(customerRepository).findByEmail(eq("jane.doe@example.org"));
      assertEquals("405", actualCreateAccountResult.responseCode);
      assertEquals("Account already exist", actualCreateAccountResult.responseMessage);
      assertNull(actualCreateAccountResult.accountInfo);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#createAccount(CustomerDto)}
    */
   @Test
   void testCreateAccount2() {
      // Arrange
      when(customerRepository.findByEmail(Mockito.any())).thenThrow(new IllegalStateException("%04d%06d"));

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> customerServiceImpl.createAccount(new CustomerDto("Jane", "Doe", "Other Name", "Gender", "jane.doe@example.org", "iloveyou", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144")));
      verify(customerRepository).findByEmail(eq("jane.doe@example.org"));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#createAccount(CustomerDto)}
    */
   @Test
   void testCreateAccount3() {
      // Arrange
      when(passwordEncoder.encode(Mockito.any())).thenReturn("secret");

      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      when(customerRepository.save(Mockito.any())).thenReturn(customer);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByEmail(Mockito.any())).thenReturn(emptyResult);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      BankResponse actualCreateAccountResult = customerServiceImpl.createAccount(new CustomerDto("Jane", "Doe", "Other Name", "Gender", "jane.doe@example.org", "iloveyou", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144"));

      // Assert
      verify(customerRepository).findByEmail(eq("jane.doe@example.org"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Bank Account Created Successfully"), eq(" Welcome to Our banking Platform...Thank You for Banking with us find your account details below\n Jane Doe Other Name\n42\n42\n 1970-01-01T00:00"));
      verify(customerRepository).save(isA(Customer.class));
      verify(passwordEncoder).encode(isA(CharSequence.class));
      assertEquals("001", actualCreateAccountResult.responseCode);
      AccountInfo accountInfo = actualCreateAccountResult.accountInfo;
      assertEquals("42", accountInfo.getAccountID());
      assertEquals("42", accountInfo.getAccountNumber());
      assertEquals("Account created successfully", actualCreateAccountResult.responseMessage);
      assertEquals("Jane Doe Other Name", accountInfo.getAccountName());
      BigDecimal expectedAccountBalance = new BigDecimal("2.3");
      assertEquals(expectedAccountBalance, accountInfo.getAccountBalance());
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccName(String)}
    */
   @Test
   void testGetAccName() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccName = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccName("42");

      // Assert
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("Jane Doe Other Name", actualAccName);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccName(String)}
    */
   @Test
   void testGetAccName2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getOtherName()).thenReturn("Other Name");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccName = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccName("42");

      // Assert
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer).getOtherName();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("Jane Doe Other Name", actualAccName);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccName(String)}
    */
   @Test
   void testGetAccName3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccName = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccName("42");

      // Assert
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("Account Not found", actualAccName);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccName(String)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testGetAccName4() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@2c3c13d6 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass401, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.getAccName("42");
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccBal(String)}
    */
   @Test
   void testGetAccBal() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccBal = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccBal("42");

      // Assert
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("2.3", actualAccBal);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccBal(String)}
    */
   @Test
   void testGetAccBal2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("2.3"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccBal = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccBal("42");

      // Assert
      verify(customer).getAccountBalance();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("2.3", actualAccBal);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccBal(String)}
    */
   @Test
   void testGetAccBal3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();

      // Act
      String actualAccBal = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).getAccBal("42");

      // Assert
      verify(customerRepository).findByAccountNumber(eq("42"));
      assertEquals("Account Not found", actualAccBal);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#getAccBal(String)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testGetAccBal4() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@1a200dd9 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass315, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.getAccBal("42");
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualCreditAccResult = customerServiceImpl.creditAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Account credited successfully. New balance: 12.3", actualCreditAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenThrow(new IllegalStateException("foo"));
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> customerServiceImpl.creditAcc(new CreditDebit("10", "3", "3")));
      verify(customerRepository).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("2.3"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualCreditAccResult = customerServiceImpl.creditAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customer).getAccountBalance();
      verify(customer, atLeast(1)).setAccountBalance(Mockito.any());
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Account credited successfully. New balance: 12.3", actualCreditAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc4() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualCreditAccResult = customerServiceImpl.creditAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Account not found", actualCreditAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc5() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualCreditAccResult = customerServiceImpl.creditAcc(new CreditDebit(null, "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Amount to credit cannot be null or empty", actualCreditAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc6() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualCreditAccResult = customerServiceImpl.creditAcc(new CreditDebit("", "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Amount to credit cannot be null or empty", actualCreditAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#creditAcc(CreditDebit)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testCreditAcc7() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@42f00dcd testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass234, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.creditAcc(new CreditDebit("10", "3", "3"));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("insufficient Balance for this transaction", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("2.3"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customer).getAccountBalance();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("insufficient Balance for this transaction", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("42"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customer).getAccountBalance();
      verify(customer, atLeast(1)).setAccountBalance(Mockito.any());
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Account debited successfully. New balance: 32", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc4() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Account not found", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc5() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit(null, "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Amount to debit cannot be null or empty", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc6() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualDebitAccResult = customerServiceImpl.debitAcc(new CreditDebit("", "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      assertEquals("Amount to debit cannot be null or empty", actualDebitAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc7() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("42"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenThrow(new IllegalStateException("foo"));
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3")));
      verify(customer).getAccountBalance();
      verify(customer, atLeast(1)).setAccountBalance(Mockito.any());
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#debitAcc(CreditDebit)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testDebitAcc8() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@288b2992 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass254, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.debitAcc(new CreditDebit("10", "3", "3"));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      assertEquals("Insufficient balance for this Transaction", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("2.3"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customer).getAccountBalance();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      assertEquals("Insufficient balance for this Transaction", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("42"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customer, atLeast(1)).getAccountBalance();
      verify(customer, atLeast(1)).setAccountBalance(Mockito.any());
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      verify(customerRepository, atLeast(1)).save(isA(Customer.class));
      assertEquals("Transfer successful. Your  current account balance: N42", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer4() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit("10", "3", "3"));

      // Assert
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      assertEquals("Account not found", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer5() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit(null, "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      assertEquals("Amount to transfer cannot be null or empty", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer6() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act
      String actualTransferResult = customerServiceImpl.transfer(new CreditDebit("", "3", "3"));

      // Assert
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      assertEquals("Amount to transfer cannot be null or empty", actualTransferResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   void testTransfer7() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAccountBalance()).thenReturn(new BigDecimal("42"));
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenThrow(new IllegalStateException("foo"));
      when(customerRepository.findByAccountNumber(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();
      CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder());

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> customerServiceImpl.transfer(new CreditDebit("10", "3", "3")));
      verify(customer, atLeast(1)).getAccountBalance();
      verify(customer, atLeast(1)).setAccountBalance(Mockito.any());
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(customerRepository, atLeast(1)).findByAccountNumber(eq("3"));
      verify(customerRepository).save(isA(Customer.class));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#transfer(CreditDebit)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testTransfer8() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@96a1081 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass487, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.transfer(new CreditDebit("10", "3", "3"));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#deleteAcc(String)}
    */
   @Test
   void testDeleteAcc() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      doNothing().when(customerRepository).deleteById(Mockito.any());
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act
      String actualDeleteAccResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).deleteAcc("42");

      // Assert
      verify(customerRepository).deleteById(eq("42"));
      verify(customerRepository).findById(eq("42"));
      assertEquals("account deleted successfully", actualDeleteAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#deleteAcc(String)}
    */
   @Test
   void testDeleteAcc2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      doThrow(new IllegalStateException("account deleted successfully")).when(customerRepository).deleteById(Mockito.any());
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).deleteAcc("42"));
      verify(customerRepository).deleteById(eq("42"));
      verify(customerRepository).findById(eq("42"));
   }

   /**
    * Method under test: {@link CustomerServiceImpl#deleteAcc(String)}
    */
   @Test
   void testDeleteAcc3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findById(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();

      // Act
      String actualDeleteAccResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).deleteAcc("42");

      // Assert
      verify(customerRepository).findById(eq("42"));
      assertEquals("There is no Account with that ID", actualDeleteAccResult);
   }

   /**
    * Method under test: {@link CustomerServiceImpl#deleteAcc(String)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testDeleteAcc4() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5729ddc4 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass274, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.deleteAcc("42");
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = new Customer();
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenThrow(new IllegalStateException("Update successful"));
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = new EmailService();

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount2() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      Optional<Customer> emptyResult = Optional.empty();
      when(customerRepository.findById(Mockito.any())).thenReturn(emptyResult);
      EmailService emailService = new EmailService();

      // Act and Assert
      assertThrows(IllegalStateException.class, () -> (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144"));
      verify(customerRepository).findById(eq("42"));
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount3() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount4() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("Update successful");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer, atLeast(1)).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount5() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("Update successful");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer, atLeast(1)).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("Update successful"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount6() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Update successful");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer, atLeast(1)).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount7() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Update successful");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer, atLeast(1)).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount8() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("Update successful");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer, atLeast(1)).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount9() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Update successful");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer, atLeast(1)).setFirstName(Mockito.any());
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount10() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("Update successful");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer, atLeast(1)).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount11() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("Update successful");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer, atLeast(1)).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount12() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", null, "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount13() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount14() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", null, "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount15() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount16() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", null, "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount17() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount18() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", null, "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount19() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount20() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", null, "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount21() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "", "6625550144", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount22() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", null, "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount23() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "", "MD", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount24() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", null, "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount25() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getNextOfKinPhone()).thenReturn("6625550144");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "", "Next Of Kin", "6625550144");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer, atLeast(1)).getNextOfKinPhone();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount26() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", null);

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testUpdateAccount27() {
      //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

      // Arrange
      Customer customer = mock(Customer.class);
      when(customer.getAddress()).thenReturn("42 Main St");
      when(customer.getEmail()).thenReturn("jane.doe@example.org");
      when(customer.getFirstName()).thenReturn("Jane");
      when(customer.getLastName()).thenReturn("Doe");
      when(customer.getOtherName()).thenReturn("Other Name");
      when(customer.getPhoneNumber()).thenReturn("6625550144");
      when(customer.getStateOfOrigin()).thenReturn("MD");
      doNothing().when(customer).setAccountBalance(Mockito.any());
      doNothing().when(customer).setAccountNumber(Mockito.any());
      doNothing().when(customer).setAccountStatus(Mockito.any());
      doNothing().when(customer).setAddress(Mockito.any());
      doNothing().when(customer).setCreatedAt(Mockito.any());
      doNothing().when(customer).setEmail(Mockito.any());
      doNothing().when(customer).setFirstName(Mockito.any());
      doNothing().when(customer).setGender(Mockito.any());
      doNothing().when(customer).setId(Mockito.any());
      doNothing().when(customer).setLastName(Mockito.any());
      doNothing().when(customer).setModifiedAt(Mockito.any());
      doNothing().when(customer).setNextOfKin(Mockito.any());
      doNothing().when(customer).setNextOfKinPhone(Mockito.any());
      doNothing().when(customer).setOtherName(Mockito.any());
      doNothing().when(customer).setPassword(Mockito.any());
      doNothing().when(customer).setPhoneNumber(Mockito.any());
      doNothing().when(customer).setStateOfOrigin(Mockito.any());
      customer.setAccountBalance(new BigDecimal("2.3"));
      customer.setAccountNumber("42");
      customer.setAccountStatus("3");
      customer.setAddress("42 Main St");
      customer.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setEmail("jane.doe@example.org");
      customer.setFirstName("Jane");
      customer.setGender("Gender");
      customer.setId("42");
      customer.setLastName("Doe");
      customer.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer.setNextOfKin("Next Of Kin");
      customer.setNextOfKinPhone("6625550144");
      customer.setOtherName("Other Name");
      customer.setPassword("iloveyou");
      customer.setPhoneNumber("6625550144");
      customer.setStateOfOrigin("MD");
      Optional<Customer> ofResult = Optional.of(customer);

      Customer customer2 = new Customer();
      customer2.setAccountBalance(new BigDecimal("2.3"));
      customer2.setAccountNumber("42");
      customer2.setAccountStatus("3");
      customer2.setAddress("42 Main St");
      customer2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setEmail("jane.doe@example.org");
      customer2.setFirstName("Jane");
      customer2.setGender("Gender");
      customer2.setId("42");
      customer2.setLastName("Doe");
      customer2.setModifiedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
      customer2.setNextOfKin("Next Of Kin");
      customer2.setNextOfKinPhone("6625550144");
      customer2.setOtherName("Other Name");
      customer2.setPassword("iloveyou");
      customer2.setPhoneNumber("6625550144");
      customer2.setStateOfOrigin("MD");
      CustomerRepository customerRepository = mock(CustomerRepository.class);
      when(customerRepository.save(Mockito.any())).thenReturn(customer2);
      when(customerRepository.findById(Mockito.any())).thenReturn(ofResult);
      EmailService emailService = mock(EmailService.class);
      doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.any(), Mockito.any());

      // Act
      String actualUpdateAccountResult = (new CustomerServiceImpl(customerRepository, emailService, new BCryptPasswordEncoder())).updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "");

      // Assert
      verify(customer).getAddress();
      verify(customer, atLeast(1)).getEmail();
      verify(customer).getFirstName();
      verify(customer).getLastName();
      verify(customer).getOtherName();
      verify(customer).getPhoneNumber();
      verify(customer).getStateOfOrigin();
      verify(customer).setAccountBalance(isA(BigDecimal.class));
      verify(customer).setAccountNumber(eq("42"));
      verify(customer).setAccountStatus(eq("3"));
      verify(customer).setAddress(eq("42 Main St"));
      verify(customer).setCreatedAt(isA(LocalDateTime.class));
      verify(customer).setEmail(eq("jane.doe@example.org"));
      verify(customer).setFirstName(eq("Jane"));
      verify(customer).setGender(eq("Gender"));
      verify(customer).setId(eq("42"));
      verify(customer).setLastName(eq("Doe"));
      verify(customer).setModifiedAt(isA(LocalDateTime.class));
      verify(customer).setNextOfKin(eq("Next Of Kin"));
      verify(customer).setNextOfKinPhone(eq("6625550144"));
      verify(customer).setOtherName(eq("Other Name"));
      verify(customer).setPassword(eq("iloveyou"));
      verify(customer).setPhoneNumber(eq("6625550144"));
      verify(customer).setStateOfOrigin(eq("MD"));
      verify(emailService).sendEmail(eq("jane.doe@example.org"), eq("Update successful"), eq("You have successfully updated your bank Account!"));
      verify(customerRepository).findById(eq("42"));
      verify(customerRepository).save(isA(Customer.class));
      assertEquals("Update successful\", \"You have successfully updated your bank Account!", actualUpdateAccountResult);
   }

   /**
    * Method under test:
    * {@link CustomerServiceImpl#updateAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   @Disabled("TODO: Complete this test")
   void testUpdateAccount28() {
      // TODO: Diffblue Cover was only able to create a partial test for this method:
      //   Reason: Failed to create Spring context.
      //   Attempt to initialize test context failed with
      //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@7837cdd7 testClass = com.Bank.DaveBank.CustomerService.DiffblueFakeClass507, locations = [], classes = [com.Bank.DaveBank.CustomerService.CustomerServiceImpl, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@71071d47, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@4fe45e71, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@3b9dc2fc, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@1ee620db], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
      //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
      //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
      //   See https://diff.blue/R026 to resolve this issue.

      // Arrange and Act
      customerServiceImpl.updateAccount("42", "Jane", "Doe", "Other Name", "jane.doe@example.org", "42 Main St", "6625550144", "MD", "Next Of Kin", "6625550144");
   }
}
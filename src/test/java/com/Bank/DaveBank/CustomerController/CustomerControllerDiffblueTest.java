package com.Bank.DaveBank.CustomerController;

import com.Bank.DaveBank.CustomerDTO.CreditDebit;
import com.Bank.DaveBank.CustomerDTO.CustomerDto;
import com.Bank.DaveBank.CustomerService.CustomerService;
import com.Bank.DaveBank.CustomerUtils.AccountInfo;
import com.Bank.DaveBank.CustomerUtils.BankResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CustomerControllerDiffblueTest {
   @Autowired
   private CustomerController customerController;

   @MockBean
   private CustomerService customerService;

   /**
    * Method under test: {@link CustomerController#createAccount(CustomerDto)}
    */
   @Test
   void testCreateAccount() throws Exception {
      // Arrange
      BankResponse.BankResponseBuilder builderResult = BankResponse.builder();
      AccountInfo.AccountInfoBuilder builderResult2 = AccountInfo.builder();
      AccountInfo accountInfo = builderResult2.accountBalance(new BigDecimal("2.3")).accountID("3").accountName("Dr Jane Doe").accountNumber("42").build();
      BankResponse buildResult = builderResult.accountInfo(accountInfo).responseCode("Response Code").responseMessage("Response Message").build();
      when(customerService.createAccount(Mockito.any())).thenReturn(buildResult);

      CustomerDto customerDto = new CustomerDto();
      customerDto.setAddress("42 Main St");
      customerDto.setEmail("jane.doe@example.org");
      customerDto.setFirstName("Jane");
      customerDto.setGender("Gender");
      customerDto.setLastName("Doe");
      customerDto.setNextOfKin("Next Of Kin");
      customerDto.setNextOfKinPhone("6625550144");
      customerDto.setOtherName("Other Name");
      customerDto.setPassword("iloveyou");
      customerDto.setPhoneNumber("6625550144");
      customerDto.setStateOfOrigin("MD");
      String content = (new ObjectMapper()).writeValueAsString(customerDto);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/BankOperations/createAccount").contentType(MediaType.APPLICATION_JSON).content(content);

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("application/json")).andExpect(MockMvcResultMatchers.content().string("{\"responseCode\":\"Response Code\",\"responseMessage\":\"Response Message\",\"accountInfo\":{\"accountName\":\"Dr" + " Jane Doe\",\"accountNumber\":\"42\",\"accountID\":\"3\",\"accountBalance\":2.3}}"));
   }

   /**
    * Method under test: {@link CustomerController#creditAcc(CreditDebit)}
    */
   @Test
   void testCreditAcc() throws Exception {
      // Arrange
      when(customerService.creditAcc(Mockito.any())).thenReturn("ada.lovelace@example.org");

      CreditDebit creditDebit = new CreditDebit();
      creditDebit.setAccountToCredit("3");
      creditDebit.setAccountToDebit("3");
      creditDebit.setAmount("10");
      String content = (new ObjectMapper()).writeValueAsString(creditDebit);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/BankOperations/creditAcc").contentType(MediaType.APPLICATION_JSON).content(content);

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("ada.lovelace@example.org"));
   }

   /**
    * Method under test: {@link CustomerController#debitAcc(CreditDebit)}
    */
   @Test
   void testDebitAcc() throws Exception {
      // Arrange
      when(customerService.debitAcc(Mockito.any())).thenReturn("ada.lovelace@example.org");

      CreditDebit creditDebit = new CreditDebit();
      creditDebit.setAccountToCredit("3");
      creditDebit.setAccountToDebit("3");
      creditDebit.setAmount("10");
      String content = (new ObjectMapper()).writeValueAsString(creditDebit);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/BankOperations/debitAcc").contentType(MediaType.APPLICATION_JSON).content(content);

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("ada.lovelace@example.org"));
   }

   /**
    * Method under test: {@link CustomerController#transfer(CreditDebit)}
    */
   @Test
   void testTransfer() throws Exception {
      // Arrange
      when(customerService.transfer(Mockito.any())).thenReturn("Transfer");

      CreditDebit creditDebit = new CreditDebit();
      creditDebit.setAccountToCredit("3");
      creditDebit.setAccountToDebit("3");
      creditDebit.setAmount("10");
      String content = (new ObjectMapper()).writeValueAsString(creditDebit);
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/BankOperations/transfer").contentType(MediaType.APPLICATION_JSON).content(content);

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("Transfer"));
   }

   /**
    * Method under test: {@link CustomerController#getAccName(String)}
    */
   @Test
   void testGetAccName() throws Exception {
      // Arrange
      when(customerService.getAccName(Mockito.any())).thenReturn("Acc Name");
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/BankOperations/{accountName}", "Dr Jane Doe");

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("Acc Name"));
   }

   /**
    * Method under test: {@link CustomerController#getAccBal(String)}
    */
   @Test
   void testGetAccBal() throws Exception {
      // Arrange
      when(customerService.getAccBal(Mockito.any())).thenReturn("Acc Bal");
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/BankOperations/bal/{accountBalance}", "3");

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("Acc Bal"));
   }

   /**
    * Method under test: {@link CustomerController#getAccBal(String)}
    */
   @Test
   void testGetAccBal2() throws Exception {
      // Arrange
      when(customerService.getAccName(Mockito.any())).thenReturn("Acc Name");
      when(customerService.getAccBal(Mockito.any())).thenReturn("Acc Bal");
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/BankOperations/bal/{accountBalance}", "", "Uri Variables");

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("Acc Name"));
   }

   /**
    * Method under test: {@link CustomerController#deleteAcc(String)}
    */
   @Test
   void testDeleteAcc() throws Exception {
      // Arrange
      when(customerService.deleteAcc(Mockito.any())).thenReturn("ada.lovelace@example.org");
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/BankOperations/deleteAccount/{id}", "42");

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("ada.lovelace@example.org"));
   }

   /**
    * Method under test:
    * {@link CustomerController#editAccount(String, String, String, String, String, String, String, String, String, String)}
    */
   @Test
   void testEditAccount() throws Exception {
      // Arrange
      when(customerService.updateAccount(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("2020-03-01");
      MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/BankOperations/editAccount/{id}", "42");

      // Act and Assert
      MockMvcBuilders.standaloneSetup(customerController).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1")).andExpect(MockMvcResultMatchers.content().string("2020-03-01"));
   }
}
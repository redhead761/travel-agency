package com.epam.finaltask.integration;

import com.epam.finaltask.dto.HotStatusRequest;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherStatusRequest;
import com.epam.finaltask.mapper.UserMapper;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class VoucherControllerFullIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    void testFindAllVouchers() throws Exception {
        addVoucher("Summer Vacation");
        mockMvc.perform(get("/vouchers?page=1&size=8&hotelType=FIVE_STARS&tourType=ECO&transferType=PLANE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results[0].title", is("Summer Vacation")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER", "ROLE_ADMIN"})
    void testGetVoucherById() throws Exception {
        VoucherDTO voucher = addVoucher("Spring Vacation");

        mockMvc.perform(get("/vouchers/" + voucher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].title", is("Spring Vacation")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testCreateVoucher() throws Exception {
        VoucherDTO voucherDTO = VoucherDTO.builder()
                .title("Winter Getaway")
                .description("A cozy winter getaway")
                .price(1200.0)
                .tourType(TourType.HEALTH.name())
                .transferType(TransferType.JEEPS.name())
                .hotelType(HotelType.FIVE_STARS.name())
                .arrivalDate(LocalDate.now().plusDays(15))
                .evictionDate(LocalDate.now().plusDays(25))
                .hot("true")
                .build();

        mockMvc.perform(post("/vouchers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voucherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].title", is("Winter Getaway")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testUpdateVoucher() throws Exception {
        VoucherDTO voucher = addVoucher("Summer Vacation");

        VoucherDTO updatedVoucherDTO = VoucherDTO.builder()
                .title("Updated Vacation")
                .description("Updated relaxing vacation")
                .price(1500.0)
                .tourType(TourType.ADVENTURE.name())
                .transferType(TransferType.BUS.name())
                .hotelType(HotelType.TWO_STARS.name())
                .status(VoucherStatus.PAID.name())
                .arrivalDate(LocalDate.now().plusDays(15))
                .evictionDate(LocalDate.now().plusDays(30))
                .hot("false")
                .build();

        mockMvc.perform(put("/vouchers/" + voucher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVoucherDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)))
                .andExpect(jsonPath("$.results[0].title", is("Updated Vacation")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testDeleteVoucher() throws Exception {
        VoucherDTO voucher = addVoucher("Summer Vacation");

        mockMvc.perform(delete("/vouchers/" + voucher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded", is(true)));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    void testCreateOrder() throws Exception {
        VoucherDTO voucher = addVoucher("Winter Vacation");
        UserDTO user = addUser();

        mockMvc.perform(patch("/vouchers/" + voucher.getId() + "/order")
                        .param("userId", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded").value(true))
                .andExpect(jsonPath("$.results[0].title", is("Winter Vacation")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testChangeHotStatus() throws Exception {
        VoucherDTO voucher = addVoucher("Winter Vacation");
        HotStatusRequest hotStatusRequest = new HotStatusRequest(true, 0);

        mockMvc.perform(patch("/vouchers/" + voucher.getId() + "/hot")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hotStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded").value(true))
                .andExpect(jsonPath("$.results[0].title", is("Winter Vacation")));
    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    void testChangeVoucherStatus() throws Exception {
        VoucherDTO voucher = addVoucher("Winter Vacation");
        VoucherStatusRequest voucherStatusRequest = new VoucherStatusRequest(VoucherStatus.PAID, 0);

        mockMvc.perform(patch("/vouchers/" + voucher.getId() + "/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(voucherStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.succeeded").value(true))
                .andExpect(jsonPath("$.results[0].title", is("Winter Vacation")));
    }

    private VoucherDTO addVoucher(String title) {
        Voucher voucher = Voucher.builder()
                .title(title)
                .description("A relaxing summer vacation")
                .price(1000.0)
                .tourType(TourType.ECO)
                .transferType(TransferType.PLANE)
                .hotelType(HotelType.FIVE_STARS)
                .status(VoucherStatus.REGISTERED)
                .arrivalDate(LocalDate.now().plusDays(10))
                .evictionDate(LocalDate.now().plusDays(20))
                .hot(true)
                .build();
        return voucherMapper.toVoucherDTO(voucherRepository.save(voucher));
    }

    private UserDTO addUser() {

        User user = new User();
        user.setUsername("testUser");
        user.setBalance(2000.0);
        user.setPassword("password");
        user.setPhoneNumber("+380501234561");
        user.setAccountStatus(true);
        user.setRole(Role.USER);

        return userMapper.toUserDTO(userRepository.save(user));
    }
}

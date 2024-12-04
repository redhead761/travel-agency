package com.epam.finaltask.mapper;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.model.Voucher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface VoucherMapper {
    VoucherDTO toVoucherDTO(Voucher voucher);

    Voucher toVoucher(VoucherDTO voucherDTO);

    List<VoucherDTO> toDTOList(List<Voucher> vouchers);
}

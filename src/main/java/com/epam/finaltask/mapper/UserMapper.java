package com.epam.finaltask.mapper;

import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = VoucherMapper.class)
public interface UserMapper {
    @Mapping(target = "user.password", ignore = true)
    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}

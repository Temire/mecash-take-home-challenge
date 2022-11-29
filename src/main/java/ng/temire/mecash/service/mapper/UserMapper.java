package ng.temire.mecash.service.mapper;

import ng.temire.mecash.data.dto.UserDTO;
import ng.temire.mecash.data.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface UserMapper extends EntityMapper <UserDTO, User>{

}

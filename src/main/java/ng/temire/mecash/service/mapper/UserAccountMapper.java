package ng.temire.mecash.service.mapper;

import ng.temire.mecash.data.dto.UserAccountDTO;
import ng.temire.mecash.data.entity.UserAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserAccountMapper extends EntityMapper<UserAccountDTO, UserAccount> {
}

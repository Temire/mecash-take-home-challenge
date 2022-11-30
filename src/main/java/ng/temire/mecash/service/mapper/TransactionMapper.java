package ng.temire.mecash.service.mapper;

import ng.temire.mecash.data.dto.TransactionDTO;
import ng.temire.mecash.data.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
}

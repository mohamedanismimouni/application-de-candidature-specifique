package com.talan.polaris.mapper;
import com.talan.polaris.dto.RequestTypeDTO;
import com.talan.polaris.entities.RequestTypeEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * Mapping User Entity
 *
 * @author Imen mechergui
 * @since 1.0.0
 */
public class RequestTypeMapper {

     RequestTypeMapper() {
    }

    /**
     *
     * @param requestTypeDTO
     * @param modelMapper
     * @return RequestTypeEntity
     */
    public static RequestTypeEntity convertRequestTypeDTOToEntity(RequestTypeDTO requestTypeDTO, ModelMapper modelMapper) {
        return modelMapper.map(requestTypeDTO, RequestTypeEntity.class);
    }

    /**
     *
     * @param requestTypeEntity
     * @param modelMapper
     * @return RequestTypeDTO
     */
    public static RequestTypeDTO convertRequestTypeEntityToDTO(RequestTypeEntity requestTypeEntity, ModelMapper modelMapper) {
        return modelMapper.map(requestTypeEntity, RequestTypeDTO.class);
    }



    /**
     *convert request Type Entity List To DTO
     * @param requestTypeEntityList
     * @param modelMapper
     * @return Collection<RequestTypeDTO>
     */
    public static Collection<RequestTypeDTO> convertRequestTypeEntityListToDTO(Collection<RequestTypeEntity> requestTypeEntityList, ModelMapper modelMapper) {
        Type requestTypeListType = new TypeToken<List<RequestTypeDTO>>() {
        }.getType();
        return modelMapper.map(requestTypeEntityList, requestTypeListType);

    }
}

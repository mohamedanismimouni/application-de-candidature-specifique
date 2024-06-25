package com.talan.polaris.mapper;
import com.talan.polaris.dto.DocumentRequestDTO;
import com.talan.polaris.entities.DocumentRequestEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class DocumentRequestMapper {

    public DocumentRequestMapper() {

    }

    /**
     *
     * @param documentRequestDTO
     * @param modelMapper
     * @return RequestEntity
     */
    public static DocumentRequestEntity convertRequestDTOToEntity(DocumentRequestDTO documentRequestDTO, ModelMapper modelMapper) {
        return modelMapper.map(documentRequestDTO, DocumentRequestEntity.class);
    }

    /**
     *
     * @param documentRequestEntity
     * @param modelMapper
     * @return RequestDTO
     */
    public static DocumentRequestDTO convertRequestEntityToDTO(DocumentRequestEntity documentRequestEntity, ModelMapper modelMapper) {
        return modelMapper.map(documentRequestEntity, DocumentRequestDTO.class);
    }


    /**
     *convert Document request  Entity List To DTO
     * @param documentRequestEntityList
     * @param modelMapper
     * @return Collection<RequestTypeDTO>
     */
    public static Collection<DocumentRequestDTO> convertDocumentsRequestListToDTO(Collection<DocumentRequestEntity> documentRequestEntityList, ModelMapper modelMapper) {
        Type documentsRequestListType = new TypeToken<List<DocumentRequestDTO>>() {
        }.getType();
        return modelMapper.map(documentRequestEntityList, documentsRequestListType);

    }

    public static Collection<DocumentRequestEntity> convertDocumentsRequestListToEntity(Collection<DocumentRequestDTO> documentRequestEntityList, ModelMapper modelMapper) {
        Type documentsRequestListType = new TypeToken<List<DocumentRequestEntity>>() {
        }.getType();
        return modelMapper.map(documentRequestEntityList, documentsRequestListType);

    }
}

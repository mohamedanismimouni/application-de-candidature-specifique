package com.talan.polaris.mapper;

import com.talan.polaris.dto.EventDTO;
import com.talan.polaris.entities.EventEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class EventMapper {

	EventMapper() {
	}

	public static EventEntity convertEventDTOToEntity(EventDTO eventDTO, ModelMapper modelMapper) {
		return modelMapper.map(eventDTO, EventEntity.class);
	}

	public static EventDTO convertEventEntityToDTO(EventEntity eventEntity, ModelMapper modelMapper) {
		return modelMapper.map(eventEntity, EventDTO.class);
	}

	public static Collection<EventDTO> convertEventListToDTO(Collection<EventEntity> eventEntityList,
			ModelMapper modelMapper) {
		Type eventListType = new TypeToken<List<EventDTO>>() {
		}.getType();
		return modelMapper.map(eventEntityList, eventListType);

	}

}

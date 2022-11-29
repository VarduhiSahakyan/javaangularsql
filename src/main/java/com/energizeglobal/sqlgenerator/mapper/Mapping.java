package com.energizeglobal.sqlgenerator.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapping {

    private final ModelMapper modelMapper;

    public Mapping(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <E, D> D convertToDto(E entity, Class<D> dto) {
        return modelMapper.map(entity, dto);
    }

    public <E, D> E convertToEntity(D dto, Class<E> entity) {
        return modelMapper.map(dto, entity);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}

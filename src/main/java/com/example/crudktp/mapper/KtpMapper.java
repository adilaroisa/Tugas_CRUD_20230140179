package com.example.crudktp.mapper;

import com.example.crudktp.model.dto.KtpDto;
import com.example.crudktp.model.entity.Ktp;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface KtpMapper {

    Ktp toEntity(KtpDto dto);

    KtpDto toDto(Ktp entity);
}
package com.example.demo.mapper;

import com.example.demo.dto.dialog.DialogDto;
import com.example.demo.dto.dialog.DialogRequestDto;
import com.example.demo.entity.Dialog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DialogMapper {
    DialogMapper INSTANCE = Mappers.getMapper(DialogMapper.class);

    DialogDto toDto(Dialog dialog);

    List<DialogDto> toDto(List<Dialog> dialog);

    Dialog fromDto(DialogRequestDto dialogDtoRequest);

}

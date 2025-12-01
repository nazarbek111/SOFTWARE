package com.example.swmidkaa.mapper;

import com.example.swmidkaa.dto.TeacherDto;
import com.example.swmidkaa.entity.Teacher;

public class TeacherMapper {

    public static TeacherDto toDto(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        return new TeacherDto(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail()
        );
    }

    public static Teacher toEntity(TeacherDto dto) {
        if (dto == null) {
            return null;
        }
        return Teacher.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
    }
}

package com.example.swmidkaa.mapper;

import com.example.swmidkaa.dto.StudentDto;
import com.example.swmidkaa.entity.Student;

public class StudentMapper {

    public static StudentDto toDto(Student student) {
        if (student == null) {
            return null;
        }
        return new StudentDto(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAge()
        );
    }

    public static Student toEntity(StudentDto dto) {
        if (dto == null) {
            return null;
        }
        return Student.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .age(dto.getAge())
                .build();
    }
}

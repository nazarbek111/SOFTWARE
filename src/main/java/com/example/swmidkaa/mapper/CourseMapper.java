package com.example.swmidkaa.mapper;

import com.example.swmidkaa.dto.CourseDto;
import com.example.swmidkaa.entity.Course;

public class CourseMapper {

    public static CourseDto toDto(Course course) {
        if (course == null) {
            return null;
        }
        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCredits()
        );
    }

    public static Course toEntity(CourseDto dto) {
        if (dto == null) {
            return null;
        }
        return Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .credits(dto.getCredits())
                .build();
    }
}

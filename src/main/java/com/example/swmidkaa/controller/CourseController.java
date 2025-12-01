package com.example.swmidkaa.controller;

import com.example.swmidkaa.dto.CourseDto;
import com.example.swmidkaa.entity.Course;
import com.example.swmidkaa.mapper.CourseMapper;
import com.example.swmidkaa.repository.CourseRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAll() {
        List<CourseDto> courses = courseRepository.findAll().stream()
                .map(CourseMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getById(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        return ResponseEntity.ok(CourseMapper.toDto(course));
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@RequestBody CourseDto dto) {
        Course course = CourseMapper.toEntity(dto);
        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(CourseMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> update(@PathVariable Long id, @RequestBody CourseDto dto) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setCredits(dto.getCredits());
        Course saved = courseRepository.save(existing);
        return ResponseEntity.ok(CourseMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        courseRepository.delete(course);
        return ResponseEntity.noContent().build();
    }
}

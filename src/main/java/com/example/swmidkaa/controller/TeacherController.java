package com.example.swmidkaa.controller;

import com.example.swmidkaa.dto.TeacherDto;
import com.example.swmidkaa.entity.Teacher;
import com.example.swmidkaa.mapper.TeacherMapper;
import com.example.swmidkaa.repository.TeacherRepository;
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
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll() {
        List<TeacherDto> teachers = teacherRepository.findAll().stream()
                .map(TeacherMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        return ResponseEntity.ok(TeacherMapper.toDto(teacher));
    }

    @PostMapping
    public ResponseEntity<TeacherDto> create(@RequestBody TeacherDto dto) {
        Teacher teacher = TeacherMapper.toEntity(dto);
        Teacher saved = teacherRepository.save(teacher);
        return ResponseEntity.ok(TeacherMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable Long id, @RequestBody TeacherDto dto) {
        Teacher existing = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        Teacher saved = teacherRepository.save(existing);
        return ResponseEntity.ok(TeacherMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        teacherRepository.delete(teacher);
        return ResponseEntity.noContent().build();
    }
}

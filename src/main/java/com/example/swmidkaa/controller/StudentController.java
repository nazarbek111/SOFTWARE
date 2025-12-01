package com.example.swmidkaa.controller;

import com.example.swmidkaa.dto.StudentDto;
import com.example.swmidkaa.entity.Student;
import com.example.swmidkaa.mapper.StudentMapper;
import com.example.swmidkaa.repository.StudentRepository;
import com.example.swmidkaa.service.StudentService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll() {
        List<StudentDto> students = studentRepository.findAll().stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentDto>> getByMinAge(@RequestParam(required = false) Integer minAge) {
        List<StudentDto> students = studentService.findByMinAge(minAge).stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        return ResponseEntity.ok(StudentMapper.toDto(student));
    }

    @PostMapping
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto dto) {
        Student student = StudentMapper.toEntity(dto);
        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(StudentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setAge(dto.getAge());
        Student saved = studentRepository.save(existing);
        return ResponseEntity.ok(StudentMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        studentRepository.delete(student);
        return ResponseEntity.noContent().build();
    }
}

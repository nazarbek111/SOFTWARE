package com.example.swmidkaa.service;

import com.example.swmidkaa.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Student> findByMinAge(Integer minAge) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        Predicate predicate = builder.conjunction();
        if (minAge != null) {
            predicate = builder.greaterThanOrEqualTo(root.get("age"), minAge);
        }

        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}

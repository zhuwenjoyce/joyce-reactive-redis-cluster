package com.joyce.redis.repo;

//import com.baeldung.spring.data.redis.model.Student;
import com.joyce.redis.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {}

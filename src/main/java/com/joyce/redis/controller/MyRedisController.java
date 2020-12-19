package com.joyce.redis.controller;

import com.joyce.redis.model.Student;
import com.joyce.redis.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * @author: Joyce Zhu
 * @date: 2020/12/16
 */
@RestController
public class MyRedisController {

    @Autowired
    StudentRepository studentRepository;

    private static final Random random = new Random();

    @RequestMapping("/my-redis/save-and-find")
    public Optional<Student> t() throws InterruptedException {
        String id = UUID.randomUUID().toString();
        studentRepository.save(
                Student.builder()
                        .id(id)
                        .name("username")
                        .grade(random.nextInt(10))
                        .build()
        );
        Optional<Student> student = studentRepository.findById(id);
        studentRepository.wait(24 * 60 * 60 * 1000L); // expiration time is one day
        studentRepository.deleteById(id);
        return student;
    }

}

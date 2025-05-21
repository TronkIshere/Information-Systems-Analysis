package com.tronk.analysis.service.impl;

import com.tronk.analysis.configuration.UserPrincipal;
import com.tronk.analysis.entity.Lecturer;
import com.tronk.analysis.entity.Student;
import com.tronk.analysis.exception.ApplicationException;
import com.tronk.analysis.exception.ErrorCode;
import com.tronk.analysis.repository.LecturerRepository;
import com.tronk.analysis.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailsServiceCustomizer implements UserDetailsService {
    LecturerRepository lecturerRepository;
    StudentRepository studentRepository;

    @Override
    @Transactional
    public UserPrincipal loadUserByUsername(String loginName) throws UsernameNotFoundException {
        Optional<Lecturer> lecturer = lecturerRepository.findByLoginName(loginName);
        if (lecturer.isPresent()) {
            return UserPrincipal.create(lecturer.get());
        }

        Student student = studentRepository.findByLoginName(loginName)
                .orElseThrow(() -> new ApplicationException(ErrorCode.STUDENT_NOT_FOUND));

        return UserPrincipal.create(student);
    }
}

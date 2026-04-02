package com.hnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnu.entity.Student;
import com.hnu.service.StudentService;
import com.hnu.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

}





package com.hnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.config.VerificationCode;
import com.hnu.dto.Result;
import java.awt.image.BufferedImage;
import com.hnu.entity.Student;
import com.hnu.service.StudentService;
import com.hnu.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/student")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    //读者登录
    @PostMapping("/login")
    public Result login(@RequestBody Student student, HttpSession session){
        //密码加密
        String pass=student.getPassword();
        String password= SecurityUtils.md5Hex(pass);
        //根据用户查询用户信息
        LambdaQueryWrapper<Student> qw=new LambdaQueryWrapper<>();
        qw.eq(Student::getUsername,student.getUsername());
        Student s=studentService.getOne(qw);
        if(s==null){
            return Result.error(Constants.LOGIN_UNKNOW_USERNAME);
        }
        if(s.getStatus()==0){
            return Result.error(Constants.LOGIN_ACCOUNT_DISABLED);
        }
        if(!SecurityUtils.md5Hex(student.getPassword()).equals(s.getPassword())){
            return Result.error(Constants.LOGIN_ERROR_PASSWORD);
        }
        session.setAttribute("student",s);
        return Result.ok(Constants.LOGIN_OK).data(s);
    }

    //    注册
    @PostMapping("/reggie")
    public Result reggie(@RequestBody Student student) {
        LambdaQueryWrapper<Student> qw=new LambdaQueryWrapper<>();
        qw.eq(Student::getUsername,student.getUsername());
        Student s = studentService.getOne(qw);
        if(s == null) {
            student.setPassword(SecurityUtils.md5Hex(student.getPassword()));
            student.setStatus(1);
            student.setNumber(2);
            student.setReputation(100);
            student.setIslogin(0);
            //        调用服务层做添加操作
            boolean flang = studentService.save(student);
            if (flang && qw == null) {
                return Result.ok(Constants.REGISTER_OK);
            }
        }
        return Result.error(Constants.REGISTER_ERRROR);
    }

    //禁用    修改管理员状态
    @PostMapping("/status/{status}/{id}")
    public Result status(@PathVariable("id")Long id,
                         @PathVariable("status")Integer status){
        LambdaUpdateWrapper<Student> uw=new LambdaUpdateWrapper<>();
        uw.set(Student::getStatus,status).eq(Student::getId,id);
        boolean flag=studentService.update(uw);
        if (flag) {
            return Result.ok(Constants.OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //    修改用户信息
    @PostMapping("/update")
    public Result update(@RequestBody Student student) {
        boolean flag = studentService.updateById(student);
        if (flag) {
            return Result.ok(Constants.OK).data(flag);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //    上传头像
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile myfile){
        //上传路径
        String base = "D:\\books";
        //获取文件文件名
        String filename=myfile.getOriginalFilename();
//获取文件文件后缀名
        String suffix=filename.substring(filename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复造成文件覆盖
        filename= UUID.randomUUID().toString()+suffix;
        //文件上传
        try {
            myfile.transferTo(new File(base,filename));
            return Result.ok(Constants.UPLOAD_SUCCESS).data(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  Result.error(Constants.UPLOAD_ERROR);
    }

    //根据姓名模糊查询
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findall(@RequestBody Student student,
                          @PathVariable("currentPage")int currentPage,
                          @PathVariable("limit")int limit){
        IPage<Student> page=new Page<>(currentPage,limit);
        List<Student> list =null;
        if(student.getName()==null){
            list = studentService.list(null);
        }else {
            LambdaQueryWrapper<Student> qw = new LambdaQueryWrapper<>();
            qw.like(Student::getName, student.getName());
            list = studentService.page(page,qw).getRecords();
        }
        if (list.size()<=0){
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(list).totalNum(page.getTotal());
    }

    @PostMapping("/query/{id}")
    public Result queryById(@PathVariable("id")Long id){
        Student student = studentService.getById(id);
        if(student==null){
            return Result.ok(Constants.NO_FIND);
        }
        return Result.error(Constants.FIND_SUCCESS).data(student);
    }

    @PostMapping("/loginout")
    public Result loginout(SessionStatus sessionStatus) {
        sessionStatus.isComplete();
        return Result.ok(Constants.LOGINOUT_OK);
    }

    @PostMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }
}

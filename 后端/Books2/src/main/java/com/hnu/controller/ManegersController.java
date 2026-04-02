package com.hnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Books;
import com.hnu.entity.Manegers;
import com.hnu.service.ManegersService;
import com.hnu.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/manegers")
@RestController
public class ManegersController {

    @Autowired
    private ManegersService manegersService;

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Manegers manegers, HttpSession session) {
        //密码加密
        String pass = manegers.getPassword();
        String password = SecurityUtils.md5Hex(pass);
        //根据用户查询用户信息
        LambdaQueryWrapper<Manegers> qw = new LambdaQueryWrapper<>();
        qw.eq(Manegers::getUsername, manegers.getUsername());
        Manegers m = manegersService.getOne(qw);
        if (m == null) {
            return Result.error(Constants.LOGIN_UNKNOW_USERNAME);
        }
        if (m.getStatus() == 0) {
            return Result.error(Constants.LOGIN_ACCOUNT_DISABLED);
        }
        if (!SecurityUtils.md5Hex(manegers.getPassword()).equals(m.getPassword())) {
            return Result.error(Constants.LOGIN_ERROR_PASSWORD);
        }
        session.setAttribute("manegers", m);
        return Result.ok(Constants.LOGIN_OK).data(m);
    }

    //退出
    @PostMapping("/loginout")
    public Result loginout(SessionStatus sessionStatus) {
        sessionStatus.isComplete();
        return Result.ok(Constants.LOGINOUT_OK);
    }

    //添加管理员
    @PostMapping("/reggie")
    public Result reggie(@RequestBody Manegers manegers) {
        //密码加密
//        String pass = manegers.getPassword();
//        String password = SecurityUtils.md5Hex(pass);
//        设置默认值
//        manegers.setPassword("123456");
        manegers.setStatus(1);
        //        调用服务层做添加操作
        boolean flang = manegersService.save(manegers);
        if (flang) {
            return Result.ok(Constants.ADD_SUCCESS);
        }
        return Result.error(Constants.ADD_ERROR);
    }

    @PostMapping("/query/{id}")
    public Result queryById(@PathVariable("id")Long id){
        Manegers manegers = manegersService.getById(id);
        if(manegers==null){
            return Result.ok(Constants.NO_FIND);
        }
        return Result.error(Constants.FIND_SUCCESS).data(manegers.getImage());
    }

    //禁用    修改管理员状态
    @PostMapping("/status/{status}/{id}")
    public Result status(@PathVariable("id")Long id,
                         @PathVariable("status")Integer status){
        LambdaUpdateWrapper<Manegers> uw=new LambdaUpdateWrapper<>();
        uw.set(Manegers::getStatus,status).eq(Manegers::getId,id);
        boolean flag=manegersService.update(uw);
        if (flag) {
            return Result.ok(Constants.OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //修改    根据id修改管理员信息
    @PostMapping("/update")
    public Result update(@RequestBody Manegers manegers) {
        boolean flag = manegersService.updateById(manegers);
        if (flag) {
            return Result.ok(Constants.UPDATE_OK).data(flag);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //修改头像    根据id修改管理员信息
    @PostMapping("/updateimage")
    public Result updateimage(@RequestBody Manegers manegers) {
        Manegers ms = manegersService.getById(manegers.getId());
        ms.setImage(manegers.getImage());
        boolean flag = manegersService.updateById(ms);
        if (flag) {
            return Result.ok(Constants.UPDATE_OK).data(ms.getImage());
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

    //分页查询 根据姓名模糊查询
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findall(@RequestBody Manegers manegers,
                          @PathVariable("currentPage")int currentPage,
                          @PathVariable("limit")int limit){
        IPage<Manegers> page=new Page<>(currentPage,limit);
        List<Manegers> list = null;
        if(manegers.getName()==null){
            list = manegersService.list(null);
        }else {
            LambdaQueryWrapper<Manegers> qw = new LambdaQueryWrapper<>();
            qw.like(Manegers::getName, manegers.getName());
            list = manegersService.page(page,qw).getRecords();
        }
        if (list.size()<=0){
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(list).totalNum(page.getTotal());
    }
}

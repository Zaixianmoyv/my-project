package com.hnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Books;
import com.hnu.entity.Manegers;
import com.hnu.entity.Message;
import com.hnu.service.ManegersService;
import com.hnu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/message")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ManegersService manegersService;

    //    查
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findAll(@PathVariable("currentPage")int currentPage,
                          @PathVariable("limit")int limit){
        //1 创建IPage分页对象,设置分页参数
        IPage<Message> page=new Page<>(currentPage,limit);
        LambdaQueryWrapper<Message> qw=new LambdaQueryWrapper<>();
        qw.orderByDesc(Message::getCreatetime);
        //2 执行分页查询
        List<Message> list = messageService.page(page, qw).getRecords();
        List<Message> list1=list.stream().map(item ->{
            Long mid = item.getManegerid();
            Manegers m = manegersService.getById(mid);
            item.setMname(m.getName());

            return item;
        }).collect(Collectors.toList());
        page.setRecords(list1);
        list1=page.getRecords();
        if(list1.size()<=0){
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(list).totalNum(page.getTotal());
    }

    //    增
    @PostMapping("/addMessage")
    public Result addMessage(@RequestBody Message message){
        System.out.println("#####"+message);
        message.setCreatetime(new Date());
        Manegers m = manegersService.getById(message.getManegerid());
        message.setMname(m.getUsername());
        boolean flang = messageService.save(message);
        if(flang){
            return Result.ok(Constants.ADD_SUCCESS).data(message);
        }
        return Result.error(Constants.ADD_ERROR);
    }
    ////    删
    @PostMapping("/deleteMessage/{id}")
    public Result deleteMessage(@PathVariable("id")String id){
        boolean flang = messageService.removeById(id);
        if (flang) {
            return Result.ok(Constants.DELETE_OK);
        }
        return Result.error(Constants.DELETE_ERROR);
    }
}

package com.hnu.controller;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Books;
import com.hnu.entity.Borrow;
import com.hnu.entity.Message;
import com.hnu.entity.Student;
import com.hnu.mapper.BorrowMapper;
import com.hnu.service.BooksService;
import com.hnu.service.BorrowService;
import com.hnu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/borrow")
@RestController
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BooksService booksService;

    //商品列表
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findall(@RequestBody Borrow borrow,
                          @PathVariable("currentPage") Integer currentPage,
                          @PathVariable("limit") Integer limit) {
        //1 创建IPage分页对象,设置分页参数
        IPage<Borrow> page = new Page<>(currentPage, limit);
        LambdaQueryWrapper<Borrow> qw = new LambdaQueryWrapper<>();
        qw.orderByDesc(Borrow::getCreatetime);
        //qw.like(Product::getProductName,product.getProductName());
        if (borrow.getStudentid() != null) {
            qw.like(Borrow::getStudentid, borrow.getStudentid());
        }
        //2 执行分页查询
        List<Borrow> list = borrowService.page(page, qw).getRecords();
        List<Borrow> lists = list.stream().map(item -> {
            Long cid = item.getStudentid();
            Student s = studentService.getById(cid);
            item.setStudentName(s.getName());

            Long bid = item.getBookid();
            Books b = booksService.getById(bid);
            item.setBookName(b.getName());
            return item;
        }).collect(Collectors.toList());
        page.setRecords(lists);
        lists = page.getRecords();
        if (lists.size() <= 0) {
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(lists).totalNum(page.getTotal());
    }

    @PostMapping("/updateStatus/{mback}/{id}")
    public Result updateStatus(@PathVariable("id") Long id,
                               @PathVariable("mback") Integer mback) {
        Borrow borrow = borrowService.getById(id);
        if (mback == 1) {
            borrow.setMback(1);
            Student s = studentService.getById(borrow.getStudentid());
            Books b = booksService.getById(borrow.getBookid());
            s.setNumber(s.getNumber() + 1);
            b.setNumber(b.getNumber() + 1);
            Boolean f1 = studentService.updateById(s);
            Boolean f2 = booksService.updateById(b);
            if (borrow.getSback() == 1) borrow.setStatus(1);
        } else if (mback == 0) {
            borrow.setMback(0);
            borrow.setStatus(0);
        }
        boolean flang= borrowService.updateById(borrow);
        if (flang) {
            return Result.ok(Constants.UPDATE_OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    @PostMapping("/updatess/{sback}/{id}")
    public Result updatess(@PathVariable("id") Long id,
                           @PathVariable("sback") Integer sback) {
        Borrow borrow = borrowService.getById(id);
        if (sback == 1) {
            borrow.setSback(1);
            borrow.setBacktime(new Date());
            if (borrow.getMback() == 1) borrow.setStatus(1);
        } else if (sback == 0) {
            borrow.setSback(0);
            borrow.setStatus(0);
        }
        boolean flang= borrowService.updateById(borrow);
        if (flang) {
            return Result.ok(Constants.UPDATE_OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //    学生对书的操作 借阅，当学生的数量=0 return error| 否则 书量-1
    @PostMapping("/borrows/{bookid}/{studentid}")
    public Result borrowBook(@PathVariable("studentid")Long studentid,
                             @PathVariable("bookid")Long bookid){
        Borrow borrow = new Borrow();
        Books books = booksService.getById(bookid);
        Student student = studentService.getById(studentid);
        if (books.getNumber()!=0 && student.getNumber()!=0){
            books.setNumber(books.getNumber()-1);
            student.setNumber(student.getNumber()-1);
            boolean flang1 = studentService.updateById(student);
            boolean flang2 = booksService.updateById(books);
            if (flang1 && flang2){
                borrow.setBookid(bookid);
                borrow.setStudentid(studentid);
                borrow.setCreatetime(new Date());
                //        加一个月
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.YEAR,1);
                date = cal.getTime();
                borrow.setEndtime(date);
                boolean flang3 = borrowService.save(borrow);
                if (flang1 && flang2 && flang3){
                    return Result.ok(Constants.OK);
                }
            }
        }
        return Result.error(Constants.UNKNOWN_REASON);
    }

    //    学生个人信息 加过书名的
    @PostMapping("/InfoStudent/{studentid}")
    public Result reggie(@RequestBody Borrow borrow,
                         @PathVariable("studentid")Long studentid) {
        LambdaQueryWrapper<Borrow> qw=new LambdaQueryWrapper<>();
        qw.like(Borrow::getStudentid,borrow.getStudentid());
        List<Borrow> list = borrowService.list(qw);
        List<Borrow> list1 = list.stream().map(item ->{
            Long bid = item.getBookid();
            Books b=booksService.getById(bid);
            item.setBookName(b.getName());
            return item;
        }).collect(Collectors.toList());
        return Result.ok(Constants.FIND_SUCCESS).data(list1);
    }

    @PostMapping("/findallss/{status}/{studentid}")
    public Result findallss(@PathVariable("status")Integer status,
                            @PathVariable("studentid")Long studentid) {
        LambdaQueryWrapper<Borrow> qw=new LambdaQueryWrapper<>();
        qw.eq(Borrow::getStudentid,studentid).eq(Borrow::getStatus,status);
        List<Borrow> list = borrowService.list(qw);
        List<Borrow> lists = list.stream().map(item -> {
            Long bid = item.getBookid();
            Books b = booksService.getById(bid);
            item.setAuthor(b.getAuthor());
            item.setBookName(b.getName());
            return item;
        }).collect(Collectors.toList());
        return Result.ok(Constants.FIND_SUCCESS).data(lists);
    }
}
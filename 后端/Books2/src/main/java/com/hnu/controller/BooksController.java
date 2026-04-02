package com.hnu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Books;
import com.hnu.entity.Booksort;
import com.hnu.entity.Borrow;
import com.hnu.entity.Student;
import com.hnu.mapper.BooksMapper;
import com.hnu.mapper.BorrowMapper;
import com.hnu.service.BooksService;
import com.hnu.service.BooksortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/books")
@RestController
public class BooksController {

    @Autowired
    private BooksService booksService;
    @Autowired
    private BooksortService booksortService;

    //根据书名模糊查询
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findall(@RequestBody Books books,
                          @PathVariable("currentPage")int currentPage,
                          @PathVariable("limit")int limit){
        IPage<Books> page=new Page<>(currentPage,limit);
        LambdaQueryWrapper<Books> qw=new LambdaQueryWrapper<>();
        if(books.getName()!=null){
            qw.like(Books::getName, books.getName());
        }
        //2 执行分页查询
        List<Books> list = booksService.page(page,qw).getRecords();
        List<Books> lists=list.stream().map(item ->{
            Long cid=item.getSortid();
            Booksort s= booksortService.getById(cid);
            item.setSortname(s.getName());
            return  item;
        }).collect(Collectors.toList());
        page.setRecords(lists);
        lists=page.getRecords();
        if(lists.size()<=0){
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(lists).totalNum(page.getTotal());
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

    @PostMapping("/findallss/{news}")
    public Result findallss(@PathVariable("news")Integer news) {
        LambdaQueryWrapper<Books> qw = new LambdaQueryWrapper<>();
        qw.eq(Books::getNews,news);
        List<Books> list = booksService.list(qw);
        List<Books> lists = list.stream().map(item -> {
            Long cid=item.getSortid();
            Booksort s= booksortService.getById(cid);
            item.setSortname(s.getName());
            return  item;
        }).collect(Collectors.toList());
        return Result.ok(Constants.FIND_SUCCESS).data(lists);
    }

    //根据id查询
    @PostMapping("/query/{id}")
    public Result queryById(@PathVariable("id")Long id){
//        LambdaQueryWrapper<Books> qw = new LambdaQueryWrapper<>();
        Books books = booksService.getById(id);
        if(books==null){
            return Result.ok(Constants.NO_FIND);
        }
        return Result.error(Constants.FIND_SUCCESS).data(books);
    }

    //    增加图书
    @PostMapping("/add")
    public Result addBook(@RequestBody Books books){
        System.out.println(books);
        books.setNumber(2);
        boolean flang = booksService.save(books);
        if(flang){
            return Result.ok(Constants.ADD_SUCCESS);
        }
        return Result.error(Constants.ADD_ERROR);
    }

    //    修改信息
    @PostMapping("/update")
    public Result updateBook(@RequestBody Books books){
        System.out.println(books);
        boolean flang = booksService.updateById(books);
        if(flang){
            return Result.ok(Constants.UPDATE_OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }

    //    删除信息
    @PostMapping("/delete/{id}")
    public Result deleteBook(@PathVariable("id")Long id){
        boolean flang = booksService.removeById(id);
        if(flang){
            return Result.ok(Constants.DELETE_OK);
        }
        return Result.error(Constants.DELETE_ERROR);
    }

}

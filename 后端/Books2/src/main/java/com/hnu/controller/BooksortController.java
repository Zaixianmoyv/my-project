package com.hnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Books;
import com.hnu.entity.Booksort;
import com.hnu.service.BooksortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //服务器端解决跨域
@RequestMapping("/booksort")
@RestController
public class BooksortController {

    @Autowired
    private BooksortService booksortService;

    //分页查询  查
    @PostMapping("/findall/{currentPage}/{limit}")
    public Result findAll(@RequestBody Booksort booksort,
                          @PathVariable("currentPage")int currentPage,
                          @PathVariable("limit")int limit) {
        IPage<Booksort> page = new Page<>(currentPage, limit);
        List<Booksort> list = null;
        if (booksort.getName() == null) {
            list = booksortService.list(null);
        } else {
        LambdaQueryWrapper<Booksort> qw = new LambdaQueryWrapper<>();
        qw.like(Booksort::getName, booksort.getName());
        list = booksortService.page(page,qw).getRecords();
        }
        if (list.size()<=0){
            return Result.error(Constants.NO_FIND);
        }
        return Result.ok(Constants.FIND_SUCCESS).data(list).totalNum(page.getTotal());
    }

    @PostMapping("/findAll")
    public Result findAll1(){
//        LambdaQueryWrapper<Booksort> qw = new LambdaQueryWrapper<>();
        List<Booksort> list = booksortService.list(null);
        if(list.size()<=0){
            return Result.ok(Constants.NO_FIND);
        }
        return Result.error(Constants.FIND_SUCCESS).data(list);
    }

    ////    增
    @PostMapping("/addSort")
    public Result addSort(@RequestBody Booksort booksort){
        System.out.println(booksort);
        boolean flang = booksortService.save(booksort);
        if(flang){
            return Result.ok(Constants.ADD_SUCCESS);
        }
        return Result.error(Constants.ADD_ERROR);

    }

    ////    删
    @PostMapping("/deleteSort/{id}")
    public Result deleteSort(@PathVariable("id")Long id) {
        boolean flang = booksortService.removeById(id);
        if (flang) {
            return Result.ok(Constants.DELETE_OK).data(flang);
        }
        return Result.error(Constants.DELETE_ERROR);
    }

    ////    改
    @PostMapping("/updateSort")
    public Result updateSort(@RequestBody Booksort booksort){
        System.out.println(booksort);
        boolean flang = booksortService.updateById(booksort);
        if(flang){
            return Result.ok(Constants.UPDATE_OK);
        }
        return Result.error(Constants.UPDATE_ERROR);
    }
}

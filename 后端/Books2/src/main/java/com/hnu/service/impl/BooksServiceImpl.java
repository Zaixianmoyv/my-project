package com.hnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnu.entity.Books;
import com.hnu.service.BooksService;
import com.hnu.mapper.BooksMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService{

}





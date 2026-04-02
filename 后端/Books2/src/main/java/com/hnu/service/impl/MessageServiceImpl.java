package com.hnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.hnu.common.Constants;
import com.hnu.dto.Result;
import com.hnu.entity.Message;
import com.hnu.service.MessageService;
import com.hnu.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
        implements MessageService{
}





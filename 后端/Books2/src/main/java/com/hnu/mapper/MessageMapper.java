package com.hnu.mapper;

import com.hnu.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Entity com.hnu.entity.Message
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}





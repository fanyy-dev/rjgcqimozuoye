package com.hotely5d.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotely5d.dao.MemberMapper;
import com.hotely5d.dao.MessageMapper;
import com.hotely5d.entity.Member;
import com.hotely5d.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MemberMapper memberMapper;

    public List<Message> findAll() {
        return messageMapper.selectList(null);
    }

    public Page<Message> search(Page<Message> page,Message message) {
        Page<Message> resultPage = messageMapper.selectPage(page, getQueryWrapper(message));
        for (Message m : resultPage.getRecords()) {
            Member member = memberMapper.selectById(m.getMemberId());
            m.setMember(member);
        }
        return resultPage;
    }

    public List<Message> search(Message message) {
        return messageMapper.selectList(getQueryWrapper(message));
    }

    //条件构造器
    private QueryWrapper getQueryWrapper(Message message){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(null != message){
            if(!StringUtils.isEmpty(message.getComment())){
                queryWrapper.like("comment",String.valueOf(message.getComment()));
            }
            if(!StringUtils.isEmpty(message.getReply())){
                queryWrapper.like("reply",String.valueOf(message.getReply()));
            }
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    public void modify(Message message) {
        messageMapper.updateById(message);
    }

    public void add(Message message) {
        messageMapper.insert(message);
    }

    public Message findById(Integer id) {
        return messageMapper.selectById(id);
    }

    public void removeById(Integer id) {
        messageMapper.deleteById(id);
    }
}

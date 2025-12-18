package com.hotely5d.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotely5d.dao.NoticeMapper;
import com.hotely5d.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
@Transactional
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    public List<Notice> findAll() {
        return noticeMapper.selectList(null);
    }

    public Page<Notice> search(Page<Notice> page,Notice notice) {
        return noticeMapper.selectPage(page,getQueryWrapper(notice));
    }

    public List<Notice> search(Notice notice) {
        return noticeMapper.selectList(getQueryWrapper(notice));
    }

    //条件构造器
    private QueryWrapper getQueryWrapper(Notice notice){
        QueryWrapper queryWrapper = new QueryWrapper();
        if(null != notice){
            if(!StringUtils.isEmpty(notice.getContent())){
                queryWrapper.like("content",String.valueOf(notice.getContent()));
            }
            if(!StringUtils.isEmpty(notice.getTitle())){
                queryWrapper.like("title",String.valueOf(notice.getTitle()));
            }
        }
        queryWrapper.orderByDesc("create_time");
        return queryWrapper;
    }

    public void modify(Notice notice) {
        noticeMapper.updateById(notice);
    }

    public void add(Notice notice) {
        noticeMapper.insert(notice);
    }

    public Notice findById(Integer id) {
        return noticeMapper.selectById(id);
    }

    public void removeById(Integer id) {
        noticeMapper.deleteById(id);
    }

    public List<Notice> getTop5() {
        return noticeMapper.selectList(new QueryWrapper<Notice>().orderByDesc("create_time").last("limit 5"));
    }
}

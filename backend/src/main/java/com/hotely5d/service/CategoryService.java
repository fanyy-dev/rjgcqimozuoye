package com.hotely5d.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotely5d.dao.CategoryMapper;
import com.hotely5d.dao.RoomMapper;
import com.hotely5d.entity.Category;
import com.hotely5d.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RoomMapper roomMapper;

    public List<Category> findAll() {
        List<Category> categories = categoryMapper.selectList(null);
        for (Category category : categories) {
            Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("category_id", category.getId()).eq("status", 1));
            category.setRoomNum(count.intValue());
        }
        return categories;
    }

    public Page<Category> search(Page<Category> page,Category category) {
        Page<Category> resultPage = categoryMapper.selectPage(page, getQueryWrapper(category));
        for (Category c : resultPage.getRecords()) {
            Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("category_id", c.getId()).eq("status", 1));
            c.setRoomNum(count.intValue());
        }
        return resultPage;
    }

    public List<Category> search(Category category) {
        List<Category> list = categoryMapper.selectList(getQueryWrapper(category));
        for (Category c : list) {
            Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("category_id", c.getId()).eq("status", 1));
            c.setRoomNum(count.intValue());
        }
        return list;
    }

    //条件构造器
    private QueryWrapper<Category> getQueryWrapper(Category category){
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if(null != category){
            if(!StringUtils.isEmpty(category.getCategoryName())){
                queryWrapper.like("category_name",String.valueOf(category.getCategoryName()));
            }
            if(!StringUtils.isEmpty(category.getPhoto())){
                queryWrapper.like("photo",String.valueOf(category.getPhoto()));
            }
            if(!StringUtils.isEmpty(category.getArea())){
                queryWrapper.like("area",String.valueOf(category.getArea()));
            }
            if(!StringUtils.isEmpty(category.getIntroduce())){
                queryWrapper.like("introduce",String.valueOf(category.getIntroduce()));
            }
        }
        return queryWrapper;
    }

    public void modify(Category category) {
        categoryMapper.updateById(category);
    }

    public void add(Category category) {
        categoryMapper.insert(category);
    }

    public Category findById(Integer id) {
        Category c = categoryMapper.selectById(id);
        Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("category_id", c.getId()).eq("status", 1));
        c.setRoomNum(count.intValue());
        return c;
    }

    public void removeById(Integer id) {
        categoryMapper.deleteById(id);
    }
}

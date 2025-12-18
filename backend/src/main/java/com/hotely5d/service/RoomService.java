package com.hotely5d.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotely5d.dao.CategoryMapper;
import com.hotely5d.dao.OrdersMapper;
import com.hotely5d.dao.RoomMapper;
import com.hotely5d.entity.Category;
import com.hotely5d.entity.Orders;
import com.hotely5d.entity.Room;
import com.hotely5d.entity.model.Result;
import com.hotely5d.entity.model.StatusCode;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    @Lazy
    private AppointmentService appointmentService;
    @Autowired
    private OrdersMapper ordersMapper;

    public List<Room> findAll() {
        return roomMapper.selectList(null);
    }

    public Page<Room> search(Page<Room> page,Room room) {
        Page<Room> resultPage = roomMapper.selectPage(page, getQueryWrapper(room));
        for (Room r : resultPage.getRecords()) {
            Category category = categoryMapper.selectById(r.getCategoryId());
            r.setCategory(category);
        }
        return resultPage;
    }

    public List<Room> search(Room room) {
        return roomMapper.selectList(getQueryWrapper(room));
    }

    //条件构造器
    private QueryWrapper<Room> getQueryWrapper(Room room){
        QueryWrapper<Room> queryWrapper = new QueryWrapper<>();
        if(null != room){
            if(!StringUtils.isEmpty(room.getRoomNum())){
                queryWrapper.like("room_num",String.valueOf(room.getRoomNum()));
            }
            if(!StringUtils.isEmpty(room.getCategoryId())){
                queryWrapper.eq("category_id", room.getCategoryId());
            }
            if(!StringUtils.isEmpty(room.getStatus())){
                queryWrapper.eq("status", room.getStatus());
            }
        }
        return queryWrapper;
    }

    public Result modify(Room room) {
        ArrayList<Integer> idList = new ArrayList<>();
        idList.add(room.getId());
        Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("room_num", room.getRoomNum()).notIn("id", idList));
        if(count != null && count > 0){
            return new Result(false, StatusCode.ERROR,"房间号已存在");
        }
        roomMapper.updateById(room);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    public Result add(Room room) {
        Long count = roomMapper.selectCount(new QueryWrapper<Room>().eq("room_num", room.getRoomNum()));
        if(count != null && count > 0){
            return new Result(false, StatusCode.ERROR,"房间号已存在");
        }
        roomMapper.insert(room);
        return new Result(true, StatusCode.OK,"新增成功");
    }

    public Room findById(Integer id) {
        Room room = roomMapper.selectById(id);
        //1.查询房型
        Category category = categoryMapper.selectById(room.getCategoryId());
        room.setCategory(category);
        //2.查询今日是否可(预订/入住)
        ArrayList<Integer> statusList = new ArrayList<>();
        statusList.add(1);//已预订
        statusList.add(2);//已入住
        List<Orders> ordersList = ordersMapper.selectList(new QueryWrapper<Orders>()
                .eq("room_id", room.getId())
                .in("status", statusList));
        if(ordersList.size() == 0){
            room.setCanUse(true);
            return room;
        }

        String startDate = DateFormatUtils.format(ordersList.get(0).getStartTime(),"yyyy-MM-dd");
        String currDate = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        //不为0，判断当前日期是否在入住日期+居住天数之后
        room.setCanUse(appointmentService.canAppointment(currDate,startDate,ordersList.get(0).getDays()));
        //3.查询已被(预订/入住)的日期
        List<String> dateList = new ArrayList();
        for (int i = 0; i < ordersList.get(0).getDays() ; i++) {
            try {
                Date date = DateUtils.parseDate(startDate, "yyyy-MM-dd");
                Date rs = DateUtils.addDays(date, i);
                dateList.add(DateFormatUtils.format(rs,"yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        room.setNotUseDateList(dateList);
        return room;
    }

    public void removeById(Integer id) {
        roomMapper.deleteById(id);
    }
}

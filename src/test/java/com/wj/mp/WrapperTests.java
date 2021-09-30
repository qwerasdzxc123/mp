package com.wj.mp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wj.mp.mapper.UserMapper;
import com.wj.mp.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author: WangJin
 * @description
 * @date: 2021/9/30 22:02
 */
@SpringBootTest
public class WrapperTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads(){
        //查询home不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    void test2(){
        //查询名字Tom
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","Tom");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    void test3(){
        //查询年龄在20-30之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30);
        Integer count = userMapper.selectCount(wrapper);
        userMapper.selectList(wrapper).forEach(System.out::println);
        System.out.println(count);
    }

    @Test
    void test4(){
        //查询年龄在20-30岁之间年龄的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30)
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //id在子查询中查出来
        wrapper.inSql("id","select id from user where id<=3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }
    
    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //通过id进行排序升序
        wrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

}

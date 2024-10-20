package life.mj.community.controller;


import jakarta.annotation.PostConstruct;
import life.mj.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

import javax.sql.DataSource;
import java.sql.SQLException;

@Controller
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test(){
        System.out.println(userMapper.t1());
        System.out.println(userMapper.t2());
        System.out.println(userMapper.t3());
        return "index";
    }

    @Autowired
    private DataSource dataSource;
    @PostConstruct
    public void logDataSource() throws SQLException {
        System.out.println("Data Source URL: " + dataSource.getConnection().getMetaData().getURL());
    }

}

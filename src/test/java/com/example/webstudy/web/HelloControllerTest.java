package com.example.webstudy.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static  org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = HelloController.class)
class HelloControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    void hi가_리턴된다() throws Exception{

        String hi = "hi spring";

        mvc.perform(get("/hi"))
                .andExpect(status().isOk())
                .andExpect(content().string(hi));

    }

    @Test
    void helloDto가_리턴된다() throws Exception{

        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello")   //get 요청
                .param("name", name)    //파라미터 name
                .param("amount", String.valueOf(amount)) // 파라미터 amount
                )
                .andExpect(status().isOk()) //200 상태 응답
                .andExpect(jsonPath("$.name", is(name)))    //응답을 검증, name이 "hello" 인지
                .andExpect(jsonPath("$.amount", is(amount)));   //응답을 검증, amount가 1000인지

    }


    @Test
    void testConn(){
        try(Connection conn = DriverManager.getConnection("jdbc:h2:./webStudy", "root", "1234")){
            System.out.println("연결 성공"+ conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
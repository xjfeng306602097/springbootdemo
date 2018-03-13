package com.xjf.springboot;

import com.xjf.controller.TestController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestControllerTests {

    private MockMvc mvc = null;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new TestController()).build();
    }

    @Test
    public void testController() throws Exception {
        RequestBuilder request = null;
        request = get("/test/");
        //1. 获取测试的用户列表
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
        request = post("/test/").param("id", String.valueOf(1)).param("name", "肖大师").param("age", String.valueOf(20));
        //2. 插入请求
        mvc.perform(request).andExpect(content().string(equalTo("success")));
        //3. get获取user列表，应该有刚才插入的数据
        request = get("/test/1");
        mvc.perform(request).andExpect(content().string(equalTo("{\"id\":1,\"name\":\"肖大师\",\"age\":25}")));
        //4. put请求
        request = put("/test/1").param("name", "肖大佬").param("age", String.valueOf(25));
        mvc.perform(request).andExpect(content().string(equalTo("success")));
        //5. delete请求
        request = delete("/test/1");
        mvc.perform(request).andExpect(content().string(equalTo("success")));
    }

}

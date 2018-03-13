package com.xjf.springboot;

import com.xjf.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XiaojunfengApplicationTests {

	private MockMvc mvc;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
	}

	@Test
	public void contextLoads() {
		List<String> list = Arrays.asList(new String[]{"你好", "我好"});
		List newList = list.stream().filter(name -> name.equals("我好")).collect(Collectors.toList());
		newList.forEach(name -> System.out.println("Item after sorted:" + name));
	}


}

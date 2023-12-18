package com.example.todolist;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import com.example.todolist.controller.ProjectListController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("123")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProjectListController projectListController;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[1]/div[1]/div/span/span[2]").string("123"));
    }

    @Test
    public void projectListTest() throws Exception {
        this.mockMvc.perform(get("/projects"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("/html/body/div[2]/div[2]/div[1]/span").string("my first project"))
                .andExpect(xpath("/html/body/div[2]/div[3]/div[1]/span").string("my second project"))
                .andExpect(xpath("/html/body/div[2]/div[4]/div[1]/span").string("my third project"));
    }
}

package com.gft;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfig.class })
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(value = { NotesController.class, NotesService.class, ResourceUtils.class })
@EnableWebMvc
public class NotesControllerIntegrationTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    
    @Test
    public void getAllNotes() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/notes").accept(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("my super note"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].contents[0]").value("Siemanko to jest moja super ekstra pierwsza notatka"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].contents[1]").value("w której daję break line'a i już teraz skończę."))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        List<Note> notes = objectMapper.readValue(json, new TypeReference<List<Note>>(){});
        
        assertEquals("application/json;charset=UTF-8", mvcResult.getResponse().getContentType());
        assertEquals(notes.size(), 2);
    }
}

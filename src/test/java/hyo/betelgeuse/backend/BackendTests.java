package hyo.betelgeuse.backend;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.TemporalType;
import java.sql.Date;
import java.util.Collections;


@RunWith(SpringRunner.class)
@WebMvcTest
public class BackendTests {
    //Unit tests are mocking repositories.
    @Autowired
    MockMvc mockMvc;    //mocks our appcontroller class

    //mock the articleRepository found in appcontroller
    @MockBean
    ArticleRepository articleRepository;

    @Test
    public void testingAll() throws Exception {

        Mockito.when(articleRepository.findAll()).thenReturn(
                Collections.emptyList()
        );

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/all/")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        System.out.println("Result: "+result);
        Mockito.verify(articleRepository).findAll();

    }
//    @Test
//    public void testingWordsWithDate() throws Exception {
//
//        //Format of date is year,month,day.
//        Mockito.when(articleRepository.findByPublishDate(new Date(2020,03,05), new Date(2020, 06,20))).thenReturn(
//                Collections.emptyList()
//        );
//
//        MvcResult result = mockMvc.perform(
//                MockMvcRequestBuilders.get("/getWordsBetweenDates/")
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andReturn();
//
//        System.out.println("Result: "+result);
//        Mockito.verify(articleRepository).findByPublishDate(new Date(2020,03,05), new Date(2020, 06,20));
//    }
//
//
//    //Testing ArticleRepository
//    @Test
//    public void testingArticleRepo(){
//
//    }

}

//package hyo.betelgeuse.backend;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Collections;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
//classes = BetelgeuseApplication.class)
//@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:app-test.properties")
//public class BackendIntegrationTests {
//
//    @Autowired
//    MockMvc mockMvc;    //mocks our appcontroller class
//
//
//    @Test
//    public void testingAll() throws Exception {
//
//        MvcResult result = mockMvc.perform(
//                MockMvcRequestBuilders.get("/all/")
//                        .accept(MediaType.APPLICATION_JSON)
//        ).andReturn();
//
//        System.out.println("Result: "+result);
//
//    }
//}

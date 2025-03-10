package test.topia.exchange;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class ConversionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void latestConversionAPI() throws Exception  {
        ResultActions resultActions = this.mockMvc.perform(get("http://localhost:8080/fx"))
                .andExpect(status().isOk());
        String content = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response-" + content);
    }

    @Test
    void conversionHistoryAPI() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(get("http://localhost:8080/fx/GBP"))
                .andExpect(status().isOk());
        String content = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response-" + content);
    }
}

package lab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc
class ApiTest {
    @Autowired MockMvc mvc;
    @Test void healthy() throws Exception { mvc.perform(get("/health")).andExpect(status().isOk()); }
    @Test void divideNormally() throws Exception { mvc.perform(get("/api/divide?a=12&b=3")).andExpect(status().isOk()).andExpect(jsonPath("$.result").value(4)); }
    @Test void rejectZero() throws Exception { mvc.perform(get("/api/divide?a=12&b=0")).andExpect(status().is4xxClientError()); }
    @Test void parseNormally() throws Exception { mvc.perform(get("/api/parse?value=42")).andExpect(status().isOk()).andExpect(jsonPath("$.result").value(42)); }
    @Test void rejectLetters() throws Exception { mvc.perform(get("/api/parse?value=hello")).andExpect(status().is4xxClientError()); }
}

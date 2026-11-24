package lab;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiInputValidationTest {
    @Autowired MockMvc mvc;

    @Test
    void nonNumericParseValueReturnsBadRequest() throws Exception {
        mvc.perform(get("/api/parse").param("value", "hello"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void outOfRangeParseValueReturnsBadRequest() throws Exception {
        mvc.perform(get("/api/parse").param("value", "2147483648"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void divisionByZeroReturnsBadRequest() throws Exception {
        mvc.perform(get("/api/divide").param("a", "12").param("b", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validParseStillReturnsParsedResult() throws Exception {
        mvc.perform(get("/api/parse").param("value", "42"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(42));
    }
}

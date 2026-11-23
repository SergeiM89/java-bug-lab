package lab;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ApiController {
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }

    @GetMapping("/api/divide")
    public Map<String, Integer> divide(@RequestParam int a, @RequestParam int b) {
        if (b == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "b must not be zero");
        }
        return Map.of("result", a / b);
    }

    @GetMapping("/api/parse")
    public Map<String, Integer> parse(@RequestParam String value) {
        try {
            return Map.of("result", Integer.parseInt(value));
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "value must be a 32-bit integer", exception);
        }
    }
}
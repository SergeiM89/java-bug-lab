package lab;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/health")
    public Map<String, String> health() { return Map.of("status", "ok"); }

    @GetMapping("/api/divide")
    public Map<String, Integer> divide(@RequestParam int a, @RequestParam int b) {
        return Map.of("result", a / b);
    }

    @GetMapping("/api/parse")
    public Map<String, Integer> parse(@RequestParam String value) {
        return Map.of("result", Integer.parseInt(value));
    }
}

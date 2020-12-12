package platform;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {
    private static Code code;

    public static void main(String[] args) {
        code = new Code("123", LocalDateTime.now());
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code", produces = MediaType.TEXT_HTML_VALUE)
    public String getCode() {
        return code.asHtml();
    }

    @GetMapping(value = "/api/code", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCodeForApi() {
        return code.asJson();
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getForm() {
        return FormWrapper.getForm();
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateCode(@RequestBody CodeWrapper code) {
        CodeSharingPlatform.code = new Code(code.code, LocalDateTime.now());
        return "{}";
    }
}
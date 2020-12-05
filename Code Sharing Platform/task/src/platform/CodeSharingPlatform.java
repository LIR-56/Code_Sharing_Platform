package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {
    private static final Code code = new Code("    public static void main(String[] args) {\n" +
            "        SpringApplication.run(CodeSharingPlatform.class, args);\n" +
            "    }\n");

    public static void main(String[] args) {
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

}

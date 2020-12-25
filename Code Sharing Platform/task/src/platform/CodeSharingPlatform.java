package platform;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    private final CodeRepository dao;
    private static final Code defaultValue = new Code("", LocalDateTime.now());

    public CodeSharingPlatform(CodeRepository dao) {
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{number}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCode(@PathVariable long number) {
        return dao.findById(number).orElse(defaultValue).asHtml();
    }

    @GetMapping(value = "/api/code/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCodeForApi(@PathVariable long number) {
        return dao.findById(number).orElse(defaultValue).asJson();
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestCode() {
        var latestCodePieces = dao.findTop10ByOrderByDateDesc().stream()
                .flatMap(x -> Stream.of(x.getDate().format(Code.dtf), HtmlUtils.htmlEscape(x.getCode())))
                .toArray(String[]::new);
        var s = FormWrapper.getFormForSeveralCodeFragments(latestCodePieces.length / 2, "Latest");
        return String.format(s, (Object[]) latestCodePieces);
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Code[] getLatestCodeForApi() {
        return dao.findTop10ByOrderByDateDesc().toArray(Code[]::new);
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getForm() {
        return FormWrapper.getFormForInputCodeFragments();
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createNewCode(@RequestBody CodeWrapper codeToAdd) {
        var id = dao.save(new Code(codeToAdd.code, LocalDateTime.now())).getId();
        return Map.of("id", String.valueOf(id));
    }
}
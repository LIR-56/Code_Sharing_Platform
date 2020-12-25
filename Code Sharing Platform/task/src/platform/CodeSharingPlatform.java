package platform;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

@SpringBootApplication
@RestController
public class CodeSharingPlatform {
    private static volatile List<Code> code;

    public static void main(String[] args) {
        code = new ArrayList<>();
        //code.add(new Code("123", LocalDateTime.now()));
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{number}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCode(@PathVariable int number) {
        return code.get(number-1).asHtml();
    }

    @GetMapping(value = "/api/code/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCodeForApi(@PathVariable int number) {
        return code.get(number-1).asJson();
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestCode() {
        var latestCodePieces = code.stream()
                .sorted(Comparator.comparing(Code::getDate).reversed())
                .limit(10)
                .flatMap(x -> Stream.of(x.getDate().toString(), x.getCode()))
                .toArray(String[]::new);
        var s = FormWrapper.getFormForSeveralCodeFragments(latestCodePieces.length/2, "Latest");
        return String.format(s, (Object[]) latestCodePieces);
    }

    @GetMapping(value = "/api/code/latest", produces = MediaType.APPLICATION_JSON_VALUE)
    public Code[] getLatestCodeForApi() {
        return code.stream()
                .sorted(Comparator.comparing(Code::getDate).reversed())
                .limit(10)
                .toArray(Code[]::new);
    }

    @GetMapping(value = "/code/new", produces = MediaType.TEXT_HTML_VALUE)
    public String getForm() {
        return FormWrapper.getFormForInputCodeFragments();
    }

    @PostMapping(value = "/api/code/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createNewCode(@RequestBody CodeWrapper codeToAdd) {
        code.add(new Code(codeToAdd.code, LocalDateTime.now()));
        return Map.of("id", String.valueOf(code.size()));
    }
}
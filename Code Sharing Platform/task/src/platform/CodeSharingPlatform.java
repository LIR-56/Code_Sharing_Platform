package platform;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    public CodeSharingPlatform(CodeRepository dao, ObjectMapper objectMapper) {
        this.dao = dao;
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getCode(@PathVariable UUID id, HttpServletResponse response) throws IOException {
        Code code = getCodeAndUpdate(id, response);
        if (code == null) return "";
        return code.asHtml();
    }

    @GetMapping(value = "/api/code/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getCodeForApi(@PathVariable UUID id, HttpServletResponse response) throws IOException {
        Code code = getCodeAndUpdate(id, response);
        if (code == null) return "";
        return objectMapper.writeValueAsString(code);
    }

    @GetMapping(value = "/code/latest", produces = MediaType.TEXT_HTML_VALUE)
    public String getLatestCode() {
        var result = dao.findTop10ByOrderByDateDesc().stream()
                .flatMap(x -> Stream.of(x.getDate().format(Code.dtf), HtmlUtils.htmlEscape(x.getCode())))
                .toArray(String[]::new);
        var s = FormWrapper.getFormForSeveralCodeFragments(result.length / 2, "Latest");
        return String.format(s, (Object[]) result);
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
        var id = dao.save(
                new Code(
                        codeToAdd.code,
                        LocalDateTime.now(),
                        codeToAdd.time < 0 ? 0 : codeToAdd.time,
                        codeToAdd.views < 0 ? 0 : codeToAdd.views)
        ).getId();
        return Map.of("id", String.valueOf(id));
    }

    private Code getCodeAndUpdate(UUID id, HttpServletResponse response) throws IOException {
        var codeOpt = dao.findById(id);
        if (codeOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        var code = codeOpt.get();
        updateCode(code);
        return code;
    }

    private void updateCode(Code code) {
        var views = code.getViews();
        if (views == 1) {
            code.setViews(-1);
            dao.save(code);
            code.setViews(0);
            code.setShowZeroViewsAsLimitations(true);
            return;
        } else if (views > 1) {
            code.setViews(views - 1);
        }
        dao.save(code);
    }
}
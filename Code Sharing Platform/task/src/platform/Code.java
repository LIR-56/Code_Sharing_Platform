package platform;

import java.time.LocalDateTime;

import org.springframework.web.util.HtmlUtils;

public class Code {
    private static final String HTML_TEMPLATE = "<html>\n" +
            "    <head><title>Code</title></head>\n" +
            "    <body><pre id=\"code_snippet\">\n" +
            "%s\n" +
            "    </pre></body>\n" +
            "<span id=\"load_date\">%s</span>\n" +
            "</html>";
    private static final String JSON_TEMPLATE = "{\n" +
            "    \"code\": \"%s\",\n" +
            "    \"date\": \"%s\"\n" +
            "}";

    private final String code;
    private final LocalDateTime date;


    Code(String code, LocalDateTime createDateTime) {
        this.code = code;
        this.date = createDateTime;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String asJson() {
        return String.format(JSON_TEMPLATE, code, date);
    }

    public String asHtml() {
        return String.format(HTML_TEMPLATE, HtmlUtils.htmlEscape(code), date);
    }
}

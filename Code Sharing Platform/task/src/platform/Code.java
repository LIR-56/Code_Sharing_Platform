package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.util.HtmlUtils;

@Entity
public class Code {

    private static final String pattern = "yyyy/MM/dd hh:mm:ss";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    private static final String HTML_TEMPLATE = "<html>\n" +
            "    <head>" +
            "<link rel=\"stylesheet\"\n" +
            "       target=\"_blank\" href=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2" +
            ".1/build/styles/default.min.css\">\n" +
            "<script src=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js\"></script>\n" +
            "<script>hljs.initHighlightingOnLoad();</script>\n" +
            "<title>Code</title></head>\n" +
            "    <body><pre id=\"code_snippet\">\n" +
            "<code>\n" +
            "%s\n" +
            "</code>\n" +
            "</pre>\n</body>\n" +
            "<span id=\"load_date\">%s</span>\n" +
            "</html>";
    private static final String JSON_TEMPLATE = "{\n" +
            "    \"code\": \"%s\",\n" +
            "    \"date\": \"%s\"\n" +
            "}";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;
    private String code;
    @DateTimeFormat(pattern = pattern)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = pattern)
    private LocalDateTime date;

    public Code() {
    }

    Code(String code, LocalDateTime date) {
        this.code = code;
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String asJson() {
        return String.format(JSON_TEMPLATE, code, date.format(dtf));
    }

    public String asHtml() {
        return String.format(HTML_TEMPLATE, HtmlUtils.htmlEscape(code), date.format(dtf));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

package platform;

public class Code {
    private static final String HTML_TEMPLATE = "<html>\n" +
            "    <head><title>Code</title></head>\n" +
            "    <body><pre>\n" +
            "%s\n" +
            "    </pre></body>\n" +
            "</html>>";
    private static final String JSON_TEMPLATE = "{\n" +
            "    \"code\": \"%s\"" +
            "}";

    private final String code;


    Code(String code) {
        this.code = code;
    }

    public String asJson() {
        return String.format(JSON_TEMPLATE, code);
    }

    public String asHtml() {
        return String.format(HTML_TEMPLATE, code);
    }
}

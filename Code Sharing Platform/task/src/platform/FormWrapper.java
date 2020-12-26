package platform;

public class FormWrapper {

    public static String getFormForInputCodeFragments() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Create</title>\n" +
                "    <script type=\"\">\n" +
                "        function send() {\n" +
                "            let object = {\n" +
                "                \"code\": document.getElementById(\"code_snippet\").value,\n" +
                "                \"time\": document.getElementById(\"time_restriction\").value,\n" +
                "                \"views\": document.getElementById(\"views_restriction\").value\n" +
                "            };\n" +
                "\n" +
                "            let json = JSON.stringify(object);\n" +
                "\n" +
                "            let xhr = new XMLHttpRequest();\n" +
                "            xhr.open(\"POST\", '/api/code/new', false)\n" +
                "            xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "            xhr.send(json);\n" +
                "\n" +
                "            if (xhr.status == 200) {\n" +
                "              alert(\"Success!\");\n" +
                "            }\n" +
                "        }\n" +
                "    </script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <input id=\"time_restriction\" type=\"text\" placeholder=\"Seconds amount until expiring\"/>\n" +
                "    <input id=\"views_restriction\" type=\"text\" placeholder=\"Views amount until expiring\"/>\n" +
                "    <textarea id=\"code_snippet\" placeholder=\"//write your code here\"></textarea>\n" +
                "    <button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String getFormForSeveralCodeFragments(int amountOfCodeFragments, String title) {
        return "<html>\n" +
                "    <head><title>" + title + "</title></head>\n" +
                "    <body>\n" +
                ("<span>%s</span>\n" +
                        "<pre id=\"code_snippet\">\n" +
                        "<code>" +
                        "%s\n" +
                        "</code>\n" +
                        "</pre>\n").repeat(Math.max(0, amountOfCodeFragments)) +
                "   </body>\n" +
                "   </html>";
    }

    public static String getTemplateForCode(boolean hasTimeLimits, boolean hasViewsLimits) {
        String htmlTemplate = "<html>\n" +
                "    <head>" +
                "<link rel=\"stylesheet\"\n" +
                "       target=\"_blank\" href=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2" +
                ".1/build/styles/default.min.css\">\n" +
                "<script src=\"//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min" +
                ".js\"></script>\n" +
                "<script>hljs.initHighlightingOnLoad();</script>\n" +
                "<title>Code</title></head>\n" +
                "    <body><pre id=\"code_snippet\">\n" +
                "<code>\n" +
                "%s\n" +
                "</code>\n" +
                "</pre>\n" +
                "<span id=\"load_date\">%s</span>\n";

        if (hasTimeLimits) {
            htmlTemplate += "<span id=\"time_restriction\">%d</span>\n";
        }
        if (hasViewsLimits) {
            htmlTemplate += "<span id=\"views_restriction\">%d</span>\n";
        }
        htmlTemplate += "</body>\n</html>";
        return htmlTemplate;
    }
}

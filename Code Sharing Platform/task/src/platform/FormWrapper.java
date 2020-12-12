package platform;

public class FormWrapper {

    public static String getForm() {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Create</title>\n" +
                "    <script type=\"\">\n" +
                "        function send() {\n" +
                "            let object = {\n" +
                "                \"code\": document.getElementById(\"code_snippet\").value\n" +
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
                "    <textarea id=\"code_snippet\" placeholder=\"//write your code here\"></textarea>\n" +
                "    <button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>\n" +
                "</body>\n" +
                "</html>";
    }
}

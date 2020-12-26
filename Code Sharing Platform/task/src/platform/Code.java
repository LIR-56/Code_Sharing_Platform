package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.util.HtmlUtils;

import static platform.FormWrapper.getTemplateForCode;

@Entity
public class Code {

    private static final String pattern = "yyyy/MM/dd hh:mm:ss";
    public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);

    @Id
    @JsonIgnore
    private UUID id = UUID.randomUUID();

    private String code;

    @DateTimeFormat(pattern = pattern)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = pattern)
    private LocalDateTime date;

    private long time;

    private long views;

    @javax.persistence.Transient
    @JsonIgnore
    private boolean showZeroViewsAsLimitations = false;


    public Code() {
    }

    Code(String code, LocalDateTime date) {
        this.code = code;
        this.date = date;
    }

    public Code(String code, LocalDateTime date, long time, long views) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public void setShowZeroViewsAsLimitations(boolean showZeroViewsAsLimitations) {
        this.showZeroViewsAsLimitations = showZeroViewsAsLimitations;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String asHtml() {
        var params = new LinkedList<>();
        params.add(HtmlUtils.htmlEscape(code));
        params.add(date.format(dtf));
        var showViews = views > 0 || (showZeroViewsAsLimitations && views == 0);
        if (time > 0) params.add(time);
        if (showViews) params.add(views);

        return String.format(getTemplateForCode(time > 0, showViews),
                params.toArray());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

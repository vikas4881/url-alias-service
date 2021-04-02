package com.neueda.url.utils;

import com.neueda.url.message.Message;
import org.springframework.stereotype.Component;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
public class DateUtils {

    Format formatter = new SimpleDateFormat(Message.Constants.DATE_FORMAT);

    public String getDateInString(Date date) {
        return Objects.isNull(date) ? null : formatter.format(date);
    }

    public Date getCurrentDateTime() {
        return new Date();
    }
}

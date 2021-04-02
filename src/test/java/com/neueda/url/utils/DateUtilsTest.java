package com.neueda.url.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {

    @InjectMocks
    private DateUtils dateUtils;

    @Test
    public void testGetDateInString() {
        Date date = getDate();
        String expectedResponse = dateUtils.getDateInString(date);
        assertEquals(expectedResponse, "2021-04-02 00:00:00");
    }

    @Test
    public void testGetCurrentDateTime() {
        Date currentDate = new Date();
        Date expectedDate = dateUtils.getCurrentDateTime();
        assertEquals(currentDate, expectedDate);
    }

    private Date getDate() {
        String strDate="02/04/2021";
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }
}

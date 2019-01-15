/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unify.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.mongodb.morphia.converters.SimpleValueConverter;
import org.mongodb.morphia.converters.TypeConverter;
import org.mongodb.morphia.mapping.MappedField;
import org.mongodb.morphia.mapping.MappingException;

/**
 *
 * @author jordi_000
 */
@SuppressWarnings("rawtypes")
public class CalendarConverter extends TypeConverter implements SimpleValueConverter {

    public CalendarConverter() {
        super(Calendar.class);
    }

    @Override
    public Object encode(Object val, MappedField optionalExtraInfo) {
        if (val == null) {
            return null;
        }
        Calendar calendar = (Calendar) val;
        long millis = calendar.getTimeInMillis();
        // . a date so that we can see it clearly in MongoVue
        // . the date is UTC because
        //   . timeZone.getOffset(millis) - timeZone.getOffset(newMillis)  may not be 0 (if we're close to DST limits)
        //   . and it's like that inside GregorianCalendar => more natural
        Date utcDate = new Date(millis);
        List<Object> vals = new ArrayList<Object>();
        vals.add(utcDate);
        vals.add(calendar.getTimeZone().getID());
        return vals;
    }

    @Override
    public Object decode(Class type, Object o, MappedField mf) throws MappingException {
        if (o == null) {
            return null;
        }
        List vals = (List) o;
        if (vals.size() < 2) {
            return null;
        }
        //-- date --//
        Date utcDate = (Date) vals.get(0);
        long millis = utcDate.getTime();

        //-- TimeZone --//
        String timeZoneId = (String) vals.get(1);
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        //-- GregorianCalendar construction --//
        GregorianCalendar calendar = new GregorianCalendar(timeZone);
        calendar.setTimeInMillis(millis);
        return calendar;
    }
}


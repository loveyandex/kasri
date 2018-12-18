package com.amin.test;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;

/**
 * is created by aMIN on 12/18/2018 at 2:21 AM
 */
public class JS {
    @Test
    public void test(){
        final Month plus = Month.of(2).plus(13443);
        DayOfWeek dayOfWeek=DayOfWeek.MONDAY;
        MonthDay monthDay=MonthDay.now();
        final MonthDay monthDay1 = MonthDay.of(2, 29);
        final LocalDate localDate = LocalDate.of(2016, 2, 29);
        final LocalDate plus1 = localDate.plus(-42-10, ChronoUnit.DAYS);
        final LocalDate localDate1 = localDate.plusDays(1);
        System.out.println(plus1);
        System.out.println(plus1.getMonth().getValue());
        System.out.println(plus1.getDayOfMonth());
    }

    @Test
    public void test3(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 33; k++) {
                    System.out.printf("%d %d %d \n",k,j,i);
                    if (k>2)break;
                }
            }
        }

    }
}

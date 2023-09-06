package com.example.finalproject_phase2.util.validation;

import com.example.finalproject_phase2.custom_exception.CustomException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class CalenderAndValidation {
    private LocalDate temporaryDate;
    private LocalTime temporaryTime;
    public boolean setAndConvertDate(LocalDate localDateOfCustomerEntered,int day,int month,int year,
                                       LocalTime localTimeOfCustomerEntered,int hour,int minutes){
        try {
            temporaryDate=LocalDate.of(year, month, day);
            temporaryTime=LocalTime.of(hour, minutes, 0);
            int dateComparison = temporaryDate.compareTo(localDateOfCustomerEntered);
            int timeComparison = temporaryTime.compareTo(localTimeOfCustomerEntered);
            if (dateComparison > 0||dateComparison==0) {
                if (timeComparison>0||timeComparison==0)
                {return true;
                }else   throw new CustomException("you must be inter time for present or future");
            } else throw new CustomException("you must be inter date for present or future");
        }catch (CustomException e){
            System.out.println(e.getMessage());
            return false;
        }

    }
    public LocalTime setAndConvertTime(LocalTime localTimeOfCustomerEntered,int hour,int minutes){
        try {
            temporaryTime=LocalTime.of(hour, minutes, 0);
          //  if (localTimeOfCustomerEntered.getHour()>)
            if(temporaryTime.isBefore(localTimeOfCustomerEntered)){
                throw new CustomException("you must be inter for present or future");}
        }catch (CustomException e){
            System.out.println(e.getMessage());
            return localTimeOfCustomerEntered;
        }
        return temporaryTime;
    }
}

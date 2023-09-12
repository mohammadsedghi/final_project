package com.example.finalproject_phase2.util.validation;

import com.example.finalproject_phase2.custom_exception.CustomException;

public class PaymentValidation {
    CustomRegex customRegex=new CustomRegex();
    public String isValidCard(String cardNumber1,String cardNumber2,String cardNumber3,
    String cardNumber4,String cvv2,String month,String year,String captchaText,String captcha,String password){
      String response;
        try {
            if (!customRegex.checkOneInputIsValid(cardNumber1, customRegex.getValidDigitCardNumberPartOne())) {
                throw new CustomException("cardNumber is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(cardNumber2,customRegex.getValidCardNumber())){
                throw new CustomException("cardNumber is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(cardNumber3,customRegex.getValidCardNumber())){
                throw new CustomException("cardNumber is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(cardNumber4,customRegex.getValidCardNumber())){
                throw new CustomException("cardNumber is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(cvv2,customRegex.getValidCardNumber())){
                throw new CustomException("cvv2 is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(month,customRegex.getValidCardNumberMonth())){
                throw new CustomException("month is incorrect");
            }
            if (!customRegex.checkOneInputIsValid(year,customRegex.getValidCardNumberYear())){
                throw new CustomException("year is incorrect");
            }
            if (!captchaText.equals(captcha)){
                throw new CustomException("captcha is incorrect");
            }
            if (!password.equals("1234")){
                throw new CustomException("password is incorrect");
            }
            response="true";
        }catch(CustomException ce){ response=ce.getMessage();}
        return response;
    }
}

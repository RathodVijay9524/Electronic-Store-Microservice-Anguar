package com.vijay.commonservice.util;

import org.apache.commons.lang3.RandomStringUtils;

public class IdUtils {

    public static String generateId(){
        String characters = "0123456789";
        String pwd = RandomStringUtils.random( 6, characters );
        //System.out.println( pwd );
        return pwd;
    }

    public static String generatePaymentId(){
        String characters = "0123456789GFGFGNBWERTGHBCVCVCV";
        String pwd = RandomStringUtils.random( 8, characters );
        //System.out.println( pwd );
        return pwd;
    }
    public static String generateCartItemId(){
        String characters = "0123456789GFGFGNBWERTGHBCVCVCV9877745";
        String pwd = RandomStringUtils.random( 7, characters );
        //System.out.println( pwd );
        return pwd;
    }
}

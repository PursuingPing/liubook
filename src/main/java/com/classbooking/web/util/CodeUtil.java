package com.classbooking.web.util;

import java.util.UUID;

public class CodeUtil {
    public static String generateUniqueCode(){
        //Base64 UUID
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}

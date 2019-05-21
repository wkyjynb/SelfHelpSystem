package com.t226.tools;

import java.net.URLEncoder;

public class Idcard {

    public static String identityId(String imgStr) {
        String result=null;
        String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        try {

            String image = imgStr.substring(imgStr.lastIndexOf(",")+1);


            String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
                    + URLEncoder.encode(image, "UTF-8");
            String accessToken =AuthService.getAuth();
             result = HttpUtil.post(idcardIdentificate, accessToken, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}

package com.ldc.cfx.lovefx.provider;

import com.alibaba.fastjson.JSON;
import com.ldc.cfx.lovefx.dto.AccessTokenDTO;
import com.ldc.cfx.lovefx.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Name:
 * Description:
 * Created by itdragon on 2019-12-09
 */
@Component
public class GithubProvider {

    public static final MediaType mediaType
            = MediaType.get("application/json; charset=utf-8");

    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] strarr = string.split("&");
            String str1 = strarr[0];
            String token = str1.split("=")[1];
            System.out.println("token :  " +token);
            return token;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+accessToken)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String string =response.body().string();
                GithubUser githubUser = com.alibaba.fastjson.JSON.parseObject(string,GithubUser.class);
                return githubUser;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;


    }

}

package com.ldc.cfx.lovefx.controller;

import com.ldc.cfx.lovefx.dto.AccessTokenDTO;
import com.ldc.cfx.lovefx.dto.GithubUser;
import com.ldc.cfx.lovefx.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Name:
 * Description:
 * Created by itdragon on 2019-12-09
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("github.client.id")
    private String clientId;
    @Value("github.client.redirectUri")
    private String redirectUri;
    @Value("github.client.secret")
    private String secret;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        System.out.println(accessTokenDTO);
        String string = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(string);
        System.out.println(githubUser);

        return "index";
    }
}

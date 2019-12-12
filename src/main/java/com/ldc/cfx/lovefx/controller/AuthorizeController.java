package com.ldc.cfx.lovefx.controller;

import com.ldc.cfx.lovefx.dto.AccessTokenDTO;
import com.ldc.cfx.lovefx.dto.GithubUser;
import com.ldc.cfx.lovefx.mapper.UserMapper;
import com.ldc.cfx.lovefx.model.User;
import com.ldc.cfx.lovefx.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Name:
 * Description:
 * Created by itdragon on 2019-12-09
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    //从对象变量中获取值：#{}
    //从资源文件中获取值：${}
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.redirectUri}")
    private String redirectUri;
    @Value("${github.client.secret}")
    private String secret;
    
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(secret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        System.out.println(accessTokenDTO);
        String string = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(string);
        //判断用户不为空，设置cookie
        if (githubUser != null){
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());

            userMapper.inster(user);

            request.getSession().setAttribute("user",githubUser);
            //redirect 返回的是路径，不是页面
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}

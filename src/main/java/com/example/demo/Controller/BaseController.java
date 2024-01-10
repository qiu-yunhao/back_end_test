package com.example.demo.Controller;

import com.example.demo.Util.RCUtil;
import com.example.demo.Answer.Result;
import com.example.demo.Token.TokenService;


public abstract class BaseController {

    public String withTokenVerity(String token, HandleFunction right) {
        try {
            boolean res = TokenService.verityToken(token);
            if (res) {
                return right.handFunc();
            } else {
                return new Result<>(RCUtil.getTokenErr(), "用户凭证失效，请重新登录").toString();
            }

        } catch (Exception e) {
            return new Result<>(RCUtil.getFailed(), "用户信息更新失败,原因：" + e).toString();
        }

    }

    public interface HandleFunction {
        String handFunc();
    }


}

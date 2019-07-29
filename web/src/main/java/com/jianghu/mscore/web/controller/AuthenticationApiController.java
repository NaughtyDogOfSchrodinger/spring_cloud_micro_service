package com.jianghu.mscore.web.controller;

import com.jianghu.mscore.web.annotation.ApiController;
import com.jianghu.mscore.web.exception.AuthenticationException;
import com.jianghu.mscore.web.service.AuthenticationService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApiController("auth")
public class AuthenticationApiController extends AbstractController {

    @Resource
    private AuthenticationService authenticationService;

    @GetMapping("authentication")
    public Map<String, Object> getAuthentication() {
        try {
            HashMap authentication = authenticationService.getAuthenticationCached();
            return success("success", authentication);
        } catch (Exception e) {
            Map<String, Object> result = exceptionHandler(e);
            if (e instanceof AuthenticationException) {
                result.put("status", 2);
            }
            return result;
        }
    }

    @PostMapping("authentication")
    public Map<String, Object> postAuthentication(@RequestBody Object authentication) {
        try {
            return success("创建认证信息成功", authenticationService.postAuthentication(authentication));
        } catch (Exception e) {
            return exceptionHandler(e);
        }
    }

    @DeleteMapping("authentication")
    public Map<String, Object> deleteAuthentication(@RequestBody List<String> ssids) {
        try {
            authenticationService.deleteAuthentication(ssids);
            return success("删除认证信息成功");
        } catch (Exception e) {
            return exceptionHandler(e);
        }
    }

}

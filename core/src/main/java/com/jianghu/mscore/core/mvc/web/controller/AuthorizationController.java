package com.jianghu.mscore.core.mvc.web.controller;

import com.jianghu.mscore.core.annotation.MspController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@MspController("authority")
public class AuthorizationController extends AbstractController{

    @Value("${authority.controller.packageName}")
    private String packageName;

    @GetMapping(value = "generate")
    @ApiOperation(value = "权限生成")
    public Map<String, Object> authorityGenerate() {
        try {
            return success("查询成功", generateAuthority(packageName));
        } catch (Exception e) {
            return exceptionHandler(e);
        }
    }
}

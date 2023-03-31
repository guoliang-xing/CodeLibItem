package com.xgl.test.spring.interceptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("interceptor")
public class InterceptorController {

    @RequestMapping("test")
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response){
        return "Hello , This is a interceptor test ！";
    }
}

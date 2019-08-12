package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class HelloController {

    @RequestMapping("/hello")
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        //Spring MVC 通过 ModelAndView 对象把模型和视图结合在一起
        ModelAndView mav = new ModelAndView("index.jsp");
        mav.addObject("message", "Hello Spring MVC");

        return mav;
    }

    @RequestMapping("/param")
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException{

        response.setContentType("text/html;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        PrintWriter out = response.getWriter();

        if(username.equals("admin")&&password.equals("123456")){
            out.println("<p>登录成功！"+"</p>");
        }
        else{
            out.println("<p>用户名或密码错误！"+"</p>");
        }
        out.flush();
        out.close();
    }

}



/**
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest,
                                      HttpServletResponse httpServletResponse) throws Exception {

        //Spring MVC 通过 ModelAndView 对象把模型和视图结合在一起
        ModelAndView mav = new ModelAndView("index.jsp");
        mav.addObject("message", "Hello Spring MVC");

        return mav;
    }
}
**/
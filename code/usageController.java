package controllers;

import hb.fwu.SecurityUtility;
import nws.config.AppContext;
import nws.utilities.enums.PAGES;
import nws.entities.MemberRules;
import nws.entities.Members;
import nws.services.MemberRulesService;
import nws.services.MemberService;
import nws.services.NewsService;
import nws.utilities.enums.UserRules;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Scope(value = "request")
public class MemberController {
@RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Members member) throws Exception {
        
        try {
            //get captcha from session
            String captcha = (String) request.getSession().getAttribute("captcha");
            //get captcha from user input
            String code = (String) request.getParameter("code");
            
            //check for not null
            if (captcha != null && code != null) {
                //reverse input captcha code because of persian input
                String codeR = new StringBuilder(code).reverse().toString();
                if (!captcha.equals(code) && !captcha.equals(codeR)) {
                    request.setAttribute("ALERT", "کد امنیتی اشتباه است");
                    return "Login";
                }
            }
            else{
                //do somthing else
            }
        } catch (Exception ex) {
            request.setAttribute("ALERT", "خطایی هنگام ورود شما رخ داده است");
            return "Home";
        }
    }
}

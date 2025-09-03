/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.healthelink.errorHandling;

import com.ut.healthelink.model.mailMessage;
import com.ut.healthelink.service.emailMessageManager;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author chadmccue
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	
    @Resource(name = "myProps")
    private Properties myProps;
	
    @Autowired
    private emailMessageManager emailMessageManager;
    
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(HttpSession session, Exception e, HttpServletRequest request) throws Exception {
       
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/exception");
        try {
            mailMessage messageDetails = new mailMessage();

            messageDetails.settoEmailAddress(myProps.getProperty("admin.email"));
            messageDetails.setfromEmailAddress("support@health-e-link.net");
            messageDetails.setmessageSubject("Exception Error "  + " " + myProps.getProperty("server.identity"));

            StringBuilder sb = new StringBuilder();

            //we log page with error and ip of remote client if possible
            try {
                if (request.getHeader("HTTP_X_FORWARDED_FOR") != null) {
                    sb.append("HTTP_X_FORWARDED_FOR: ")
                    .append(request.getHeader("HTTP_X_FORWARDED_FOR"))
                    .append("<br/>");      
                }
                sb.append("Remote Address: ")
                .append(request.getRemoteAddr())
                .append("<br/>")
                .append("Web Page: ")
                .append(request.getRequestURL())
                .append("<br/>"); 
            } 
            catch (Exception ex) {
                ex.printStackTrace();
            }
        
            sb.append("Error: ")
            .append(e)
            .append("<br /><br />")
            .append("Time: ")
            .append(new Date())
            .append("<br /><br />")
            .append("Message: ")
            .append(e.getMessage())
            .append("<br /><br />")
            .append("Stack Trace: ")
            .append(Arrays.toString(e.getStackTrace()));

            messageDetails.setmessageBody(sb.toString());
            emailMessageManager.sendEmail(messageDetails); 
        } 
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.toString() + " error at exception");
        }
        
        return mav;
    }
}

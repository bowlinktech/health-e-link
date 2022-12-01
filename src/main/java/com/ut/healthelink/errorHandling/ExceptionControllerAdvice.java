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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    public ModelAndView exception(HttpSession session, Exception e, HttpServletRequest request, 
    		Authentication authentication) throws Exception {
       
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
        		sb.append("HTTP_X_FORWARDED_FOR: " + request.getHeader("HTTP_X_FORWARDED_FOR") + "<br/>");      
        	}
        	sb.append("Remote Address: " + request.getRemoteAddr() + "<br/>"); 
        	sb.append("Web Page: " + request.getRequestURL() + "<br/>"); 
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
       
        sb.append("Error: "+ e);
        sb.append("<br /><br />");
        sb.append("Time: " + new Date());
        sb.append("<br /><br />");
        sb.append("Message: " + e.getMessage());
        sb.append("<br /><br />");
        sb.append("Stack Trace: " + Arrays.toString(e.getStackTrace()));
        
        messageDetails.setmessageBody(sb.toString());
        emailMessageManager.sendEmail(messageDetails); 
        /*mav.addObject("messageBody",sb.toString());*/
        } catch (Exception ex) {
        	ex.printStackTrace();
        	System.err.println(ex.toString() + " error at exception");
        }
        
        return mav;
    }
}

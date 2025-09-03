/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ut.healthelink.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author chadmccue
 */
@Controller
@RequestMapping("/support-services")
public class solutionsController {
    
    /**
     * The '' request will display the solutions overview page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView consultingservices() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/solutions/services");
        mav.addObject("pageName","services");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Professional Services");
        mav.addObject("pageTitle","Support Services");
        mav.addObject("pageDescription","Health-e-link staff collaborates with our customers to understand partnerships within your network of care, the level of maturity of your collaborative business models, your vision for integrated care models and your current and planned utilization of technology. Working with you, we use this information to develop strategies for implementing transformative care delivery and program management models enabled by our technology solutions.");
       
        return mav;
    }
    
    /**
     * The '/case-studies' request will display the case studies page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/case-studies", method = RequestMethod.GET)
    public ModelAndView healthedata() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/solutions/casestudies");
        mav.addObject("pageName","services");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Case Studies");
        mav.addObject("pageTitle","Case Studies");
        mav.addObject("pageDescription","Health-e-link staff collaborates with our customers to understand partnerships within your network of care, the level of maturity of your collaborative business models, your vision for integrated care models and your current and planned utilization of technology. Working with you, we use this information to develop strategies for implementing transformative care delivery and program management models enabled by our technology solutions.");
       
        return mav;
    }
}
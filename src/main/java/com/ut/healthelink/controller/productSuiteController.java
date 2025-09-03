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
@RequestMapping("/product-suite")
public class productSuiteController {
    
    /**
     * The '' request will display the product suite information page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView productSuite() throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/productSuite/careConnector");
        mav.addObject("pageName","careConnector");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Care Connector");
        mav.addObject("pageTitle","Care Connector");
        mav.addObject("pageDescription","Health-e-link's care connector system connects healthcare providers with their community-based partners and supports fully integrated eReferral communications regardless of the technology-based capabilities of the eReferral partners. Using Health-e-link's care connector system, all referral types with all collaborating partners are managed from a single location. Health-e-Link's care connector system spans the virtual boundaries between healthcare partners in your community.");
        
        return mav;
    }
    
    /**
     * The '/universal-hie' request will display the Universal HIE information page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/universal-hie", method = RequestMethod.GET)
    public ModelAndView universalhie() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/productSuite/universal-hie");
        mav.addObject("pageName","universal-hie");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Universal HIE");
        mav.addObject("pageTitle","Universal HIE");
        mav.addObject("pageDescription","The Health-e-Link Universal HIE solution is the one 'flexible' health information exchange network that allows healthcare partners in your community to share patient information even when their technology-based systems are incompatible. The Universal HIE allows you to define individual partnerships on the network and the technical specifications that support data exchange for each partnership.");
        
        return mav;
    }
    
    /**
     * The '/clinical-data-warehouse' request will display the Clinical Data Warehouse information page.
     */
    @RequestMapping(value = "/clinical-data-warehouse", method = RequestMethod.GET)
    public ModelAndView clinicaldatawarehouse() throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/productSuite/clinical-data-warehouse");
        mav.addObject("pageName","clinical-data-warehouse");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Universal HIE");
        mav.addObject("pageTitle","Clinical Data Warehouse");
        mav.addObject("pageDescription","Health-e-link's Clinical Data Warehouse is a highly configurable and flexible data warehouse and program registry for collaborating healthcare professionals. Health-e-link serves as a single platform for managing data collection, data access, data management, data analysis and reporting needs across the healthcare community.");
        
        return mav;
    }
    
    /**
     * The '/careConnector' request will display the care connect information page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/careConnector", method = RequestMethod.GET)
    public ModelAndView careConnector() throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/productSuite/careConnector");
        mav.addObject("pageName","careConnector");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Care Connector");
        mav.addObject("pageTitle","Care Connector");
        mav.addObject("pageDescription","Health-e-link's care connector system connects healthcare providers with their community-based partners and supports fully integrated eReferral communications regardless of the technology-based capabilities of the eReferral partners. Using Health-e-link's care connector system, all referral types with all collaborating partners are managed from a single location. Health-e-Link's care connector system spans the virtual boundaries between healthcare partners in your community.");
        
        return mav;
    }
    
    /**
     * The '/doc-u-link' request will display the DOC-u-Link information page.
     */
    @RequestMapping(value = "/doc-u-link", method = RequestMethod.GET)
    public ModelAndView doculink() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/productSuite/doc-u-link");
        mav.addObject("pageName","productSuite");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - DOC-u-Link");
        mav.addObject("pageTitle","DOC-u-Link");
        mav.addObject("pageDescription","");
        
        return mav;
    }
}

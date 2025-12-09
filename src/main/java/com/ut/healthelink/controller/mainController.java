package com.ut.healthelink.controller;

import com.ut.healthelink.model.GoogleResponse;
import com.ut.healthelink.model.mailMessage;
import com.ut.healthelink.service.emailMessageManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * The mainController class will handle all URL requests that fall outside of specific user or admin controllers
 *
 * eg. login, logout, about, etc
 *
 * @author chadmccue
 *
 */
@Controller
public class mainController {

    @Autowired
    private emailMessageManager emailMessageManager;
    
    @Resource(name = "myProps")
    private Properties myProps;
    
    @RequestMapping(value = "/files/{file_name}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) throws FileNotFoundException, Exception {
        
        if(!fileName.contains(".docx")) {
            response.sendRedirect("/about");
        }
        else {
            try {
	
                File file = ResourceUtils.getFile("classpath:files/"+fileName+".docx");

                // get your file as InputStream
                InputStream is = new FileInputStream(file);
                // copy it to response's OutputStream
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
             } 
            catch (IOException ex) {
               throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }
    
    /**
     * The '/' request will be the default request of the translator. The request will serve up the home page of the translator.
     *
     * @return	the home page view
     * @throws Exception
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView welcome() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");
        mav.addObject("pageName","home");
        mav.addObject("pageId","home");
        mav.addObject("pageSection","home");
        mav.addObject("pageTitle","Data exchange software connecting the healthcare community");
        mav.addObject("pageDescription","Promoting Better Health and Health Care One Community at a Time. Our products and services enable health information exchange between groups of health care providers, community partners, as well as federal, state and local health agencies.");
        return mav;
    }
    
    /**
     * The '/' head request 
     * @return	the login page
     * @throws Exception
     */
    @RequestMapping(value = "/", method = {RequestMethod.HEAD})
    public ModelAndView headRequest() throws Exception { 
        ModelAndView mav = new ModelAndView(new RedirectView("/home"));
        return mav;   
    }
    

    /**
     * The '/about' GET request will display the about page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView aboutPage() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/about");
        mav.addObject("pageName","about");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - About Us");
        mav.addObject("pageTitle","About Health-e-Link");
        mav.addObject("pageDescription","With a foundation in community-based healthcare and public health programs, Health-e-link was established to provide cost-effective solutions for managing healthcare information for community-based organizations and public health agencies. Historically, Health-e-link has focused specifically on helping our clients meet the data management needs of federal and state funded healthcare programs.");
        
        return mav;
    }
    
    /**
     * The '/about/Network-Capabilities' GET request will display the Network Capabilities  page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/about/network-capabilities", method = RequestMethod.GET)
    public ModelAndView networkcapabilitiesPage() throws Exception {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/networkcapabilities");
        mav.addObject("pageName","networkcapabilities");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Network Capabilities");
        mav.addObject("pageTitle","Network Capabilities");
        mav.addObject("pageDescription","Health-e-link's technology-based solutions including eReferral, Universal HIE and Clinical Data Warehouse. Health-e-link solutions are hosted within our secure data center and are available for customer use on an ongoing basis. Clients can be configured and operational on Health-e-link within a matter of days. Licensed software may be installed, configured and operated by your IT staff with Health-e-link resources available for technical support as required. Health-e-link solutions are developed on Java and Microsoft SQL Server platforms.");
        
        return mav;
    }
    
    /**
     * The '/privacy' GEt request will display the privacy page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/privacy", method = RequestMethod.GET)
    public ModelAndView privacyPage() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/privacy");
        mav.addObject("pageName","403");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Privacy");
        mav.addObject("pageTitle","Privacy");
        mav.addObject("pageDescription","");
        return mav;
    }

    /**
     * The '/contact' GEt request will display the contact page.
     */
    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public ModelAndView contactPage() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("informationPages/contact");
        mav.addObject("pageName","contact");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Contact Us");
        mav.addObject("pageTitle","Contact Us");
        mav.addObject("pageDescription","Health-e-link's staff would be happy to contact you and discuss how our technology solutions and support services may help you create expanded service delivery and patient care models, improve your ability manage your network of collaborating healthcare professionals, and track program and population-based health data. Please complete the form below, provide any information you feel is relevant and we'll be in touch soon!");

        return mav;
    }
    
    /**
     * The '/contact' POST request will display the contact page.
     * @param name
     * @param company
     * @param address
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param ext
     * @param fax
     * @param email
     * @param interestedIn
     * @param comments
     * @param request
     * @return 
     * @throws java.lang.Exception 
     */
    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public ModelAndView contactPageSend(@RequestParam String name, @RequestParam String company, @RequestParam String address, @RequestParam String city, 
        @RequestParam String state, @RequestParam String zip, @RequestParam String phone, @RequestParam String ext, @RequestParam String fax, @RequestParam String email, 
        @RequestParam(value="interestedIn", required = false, defaultValue = "") String interestedIn, 
        @RequestParam(value="comments", required = false, defaultValue = "") String comments, HttpServletRequest request) throws Exception {
        
      
	String response = request.getParameter("g-recaptcha-response");
    	String action = request.getParameter("action");
	
	String reCaptchaKey = myProps.getProperty("recaptcha.key");
	
    	URI verifyUri = URI.create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s",reCaptchaKey, response));
	
	RestTemplate restTemplate = new RestTemplate();
	
	GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
	
	ModelAndView mav = new ModelAndView();
	mav.setViewName("informationPages/contact");
        mav.addObject("pageName","contact");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Contact Us");
        mav.addObject("pageTitle","Contact Us");
        mav.addObject("pageDescription","Health-e-link's staff would be happy to contact you and discuss how our technology solutions and support services may help you create expanded service delivery and patient care models, improve your ability manage your network of collaborating healthcare professionals, and track program and population-based health data. Please complete the form below, provide any information you feel is relevant and we'll be in touch soon!");

	if(!googleResponse.isSuccess() || !googleResponse.getAction().equals(action) || googleResponse.getScore() < 0.5) {
	    //messageDetails.settoEmailAddress("cmccue@health-e-link.net");
	    //messageDetails.setmessageSubject("Captcha Error - Health-e-Link Contact Form");
	    
	    mav.addObject("error","Invalid Captcha!");
	}
	else {
            mailMessage messageDetails = new mailMessage();
            
            messageDetails.setfromEmailAddress("support@health-e-link.net");
	    messageDetails.settoEmailAddress("information@health-e-link.net");
	    messageDetails.setmessageSubject("Health-e-Link Contact Form Submission");
	    
	    mav.addObject("sent","sent");
            
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(name);
            sb.append("<br /><br />");
            sb.append("Company / Organization: ").append(company);
            sb.append("<br /><br />");
            sb.append("Address: ").append(address);
            sb.append("<br /><br />");
            sb.append("City: ").append(city);
            sb.append("<br /><br />");
            sb.append("State: ").append(state);
            sb.append("<br /><br />");
            sb.append("Zip: ").append(zip);
            sb.append("<br /><br />");
            sb.append("Phone: ").append(phone);
            sb.append("<br /><br />");
            sb.append("Ext: ").append(ext);
            sb.append("<br /><br />");
            sb.append("Fax: ").append(fax);
            sb.append("<br /><br />");
            sb.append("Email: ").append(email);
            sb.append("<br /><br />");
            sb.append("Interested In: ").append(interestedIn);
            sb.append("<br /><br />");
            sb.append("Comments: ").append(comments);
            sb.append("<br /><br />");

            messageDetails.setmessageBody(sb.toString());

            if(!"".equals(interestedIn) && !"".equals(name) && !"".equals(email) && !"".equals(company)) {
               emailMessageManager.sendEmail(messageDetails); 
            }
	}
	
        return mav;
    }
    
    /**
     * The '/partners' GEt request will display the partner request page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/partners", method = RequestMethod.GET)
    public ModelAndView partnersPage() throws Exception {
        ModelAndView mav = new ModelAndView(new RedirectView("/contact"));
        return mav;   
    }
    
    /**
     * The '/partners' POST request will submit the parner request form.
     */
    @RequestMapping(value = "/partners", method = RequestMethod.POST)
    public ModelAndView partnerPageSend(@RequestParam String name, @RequestParam String title, @RequestParam String company, @RequestParam String URL, @RequestParam String address, @RequestParam String city, 
            @RequestParam String state, @RequestParam String zip, @RequestParam String phone, @RequestParam String ext, @RequestParam String fax, @RequestParam String email, 
            @RequestParam String comments) throws Exception {
        
       StringBuilder sb = new StringBuilder();
       
       mailMessage messageDetails = new mailMessage();
        
       messageDetails.settoEmailAddress("information@health-e-link.net");
       messageDetails.setfromEmailAddress("support@health-e-link.net");
       messageDetails.setmessageSubject("Health-e-Link Partner Request Form Submission");
       
        sb.append("Name: "+ name);
        sb.append("<br /><br />");
        sb.append("Title: "+ title);
        sb.append("<br /><br />");
        sb.append("Company / Organization: " + company);
        sb.append("<br /><br />");
        sb.append("URL: "+ URL);
        sb.append("<br /><br />");
        sb.append("Address: " + address);
        sb.append("<br /><br />");
        sb.append("City: " + city);
        sb.append("<br /><br />");
        sb.append("State: " + state);
        sb.append("<br /><br />");
        sb.append("Zip: " + zip);
        sb.append("<br /><br />");
        sb.append("Phone: " + phone);
        sb.append("<br /><br />");
        sb.append("Ext: " + ext);
        sb.append("<br /><br />");
        sb.append("Fax: " + fax);
        sb.append("<br /><br />");
        sb.append("Email: " + email);
        sb.append("<br /><br />");
        sb.append("Comments: " + comments);
        sb.append("<br /><br />");
        
        messageDetails.setmessageBody(sb.toString());
        
        emailMessageManager.sendEmail(messageDetails); 

        ModelAndView mav = new ModelAndView();
        
        mav.setViewName("/partners");
        mav.addObject("pageTitle", "Partners");
        mav.addObject("sent","sent");
        return mav;
    }

    /**
     * The '/emailSignUp.do' function will save the email form.
     * 
     * @param emailAddress	The email address being signed up
     * @param unsubscribe
     * @return 
     *
     * @throws Exception
     */
    @RequestMapping(value = "/emailSignUp.do", method = RequestMethod.POST)
    public @ResponseBody Integer emailSignUp(@RequestParam(value = "emailAddress", required = true) String emailAddress, @RequestParam(value = "unsubscribe", required = true) boolean unsubscribe) throws Exception {
         
        return 1;
    }
    
    /**
     * The '/settings' request will be the default request of the translator. The request will serve up the home page of the translator.
     *
     * @return	the home page view
     * @throws Exception
     */
    @RequestMapping(value = "/settings", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView settings() throws Exception {
       ModelAndView mav = new ModelAndView();
       mav.setViewName("/home");
       return mav;
    }
    
    
    @RequestMapping(value = "/netbeans-tomcat-status-test", method = RequestMethod.HEAD)
    public ResponseEntity<Void> handleStatusTest() {
        return ResponseEntity.ok().build();
    }
    
    /**
     * The '/403' GEt request will display the contact page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = {"/403.html", "/403","/error/403.html","/error/403"}, method = RequestMethod.GET)
    public ModelAndView missingpage() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/403");
        mav.addObject("pageName","403");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - No Permission");
        mav.addObject("pageTitle","403 Access Denied");
        mav.addObject("pageDescription","");

        return mav;
    }
    
    /**
     * The '/404' GEt request will display the contact page.
     * @return 
     * @throws java.lang.Exception
     */
    @RequestMapping(value = {"/404.html", "/404","/error/404.html","/error/404"}, method = RequestMethod.GET)
    public ModelAndView errorpage() throws Exception {
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error/404");
        mav.addObject("pageName","contact");
        mav.addObject("pageId","");
        mav.addObject("pageSection","");
        mav.addObject("pageTabTitle","Health-e-Link - Page Not Found");
        mav.addObject("pageTitle","404 Page Not Found");
        mav.addObject("pageDescription","");

        return mav;
    }
}

package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.emailService.SendEmailService;
import com.example.demo.entity.BloodGroupDetails;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.LoggedInUser;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.AdminService;
import com.example.demo.service.DonorDetailsService;
import com.example.demo.service.InventoryService;
import com.example.demo.service.PatientDetailsService;

import com.example.demo.service.RegistrationDetailsService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	
	@Autowired
	private AdminService service;
	
	@Autowired 
	private InventoryService inventoryService;
	
	@Autowired
	private DonorDetailsService donorDetailsService;
	
	@Autowired
	private PatientDetailsService patientDetailsService;
	
	@Autowired
	private RegistrationDetailsService registrationDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SendEmailService emailService;

	
//	@GetMapping("/verifyAdminLogin")
//    public String verifyAdminLogin(@ModelAttribute("received") RegistrationDetails received, 
//                                   HttpSession session, Model model) {
//		System.out.println(received.getEmail());
//        int status = service.verifyLogin(received);
//
//        if (status == 1) {
//            // If login is successful, set the email as a session attribute
//            session.setAttribute("loggedInUserEmail", received.getEmail());
//            //totaldonation
//            List<Integer> countDonor = countDonorsWithStatusOne();
//            int a=countDonor.get(0);
//            System.out.println(a);
//            
//            model.addAttribute("DonorCount", a);
//            
//            
//            //totalrequest
//            int b= countBloodRequest();
//            System.out.println(b);
//            model.addAttribute("requestCount", b);
//            
//            //bloodcount
//           
//            List<BloodGroupDetails> bloodCount = getDetail();
//            model.addAttribute("bloodGroupDetailsList", bloodCount);
//            
//
//            // Redirect to the admin home page
//            return "adminHome";
//        } else if (status == 0) {
//            model.addAttribute("invalidMail", "This email is not admin");
//            return "adminLogin";
//        } else if (status == -1) {
//            model.addAttribute("error", "Invalid username or password");
//        }
//        
//        
//         
//        // If login fails, stay on the login page
//        return "adminLogin";
//    }
	
	
	@GetMapping("/viewProfileDetail")
	public String viewProfileDetail(HttpSession session, Model model) {
	    // Retrieve the email from the session
	   // String adminEmail = (String) session.getAttribute("loggedInUserEmail");
	    CustomUserDetail user = (CustomUserDetail) session.getAttribute("user");
	    String adminEmail = user.getEmail();
	    
	    System.out.println(adminEmail);

	    if (adminEmail == null) {
	        // Handle the case where the admin is not logged in
	        return "redirect:/adminLogin";  // Redirect to the admin login page or handle appropriately
	    }

	    // Add logic to fetch user details based on the admin's email
	    List<RegistrationDetails> userDetails = registrationDetailsService.getRegistrationDetailsByEmail(adminEmail);
	    for(RegistrationDetails a:userDetails) {
	    	System.out.println(a.getBloodGroup());
	    	System.out.println(a.getCity());
	    	System.out.println(a.getFirstname());
	    }

	    // Add the user details to the model
	    model.addAttribute("user", userDetails);
	    model.addAttribute("adminUpdate", "admin detail updated");
	    

	    // Return the Thymeleaf template name
	    return "adminProfile";
	}
	
	@GetMapping("/getInventory")
	public String getDetails(Model model) {
	    List<BloodGroupDetails> bloodGroupDetailsList = new ArrayList<>();

	    bloodGroupDetailsList.add(new BloodGroupDetails("A+", inventoryService.getBloodCount("A+")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("A-", inventoryService.getBloodCount("A-")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("B+", inventoryService.getBloodCount("B+")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("B-", inventoryService.getBloodCount("B-")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("AB+", inventoryService.getBloodCount("AB+")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("AB-", inventoryService.getBloodCount("AB-")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("O+", inventoryService.getBloodCount("O+")));
	    bloodGroupDetailsList.add(new BloodGroupDetails("O-", inventoryService.getBloodCount("O-")));
	    
	    
	    System.out.println(bloodGroupDetailsList.get(0));
	    System.out.println(bloodGroupDetailsList.get(1));
	    System.out.println(bloodGroupDetailsList.get(3));

	    model.addAttribute("bloodGroupDetailsList", bloodGroupDetailsList);

	    return "bloodInventoryTable"; // Replace with the actual Thymeleaf template name
	}
	
  
  @GetMapping("/getInventoryDetailsByBloodGroup/{BloodGroup}")
	public List<Inventory> findDetailsByBloodGroup(@PathVariable("BloodGroup") String bloodGroup) {
		return inventoryService.findByBloodGroup(bloodGroup);
		
	}
  
  
  @GetMapping("/countStatus1")
  public List<Integer> countDonorsWithStatusOne() {
      List<DonorDetails> saved =donorDetailsService.getTotalDonationCount();
      int count = saved.size();
      List<Integer> countDonor = new ArrayList<>();
      countDonor.add(count);
      System.out.println(countDonor);
      return countDonor;
     
      }
  
//  @GetMapping("/getCountByBloodGroup/{bloodGroup}")
//  public Map<String, Long> getCountByBloodGroup(@PathVariable String bloodGroup) {
//      return inventoryService.getCountByBloodGroup(bloodGroup);
//  }
 
  @GetMapping("/getDonorDetails")
  public List<DonorDetails> getDonorDetails(){
	  return donorDetailsService.getDonorDetails();
  }
  
  
  @GetMapping("/getDonorDetailsByEmial/{email}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody 
  public String getDonorDetailsByEmail(@PathVariable("email") String email, Model model){
	  List<DonorDetails> donorSearch =  donorDetailsService.findByemail(email);
	  model.addAttribute("search", donorSearch);
	  //////////////////////////////////////////////////
	  return "bloodDonationView";
  }
  

  @GetMapping("/getInventoryDetails")
	public String getInventoryDetails(Model model) {
	  List<Inventory> details = new ArrayList<>();
	  
		details= inventoryService.getInventoryDetails();
//		System.out.println(details.get(0));
		model.addAttribute("blood", details);
		
		return "bloodExpiry";
		
	}
	
  @GetMapping("/clearExpiry")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
  public List<Inventory> checkBlood() {
	  System.out.println("inside clear expiry");
	  List<Inventory> donors = inventoryService.getInventoryDetails();
	  return inventoryService.checkForOldBloodSamples(donors);
  }
  
  
   	
  @GetMapping("/eligibleToDonate")
  @ResponseStatus(HttpStatus.OK)
	@ResponseBody
  public List<DonorDetails> checkEligibility(){
  List<DonorDetails> donors = donorDetailsService.getDonorDetails();
  System.out.println("ELIBLEEEEEEE");
  List<DonorDetails> data = new ArrayList<>();
  
  data = donorDetailsService.checkEligibility(donors);
  
  
  for(DonorDetails ya:data) {
	  System.out.println(ya);
  }
// System.out.println(data.get(0));
// System.out.println(data.get(1));
 return data;
       
  }
  
  @PostMapping("/updateDetails")//1
	public String updateUserProfile(@ModelAttribute("detail") RegistrationDetails detail, Model model)
	{
	  System.out.println(detail.getEmail());
	  service.updateUserProfile(detail);
	  //model.addAttribute("adminUpdate", "admin detail updated");
//		return new ResponseEntity<RegistrationDetails>(detail, HttpStatus.OK);
	  return "redirect:/admin/viewProfileDetail";
	}

  
  	@GetMapping("/viewDonationHistory")//1
  	public String viewDonationHistory(Model model) {
  		List<DonorDetails> donorData = new ArrayList<>();
  		donorData = service.getDonationHistory();
  		model.addAttribute("donor", donorData);
  		return "bloodDonationView";
  		
  	}
  	
  	@GetMapping("/viewDonationRequests")//1
  	public String viewDonationRequests(Model model) {
  		List<DonorDetails> donorData = new ArrayList<>();
  		donorData = service.getDonationRequests();
//  		System.out.println(donorData.get(0));
  		//System.out.println(donorData.get(1));
  		model.addAttribute("donor", donorData);
  		return "bloodDonationRequests";
  		
  	}
  	
  	@GetMapping("/viewBloodRequestHistory")
  	public String viewBloodRequestHistory(Model model) {
  		List<PatientDetails> requestData= new ArrayList<>();
  		requestData = patientDetailsService.getBloodRequestsHistory();
//  		System.out.println(requestData.get(0));
  		model.addAttribute("recieve", requestData);
  		return "bloodRequestsViews";
  	}
  	
  	@GetMapping("viewBloodRequest")
  	public String viewBloodRequest(Model model) {
  		List<PatientDetails> requestData = new ArrayList<>();
  		requestData= service.getBloodRequest();
//  		System.out.println(requestData.get(0));
  		model.addAttribute("recieve", requestData);
  		return "bloodRequestAdmin";
  	}
  	
  	@GetMapping("/viewUserDetails")
  	public String viewUserDetails(Model model) {
  		List<DonorDetails> data = new ArrayList<>();
  		//data = registrationDetailsService.getRegistrationDetails();
  		data = donorDetailsService.getDonorsDetails();
  		model.addAttribute("user", data);
  		
  		
  		return "userDetails";
  	}
  	

  	@PostMapping("/acceptDonationRequest/{email}")
  	@ResponseStatus(HttpStatus.OK)
  	@ResponseBody
  	public void acceptDonationRequest(@PathVariable("email") String email, Model model) {
  		
  		System.out.println("INside accept" + email);
  	    String result = service.acceptDonationRequest(email);
  	    
//  	    if ("User already existing".equals(result)) {
//  	        model.addAttribute("message", result);
//  	    }

  	    System.out.println("Request mapped for email: " + email);
  	  emailService.sendEmail(email,"HemoHarbor ", "you Blood Donation Request is Accepted ");
  	}

	
	
	
	@GetMapping("/viewBloodRequests")//1
	public List<PatientDetails> viewBloodRequest(){
		return  service.viewBloodRequest();
	}
	
	
	@PostMapping("/acceptBloodRequest/{email}")
  	@ResponseStatus(HttpStatus.OK)
  	@ResponseBody
	public void acceptBloodRequest(@PathVariable("email") String email, Model model) {
		System.out.println("INside accept" + email);
		String result = service.acceptBloodRequest(email);
		
		
		   System.out.println("Request mapped for email: " + email);
		   emailService.sendEmail(email,"HemoHarbor ", "you Blood Request is Accepted ");
	}
	

	
	
	 @PostMapping("/rejectBloodDonationRequest/{email}")
	 @ResponseStatus(HttpStatus.OK)
	 @ResponseBody
	 public void rejectDonationRequest(@PathVariable("email") String email, Model model) {
		 System.out.println("INside reject" + email);
		 service.rejectDonationRequest(email);
		 
		 
		 System.out.println("Request mapped for email: " + email);
		 emailService.sendEmail(email,"HemoHarbor ", "you Blood Donation Request is Rejected ");
		
	}
	
	 @PostMapping("/rejectBloodRequest/{email}")
	 @ResponseStatus(HttpStatus.OK)
	 @ResponseBody
	public void rejectBloodRequest(@PathVariable("email") String email, Model model) {
		 System.out.println("INside reject" + email);
		service.rejectBloodRequest(email);
		
		
		 System.out.println("Request mapped for email: " + email);
		 emailService.sendEmail(email,"HemoHarbor ", "you Blood Request is Rejected ");
		
	}
	
}
  
  
  


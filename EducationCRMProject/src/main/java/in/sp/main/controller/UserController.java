
package in.sp.main.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import in.sp.main.entities.Course;
import in.sp.main.entities.User;
import in.sp.main.repositories.UserRepository;
import in.sp.main.services.CourseService;
import in.sp.main.services.UserService;
import jakarta.validation.Valid;

@Controller
@SessionAttributes("sessionUser")
public class UserController
{
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping({"/", "/index"})
	public String openIndexPage(Model model)
	{
		List<Course> coursesList = courseService.getAllCourseDetails();
		model.addAttribute("coursesList", coursesList);
		
		return "index";
	}
	
	//-----------register starts---------------------------------
	@GetMapping("/register")
	public String openRegisterPage(Model model)
	{
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/regForm")
	public String handleRegForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)
	{
		if(result.hasErrors())
		{
			return "register";
		}
		else
		{
			try
			{
				userService.registerUserService(user);
				
				model.addAttribute("successMsg", "Registered Successfully");
				return "register";
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
				model.addAttribute("errorMsg", "Registration Failed");
				return "error";
			}
		}
	}
	//-----------register finished---------------------------------
	
	
	//-----------------------login starts---------------------------------
	@GetMapping("/login")
	public String openLoginPage(Model model)
	{
		model.addAttribute("user", new User());
		return "login";
	}
	@PostMapping("/loginForm")
	public String handleLoginForm(@ModelAttribute("user") User user, Model model)
	{
		boolean isAuthenticated = userService.loginUserService(user.getEmail(), user.getPassword());
		if(isAuthenticated)
		{
			User authenticatedUser = userRepository.findByEmail(user.getEmail());
			model.addAttribute("sessionUser", authenticatedUser);
			
			return "user-profile";
		}
		else
		{
			model.addAttribute("errorMsg", "Incorrect Email id or Password");
			return "login";
		}
	}
	//-----------------------login finished---------------------------------
	
	
	@GetMapping("/logout")
	public String logout(SessionStatus sessionStatus)
	{
		sessionStatus.setComplete();
		return "login";
	}
	
	@GetMapping("/userProfile")
	public String openUserProfile()
	{
		return "user-profile";
	}
	
	@GetMapping("/myCourses")
	public String myCoursesPage()
	{
		return "my-courses";
	}
}
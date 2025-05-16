package in.sp.main.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.User;
import in.sp.main.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	public void registerUserService(User user) {
		userRepository.save(user);

		
	}
	
	public boolean loginUserService(String email,String password) {
	User user=userRepository.findByEmail(email);
	if(user!=null) {
		if(password.equals(user.getPassword())) {
		return true;
		}
	}
	
		return false;
	     }
}

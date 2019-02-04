package co.igorski.centralcommittee;

import co.igorski.centralcommittee.model.Role;
import co.igorski.centralcommittee.model.User;
import co.igorski.centralcommittee.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CentralCommittee {
	private final static Logger LOG = LoggerFactory.getLogger(CentralCommittee.class);
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(CentralCommittee.class, args);
	}

	@PostConstruct
	public void setup() {
        if (userService.getUser("sinatra") != null) {
            return;
        }

		User user = new User();
		user.setName("Frank");
		user.setRole(Role.ADMIN);
		user.setPassword("huckleberryFriend");
		user.setUsername("sinatra");

		user = userService.createUser(user);

		LOG.info("Created new user with id {}", user.getId());
	}
}

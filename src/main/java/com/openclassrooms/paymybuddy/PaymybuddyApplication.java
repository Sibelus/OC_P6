package com.openclassrooms.paymybuddy;

import com.openclassrooms.paymybuddy.model.BankAccount;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.model.User;
import com.openclassrooms.paymybuddy.service.IBankAccountService;
import com.openclassrooms.paymybuddy.service.IConnectionService;
import com.openclassrooms.paymybuddy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class PaymybuddyApplication implements CommandLineRunner {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private IBankAccountService iBankAccountService;

	@Autowired
	private IConnectionService iConnectionService;


	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}


	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Optional<User> user1 = iUserService.findByEmail("mtampion@mail.fr");
		System.out.println(user1.get().getFirstname());


		//iConnectionService.addFriendship(1,3);
		List<Connection> friendshipList = iConnectionService.getFriendshipList(1);
		friendshipList.forEach(connection -> System.out.println(connection.getFriend().getFirstname()));

		user1 = iUserService.findByEmail("mtampion@mail.fr");
		System.out.println(user1.get().getFriendsList());
	}
}

/*
@SpringBootApplication
public class PaymybuddyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymybuddyApplication.class, args);
	}

}*/
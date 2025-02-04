package com.dev.smc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dev.smc.servers.credentials.Credential;
import com.dev.smc.servers.credentials.CredentialUtil;

@SpringBootApplication
public class SmcApplication implements CommandLineRunner {

	@Autowired
	private CredentialUtil credentialUtil;

	public static void main(String[] args) {
		SpringApplication.run(SmcApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Credential credential = new Credential();
		credential.setName("Admin");
		credential.setUsername("admin_user");
		credential.setPassword("mysecurepassword");
		System.out.println("Name: " + credential.getName());
		System.out.println("Username: " + credential.getUsername());
		String encryptedPassword = credentialUtil.encrypt(credential.getPassword());
		System.out.println("Encrypted Password: " + encryptedPassword);
		String decryptedPassword = credentialUtil.decrypt(encryptedPassword);
		System.out.println("Decrypted Password: " + decryptedPassword);
	}
}

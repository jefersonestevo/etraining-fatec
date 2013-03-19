package br.com.etraining.web.security;

import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

public class EtrainingLoginModulo extends UsernamePasswordLoginModule {

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		System.out.println("DENTRO LOGIN MODULE ETRAINING - initialize");

		super.initialize(subject, callbackHandler, sharedState, options);
	}

	public boolean login() throws LoginException {

		System.out.println("DENTRO LOGIN MODULE ETRAINING");

		return super.login();
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		System.out.println("DENTRO LOGIN MODULE ETRAINING - getUsersPassword");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		System.out.println("DENTRO LOGIN MODULE ETRAINING - getRoleSets");
		// TODO Auto-generated method stub
		return null;
	}
}

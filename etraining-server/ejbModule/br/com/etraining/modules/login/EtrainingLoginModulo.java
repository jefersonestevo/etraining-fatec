package br.com.etraining.modules.login;

import java.security.acl.Group;
import java.util.Map;

import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.collections.CollectionUtils;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import br.com.etraining.exception.ETrainingException;
import br.com.etraining.negocio.bo.BOResolver;
import br.com.etraining.negocio.bo.impl.realizarlogin.RealizarLoginBO;
import br.com.etraining.utils.CDIUtils;

public class EtrainingLoginModulo extends UsernamePasswordLoginModule {

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
	}

	public boolean login() throws LoginException {
		return super.login();
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		RespostaRealizarLoginVO resp = getResposta(getUsernameAndPassword()[0],
				getUsernameAndPassword()[1]);

		if (resp == null || resp.getIdAluno() == null)
			return null;
		return resp.getSenha();
	}

	@Override
	protected Group[] getRoleSets() throws LoginException {
		RespostaRealizarLoginVO resp = getResposta(getUsernameAndPassword()[0],
				getUsernameAndPassword()[1]);

		if (resp == null || resp.getIdAluno() == null
				|| CollectionUtils.isEmpty(resp.getListaPermissoes()))
			return null;

		SimpleGroup group = new SimpleGroup("Roles");
		for (String perm : resp.getListaPermissoes()) {
			group.addMember(new SimplePrincipal(perm));
		}
		return new Group[] { group };
	}

	private RespostaRealizarLoginVO getResposta(String matricula, String senha) {
		BOResolver resolver = new BOResolver();
		try {
			CDIUtils.programmaticInjection(BOResolver.class, resolver);
			RealizarLoginVO realizarLogin = new RealizarLoginVO();
			realizarLogin.setNumeroMatricula(matricula);
			realizarLogin.setSenha(senha);

			RealizarLoginBO bo = (RealizarLoginBO) resolver
					.getBOFor(RealizarLoginVO.class);
			return bo.executa(realizarLogin);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (ETrainingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

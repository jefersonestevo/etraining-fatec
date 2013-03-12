package br.com.etraining.test.ejb;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.etraining.client.fachada.ejb.IEtrainingService;
import br.com.etraining.client.vo.impl.programatreinamento.ConsultaProgramaTreinamentoVO;
import br.com.etraining.client.vo.transporte.VORequest;

@WebServlet(value = "/TestEJBServlet")
public class TestEJBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private IEtrainingService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestEJBServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("TESTE EJB SERVLET");

		VORequest req = new VORequest();
		req.setRequest(new ConsultaProgramaTreinamentoVO());
		service.executa(req);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}

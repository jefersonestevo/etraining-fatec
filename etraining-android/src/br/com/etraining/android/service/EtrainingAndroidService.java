package br.com.etraining.android.service;

import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.service.conn.SoapActionEnum;
import br.com.etraining.android.service.conn.SoapConnector;
import br.com.etraining.client.vo.interfaces.IVO;

import com.googlecode.androidannotations.annotations.EBean;

@EBean
public class EtrainingAndroidService {

	public IVO executa(IVO request) throws EtrainingViewException {
		SoapConnector<IVO> reqSoap = new SoapConnector<IVO>(
				SoapActionEnum.EXECUTAR);
		reqSoap.putRequest("request", request);
		reqSoap.execute();
		return reqSoap.getResponse();
	}

}

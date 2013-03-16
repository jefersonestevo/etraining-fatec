package br.com.etraining.android.service;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.service.conn.SoapActionEnum;
import br.com.etraining.android.service.conn.SoapConnector;
import br.com.etraining.client.vo.interfaces.IVO;
import br.com.etraining.client.vo.transporte.CodigoExcecao;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;

@EBean
public class EtrainingAndroidService {

	@SystemService
	ConnectivityManager connectivityManager;

	private void validaConexao() throws EtrainingViewException {
		if (connectivityManager != null) {
			NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
			boolean conectado = (netInfo != null)
					&& (netInfo.isConnectedOrConnecting())
					&& (netInfo.isAvailable());
			if (!conectado) {
				throw new EtrainingViewException(
						CodigoExcecao.ANDROID_APARELHO_SEM_CONEXAO);
			}
		}
	}

	public IVO executa(IVO request) throws EtrainingViewException {
		validaConexao();
		SoapConnector<IVO> reqSoap = new SoapConnector<IVO>(
				SoapActionEnum.EXECUTAR, IVO.class);
		reqSoap.putRequest("request", request);
		reqSoap.execute();
		return reqSoap.getResponse();
	}

}

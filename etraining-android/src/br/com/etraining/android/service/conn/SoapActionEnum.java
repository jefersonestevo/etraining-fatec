package br.com.etraining.android.service.conn;

public enum SoapActionEnum {
	EXECUTAR("executar", "executar"), //
	;

	// TODO - Consertar URL do WS no android
	public static final String URL = "http://10.0.2.2:8080/etraining-server/ETrainingServiceWS?wsdl";
	public static final String NAMESPACE = "ETrainingServiceWS";
	private String soapAction;
	private String metodo;

	private SoapActionEnum(String soapAction, String metodo) {
		this.soapAction = soapAction;
		this.metodo = metodo;
	}

	public String getUrl() {
		return URL;
	}

	public String getNamespace() {
		return NAMESPACE;
	}

	public String getSoapAction() {
		return soapAction;
	}

	public String getMetodo() {
		return metodo;
	}

}

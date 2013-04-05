package br.com.etraining.android.service.conn;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.client.vo.transporte.CodigoExcecao;
import br.com.etraining.client.vo.transporte.impl.VORequestWS;
import br.com.etraining.client.vo.transporte.impl.VOResponseWS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class SoapConnector<T> {

	private Gson gson;
	private SoapActionEnum connectorEnum;
	private SoapSerializationEnvelope envelope;
	private String paramName;
	private VORequestWS request;
	private VOResponseWS response;
	private Class<?> classResponse;
	private Type collectionType;

	public SoapConnector(SoapActionEnum connectorEnum) {
		this.connectorEnum = connectorEnum;
		this.request = new VORequestWS();
	}

	public void putRequest(String nome, Object obj) {
		this.paramName = nome;
		this.request.setRequest(getGson().toJson(obj));
		this.request.setClasse(obj.getClass().getName());
	}

	public void execute() throws EtrainingViewException {
		try {
			SoapObject requestSoap = new SoapObject(
					this.connectorEnum.getNamespace(),
					this.connectorEnum.getMetodo());

			requestSoap.addProperty(paramName, getGson().toJson(request));

			envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(requestSoap);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(
					connectorEnum.getUrl());
			androidHttpTransport.call(connectorEnum.getSoapAction(), envelope);

			response = getGson().fromJson(envelope.getResponse().toString(),
					VOResponseWS.class);
			classResponse = Class.forName(response.getClasse());
		} catch (XmlPullParserException e) {
			Log.e("ERROR", "XmlPullParserException", e);
			throw new EtrainingViewException(
					CodigoExcecao.ANDROID_SEM_CONEXAO_SERVIDOR);
		} catch (SoapFault e) {
			Log.e("ERROR", "SoapFault", e);
			throw new EtrainingViewException(
					CodigoExcecao.ANDROID_SEM_CONEXAO_SERVIDOR);
		} catch (SocketTimeoutException e) {
			Log.e("ERROR", "SocketTimeoutException", e);
			throw new EtrainingViewException(
					CodigoExcecao.ANDROID_TIMEOUT_SERVIDOR);
		} catch (IOException e) {
			Log.e("ERROR", "IOException", e);
			throw new EtrainingViewException(
					CodigoExcecao.ANDROID_ENTRADA_SAIDA_SERVIDOR);
		} catch (ClassNotFoundException e) {
			Log.e("ERROR", "ClassNotFoundException", e);
			throw new EtrainingViewException(
					CodigoExcecao.ANDROID_ENTRADA_SAIDA_SERVIDOR);
		}
	}

	@SuppressWarnings("unchecked")
	public T getResponse() throws EtrainingViewException {
		if (response.getResponse() == null || "".equals(response.getResponse())) {
			return null;
		}
		if (response.getCodigoErro() != null)
			throw new EtrainingViewException(response.getCodigoErro());

		String resp = response.getResponse().toString();
		if (classResponse != null)
			return (T) getGson().fromJson(resp, classResponse);
		else if (collectionType != null) {
			JsonReader reader = new JsonReader(new StringReader(resp));
			reader.setLenient(true);
			return getGson().fromJson(reader, collectionType);
		}
		throw new EtrainingViewException(CodigoExcecao.ERRO_DESCONHECIDO);
	}

	private Gson getGson() {
		if (gson == null) {
			gson = new GsonBuilder().create();
		}
		return gson;
	}
}

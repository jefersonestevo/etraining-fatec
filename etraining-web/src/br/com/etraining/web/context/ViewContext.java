package br.com.etraining.web.context;

import java.util.Map;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import br.com.etraining.web.context.annotation.ViewScope;

public class ViewContext implements Context {
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getScope() {
		return ViewScope.class;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object get(Contextual contextual, CreationalContext creationalContext) {
		Bean bean = (Bean) contextual;
		Map viewMap = getViewMap();
		if (viewMap.containsKey(bean.getName())) {
			return (Object) viewMap.get(bean.getName());
		} else {
			Object t = bean.create(creationalContext);
			viewMap.put(bean.getName(), t);
			return t;
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object get(Contextual contextual) {
		Bean bean = (Bean) contextual;
		Map viewMap = getViewMap();
		if (viewMap.containsKey(bean.getName())) {
			return (Object) viewMap.get(bean.getName());
		} else {
			return null;
		}
	}

	@Override
	public boolean isActive() {
		return true;
	}
	
	@SuppressWarnings("rawtypes")
	private Map getViewMap() {
		FacesContext fctx = FacesContext.getCurrentInstance();
		UIViewRoot viewRoot = fctx.getViewRoot();
		return viewRoot.getViewMap(true);
	}
}

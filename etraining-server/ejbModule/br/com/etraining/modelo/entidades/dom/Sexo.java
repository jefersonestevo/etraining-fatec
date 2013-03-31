package br.com.etraining.modelo.entidades.dom;

public enum Sexo {
	M, //
	F;

	public static Sexo getEnumFor(String index) {
		if (index != null) {
			for (Sexo s : Sexo.values()) {
				if (s.name().equals(index))
					return s;
			}
		}
		return null;
	}

	public static Sexo getEnumFromOrdinal(Integer index) {
		if (index != null) {
			for (Sexo s : Sexo.values()) {
				if (s.ordinal() == index)
					return s;
			}
		}
		return null;
	}
}

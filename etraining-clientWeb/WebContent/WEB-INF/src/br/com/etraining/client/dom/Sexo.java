package br.com.etraining.client.dom;

public enum Sexo {
	M(1), //
	F(2);

	private Integer id;

	private Sexo(Integer id) {
		this.id = id;
	}

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

	public Integer getId() {
		return id;
	}
}

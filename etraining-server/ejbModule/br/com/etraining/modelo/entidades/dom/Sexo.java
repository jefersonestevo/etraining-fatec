<<<<<<< HEAD
package br.com.etraining.modelo.entidades.dom;

public enum Sexo {
	M, //
	F;
	
	public static Sexo getEnumFor(Integer index) {
		if (index != null) {
			for (Sexo s : Sexo.values()) {
				if (s.ordinal() == index)
					return s;
			}
		}
		return null;
	}
}
=======
package br.com.etraining.modelo.entidades.dom;

public enum Sexo {
	M, //
	F;
	
	public static Sexo getEnumFor(Integer index) {
		if (index != null) {
			for (Sexo s : Sexo.values()) {
				if (s.ordinal() == index)
					return s;
			}
		}
		return null;
	}
}
>>>>>>> 3a846b28708962438f8b30ab756e3215a08ab979

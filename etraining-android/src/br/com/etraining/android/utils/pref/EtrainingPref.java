package br.com.etraining.android.utils.pref;

import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref.Scope;

@SharedPref(value = Scope.UNIQUE)
public interface EtrainingPref {

	public long idAluno();

	public long numeroMatricula();

	public long dataSelecionada();

}

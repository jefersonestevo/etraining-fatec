//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package br.com.etraining.android.utils.pref;

import android.content.Context;
import android.content.SharedPreferences;
import com.googlecode.androidannotations.api.sharedpreferences.EditorHelper;
import com.googlecode.androidannotations.api.sharedpreferences.LongPrefEditorField;
import com.googlecode.androidannotations.api.sharedpreferences.LongPrefField;
import com.googlecode.androidannotations.api.sharedpreferences.SharedPreferencesHelper;
import com.googlecode.androidannotations.api.sharedpreferences.StringPrefEditorField;
import com.googlecode.androidannotations.api.sharedpreferences.StringPrefField;

public final class EtrainingPref_
    extends SharedPreferencesHelper
{


    public EtrainingPref_(Context context) {
        super(context.getSharedPreferences("EtrainingPref", 0));
    }

    public EtrainingPref_.EtrainingPrefEditor_ edit() {
        return new EtrainingPref_.EtrainingPrefEditor_(getSharedPreferences());
    }

    public LongPrefField dataSelecionada() {
        return longField("dataSelecionada", 0L);
    }

    public LongPrefField idAluno() {
        return longField("idAluno", 0L);
    }

    public StringPrefField numeroMatricula() {
        return stringField("numeroMatricula", "");
    }

    public final static class EtrainingPrefEditor_
        extends EditorHelper<EtrainingPref_.EtrainingPrefEditor_>
    {


        EtrainingPrefEditor_(SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        public LongPrefEditorField<EtrainingPref_.EtrainingPrefEditor_> dataSelecionada() {
            return longField("dataSelecionada");
        }

        public LongPrefEditorField<EtrainingPref_.EtrainingPrefEditor_> idAluno() {
            return longField("idAluno");
        }

        public StringPrefEditorField<EtrainingPref_.EtrainingPrefEditor_> numeroMatricula() {
            return stringField("numeroMatricula");
        }

    }

}

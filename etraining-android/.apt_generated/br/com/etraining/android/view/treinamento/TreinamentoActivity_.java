//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package br.com.etraining.android.view.treinamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import br.com.etraining.android.R.id;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.service.EtrainingAndroidService_;
import br.com.etraining.android.service.ExercicioHolder_;
import br.com.etraining.android.utils.pref.EtrainingPref_;
import br.com.etraining.client.vo.impl.entidades.ExercicioRealizadoSimplesVO;
import br.com.etraining.client.vo.impl.entidades.ExercicioVO;
import br.com.etraining.client.vo.impl.exerciciorealizado.RespostaConsultaListaExercicioRealizadoVO;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public final class TreinamentoActivity_
    extends TreinamentoActivity
{

    private Handler handler_ = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    private void init_(Bundle savedInstanceState) {
        pref = new EtrainingPref_(this);
        injectExtras_();
        exerciciosHolder = ExercicioHolder_.getInstance_(this);
        service = EtrainingAndroidService_.getInstance_(this);
        restoreSavedInstanceState_(savedInstanceState);
    }

    private void afterSetContentView_() {
        btnPrimario = ((Button) findViewById(id.btn_primario));
        dataSelecionada = ((TextView) findViewById(id.lbl_data_selecionada));
        listaItens = ((ListView) findViewById(id.lista_items));
        {
            View view = findViewById(id.btn_secundario);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TreinamentoActivity_.this.pressBotaoSecundario();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.btn_voltar);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TreinamentoActivity_.this.pressBotaoVoltar();
                    }

                }
                );
            }
        }
        {
            View view = findViewById(id.btn_primario);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        TreinamentoActivity_.this.pressBotaoPrincipal();
                    }

                }
                );
            }
        }
        ((ExercicioHolder_) exerciciosHolder).afterSetContentView_();
        ((EtrainingAndroidService_) service).afterSetContentView_();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    public static TreinamentoActivity_.IntentBuilder_ intent(Context context) {
        return new TreinamentoActivity_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    private void injectExtras_() {
        Intent intent_ = getIntent();
        Bundle extras_ = intent_.getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("acaoTreinamentoStrategy")) {
                try {
                    acaoStrategy = cast_(extras_.get("acaoTreinamentoStrategy"));
                } catch (ClassCastException e) {
                    Log.e("TreinamentoActivity_", "Could not cast extra to expected type, the field is left to its default value", e);
                }
            }
        }
    }

    @Override
    public void setIntent(Intent newIntent) {
        super.setIntent(newIntent);
        injectExtras_();
    }

    @Override
    public void finalizarInsercaoExercicioRealizado(final EtrainingViewException exception) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.finalizarInsercaoExercicioRealizado(exception);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void finalizarCarregamentoListaExercicioRealizado(final RespostaConsultaListaExercicioRealizadoVO response, final EtrainingViewException exception) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.finalizarCarregamentoListaExercicioRealizado(response, exception);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void finalizarRemocaoExercicioRealizado(final EtrainingViewException exception) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.finalizarRemocaoExercicioRealizado(exception);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void finalizarCarregamentoListaExercicio(final List<ExercicioVO> exercicios, final String tituloBotao, final EtrainingViewException exception) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.finalizarCarregamentoListaExercicio(exercicios, tituloBotao, exception);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void carregarListaExercicio(final Long categoriaSelecionada) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.carregarListaExercicio(categoriaSelecionada);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void carregarListaExercicioRealizado() {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.carregarListaExercicioRealizado();
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void inserirExercicioRealizado(final ExercicioVO exerc, final Integer quantidade) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.inserirExercicioRealizado(exerc, quantidade);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void removerExercicioRealizado(final ExercicioRealizadoSimplesVO exerc) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    TreinamentoActivity_.super.removerExercicioRealizado(exerc);
                } catch (RuntimeException e) {
                    Log.e("TreinamentoActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("listaExercicio", listaExercicio);
        bundle.putSerializable("acaoStrategy", acaoStrategy);
    }

    @SuppressWarnings("unchecked")
    private void restoreSavedInstanceState_(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            return ;
        }
        listaExercicio = ((ArrayList<ExercicioVO> ) savedInstanceState.getSerializable("listaExercicio"));
        acaoStrategy = ((Integer) savedInstanceState.getSerializable("acaoStrategy"));
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, TreinamentoActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public TreinamentoActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

        public TreinamentoActivity_.IntentBuilder_ acaoStrategy(Integer acaoStrategy) {
            intent_.putExtra("acaoTreinamentoStrategy", ((Serializable) acaoStrategy));
            return this;
        }

    }

}

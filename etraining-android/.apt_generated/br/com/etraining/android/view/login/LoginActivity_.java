//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package br.com.etraining.android.view.login;

import java.io.Serializable;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;
import br.com.etraining.android.R.id;
import br.com.etraining.android.exceptions.EtrainingViewException;
import br.com.etraining.android.service.EtrainingAndroidService_;
import br.com.etraining.android.utils.pref.EtrainingPref_;
import br.com.etraining.client.vo.impl.realizarlogin.RealizarLoginVO;
import br.com.etraining.client.vo.impl.realizarlogin.RespostaRealizarLoginVO;
import com.googlecode.androidannotations.api.BackgroundExecutor;

public final class LoginActivity_
    extends LoginActivity
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
        service = EtrainingAndroidService_.getInstance_(this);
    }

    private void afterSetContentView_() {
        txtSenha = ((TextView) findViewById(id.txt_login_senha));
        txtLogin = ((TextView) findViewById(id.txt_login_login));
        {
            View view = findViewById(id.btn_logar);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        LoginActivity_.this.logar();
                    }

                }
                );
            }
        }
        ((EtrainingAndroidService_) service).afterSetContentView_();
        inicializar();
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

    public static LoginActivity_.IntentBuilder_ intent(Context context) {
        return new LoginActivity_.IntentBuilder_(context);
    }

    @SuppressWarnings("unchecked")
    private<T >T cast_(Object object) {
        return ((T) object);
    }

    private void injectExtras_() {
        Intent intent_ = getIntent();
        Bundle extras_ = intent_.getExtras();
        if (extras_!= null) {
            if (extras_.containsKey("deslogarUsuario")) {
                try {
                    deslogarUsuario = cast_(extras_.get("deslogarUsuario"));
                } catch (ClassCastException e) {
                    Log.e("LoginActivity_", "Could not cast extra to expected type, the field is left to its default value", e);
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
    public void finalizarLoginAluno(final RespostaRealizarLoginVO response, final EtrainingViewException exception) {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginActivity_.super.finalizarLoginAluno(response, exception);
                } catch (RuntimeException e) {
                    Log.e("LoginActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    @Override
    public void realizarLogin(final RealizarLoginVO realizarLoginVO) {
        BackgroundExecutor.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    LoginActivity_.super.realizarLogin(realizarLoginVO);
                } catch (RuntimeException e) {
                    Log.e("LoginActivity_", "A runtime exception was thrown while executing code in a runnable", e);
                }
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, LoginActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public LoginActivity_.IntentBuilder_ flags(int flags) {
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

        public LoginActivity_.IntentBuilder_ deslogarUsuario(Boolean deslogarUsuario) {
            intent_.putExtra("deslogarUsuario", ((Serializable) deslogarUsuario));
            return this;
        }

    }

}

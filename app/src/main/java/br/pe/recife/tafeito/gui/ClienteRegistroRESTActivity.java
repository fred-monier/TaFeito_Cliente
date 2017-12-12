package br.pe.recife.tafeito.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Acesso;
import br.pe.recife.tafeito.negocio.Autenticacao;
import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.negocio.Usuario;
import br.pe.recife.tafeito.util.MaskaraCpfCnpj;
import br.pe.recife.tafeito.util.MaskaraType;
import br.pe.recife.tafeito.util.Util;

public class ClienteRegistroRESTActivity extends AppCompatActivity {

    //@InjectView(R.id.input_name)
    EditText _nameText;
    //@InjectView(R.id.input_cnpj)
    EditText _cpfText;
    //@InjectView(R.id.input_phone)
    EditText _phoneText;
    //@InjectView(R.id.input_email)
    EditText _emailText;
    //@InjectView(R.id.input_address)
    EditText _addressText;
    //@InjectView(R.id.input_password)
    EditText _passwordText;
    //@InjectView(R.id.btn_signup)
    Button _signupButton;
    //@InjectView(R.id.link_login)
    TextView _loginLink;

    //Task Async
    private InserirAcessoClienteRESTClientTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_registro_rest);

        //ButterKnife.inject(this);
        _nameText = (EditText) findViewById(R.id.input_name);
        _cpfText = (EditText) findViewById(R.id.input_cpf);
        _phoneText = (EditText) findViewById(R.id.input_phone);
        _emailText = (EditText) findViewById(R.id.input_email);
        _addressText = (EditText) findViewById(R.id.input_address);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
        ///////

        //Mascara início
        _cpfText.addTextChangedListener(MaskaraCpfCnpj.insert(_cpfText, MaskaraType.CPF));
        //Mascara fim

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Voltar para a tela de Login
                finish();
            }
        });
    }

    private void signup() {

        if (!validate()) {
            onSignupFailed(null);
            return;
        }

        _signupButton.setEnabled(false);

        String name = _nameText.getText().toString();
        String cpf = _cpfText.getText().toString().replaceAll("\\D", "");
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String address = _addressText.getText().toString();
        String password = _passwordText.getText().toString();


        //Cadastrar cliente

        try {

            //Investigar como pesquisar email já existente. Talvez trazendo resposta
            /*
            boolean existe = fachada.existePorLoginAcesso(email);
            if (existe) {
                onSignupFailed(getApplicationContext().getResources().
                        getText(R.string.registro_email_ja_existente).toString());
            } else {
            }
            */

            Acesso acesso = new Acesso();
            acesso.setLogin(email);
            acesso.setSenha(password);

            Usuario usuario = new Cliente();
            usuario.setHabilitado(true);
            usuario.setNome(name);
            usuario.setEndereco(address);
            usuario.setTelefone(Integer.parseInt(phone));
            usuario.setEmail(email);
            ((Cliente) usuario).setCpf(cpf);

            if (Util.temConexaoWeb(getApplicationContext())) {
                if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {

                    task = new InserirAcessoClienteRESTClientTask(acesso, (Cliente) usuario);
                    task.execute();
                }
            } else {

                throw new NegocioException(getApplicationContext().getResources().
                        getText(R.string.login_conexaoweb_inexistente).toString());
            }


        } catch (Exception e) {

            onSignupFailed(e.getMessage());
        }

        //new android.os.Handler().postDelayed(
        //         new Runnable() {
        //             public void run() {
        //                 // On complete call either onSignupSuccess or onSignupFailed
        //                 // depending on success
        //                 onSignupSuccess();
        //                 // onSignupFailed();
        //                 progressDialog.dismiss();
        //             }
        //         }, 3000);

    }


    private void onSignupSuccess(Autenticacao autenticacao) {
        _signupButton.setEnabled(true);

        Intent devolve = getIntent();
        devolve.putExtra(ClienteLoginRESTActivity.AUTENTICACAO, autenticacao);
        setResult(RESULT_OK, devolve);
        finish();
    }

    private void onSignupFailed(String message) {

        if (message == null) {
            message =  getApplicationContext().getResources().
                    getText(R.string.registro_falhou).toString();
        }
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String cpf = _cpfText.getText().toString().replaceAll("\\D", "");
        String phone = _phoneText.getText().toString();
        String email = _emailText.getText().toString();
        String address = _addressText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3 || name.length() > 200) {
            _nameText.setError(getApplicationContext().getResources().
                    getText(R.string.registro_nome_invalido).toString());
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (cpf.isEmpty() || cpf.length() != 11) { //|| !Util.isCPF(cpf)) {
            _cpfText.setError(getApplicationContext().getResources().
                    getText(R.string.registro_cpf_invalido).toString());
            valid = false;
        } else {
            _cpfText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 10 || phone.length() > 11) {
            _phoneText.setError(getApplicationContext().getResources().
                    getText(R.string.registro_phone_invalido).toString());
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ||  email.length() > 100) {
            _emailText.setError(getApplicationContext().getResources().
                    getText(R.string.registro_email_invalido).toString());
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (address.isEmpty() || address.length() > 200) {
            _addressText.setError(getApplicationContext().getResources().
                    getText(R.string.registro_endereco_invalido).toString());
            valid = false;
        } else {
            _addressText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError(getApplicationContext().getResources().
                    getText(R.string.login_senha_tamanho_invalido).toString());
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private class InserirAcessoClienteRESTClientTask extends AsyncTask<String, Void, String> {

        private final String CAMINHO =
                "http://192.168.1.107:8080/TaFeito_Servidor/rest/AcessoService/acessosCliente";

        private Acesso acesso;
        private Cliente cliente;

        private ProgressDialog progressDialog;

        public InserirAcessoClienteRESTClientTask(Acesso acesso, Cliente cliente) {
            this.acesso = acesso;
            this.cliente = cliente;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ClienteRegistroRESTActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(getApplicationContext().getResources().
                    getText(R.string.registro_criando_conta).toString());
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();

            if (s != null && !s.equals(Util.FAILURE_RESULT)) {

                Autenticacao aut = new Autenticacao();
                aut.setIdAcesso(Util.extrairIdResult(s));
                aut.setToken("");

                onSignupSuccess(new Autenticacao());
            }
            else{
                onSignupFailed(null);
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String res = null;

            HttpURLConnection conexao = null;

            try {

                conexao = Util.conectarPOST(CAMINHO);

                //Building URI
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("login", acesso.getLogin())
                        .appendQueryParameter("senha", acesso.getSenha())
                        .appendQueryParameter("email", cliente.getEmail())
                        .appendQueryParameter("endereco", cliente.getEndereco())
                        .appendQueryParameter("habilitado", cliente.isHabilitado() + "")
                        .appendQueryParameter("nome", cliente.getNome())
                        .appendQueryParameter("telefone", cliente.getTelefone() + "")
                        .appendQueryParameter("cpf", cliente.getCpf());

                //Getting object of OutputStream from urlConnection to write some data to stream
                OutputStream outputStream = conexao.getOutputStream();

                //Writer to write data to OutputStream
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(builder.build().getEncodedQuery());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                conexao.connect();
                //

                int resposta = conexao.getResponseCode();
                if (resposta == HttpURLConnection.HTTP_OK) {

                    InputStream is = conexao.getInputStream();

                    res = Util.bytesParaString(is);

                }

            } catch (Exception e) {

                e.printStackTrace();

            } finally {

                if (conexao != null) {
                    conexao.disconnect();
                }
            }

            return res;
        }

    }
}

package br.pe.recife.tafeito.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.pe.recife.tafeito.R;

public class ClientePrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_principal);
    }

    public void irConfig(View view) {
        Intent intent = new Intent(this, SettingsClientActivity.class);
        startActivity(intent);
    }

    public void irLogin(View view) {
        Intent intent = new Intent(this, ClienteLoginActivity.class);
        startActivity(intent);
    }

    public void irSchedule(View view) {
        Intent intent = new Intent(this, ScheduleClientActivity.class);
        startActivity(intent);
    }

    public void irOffer(View view) {
        Intent intent = new Intent(this, OfferClientActivity.class);
        startActivity(intent);
    }
}

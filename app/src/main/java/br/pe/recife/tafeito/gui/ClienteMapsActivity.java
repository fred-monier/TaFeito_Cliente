package br.pe.recife.tafeito.gui;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.fachada.FachadaTaFeitoSQLite;
import br.pe.recife.tafeito.fachada.IFachadaTaFeito;
import br.pe.recife.tafeito.negocio.Autenticacao;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.ServicoCategoria;
import br.pe.recife.tafeito.negocio.Usuario;

public class ClienteMapsActivity extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private IFachadaTaFeito fachada;
    private List<Fornecedor> fornecedores;
    private Double latitude;
    private Double longitude;
    private LatLng localizacao;
    private Spinner spn_categoria;
    private ArrayAdapter<CharSequence> spinner_adp;
    List<Address> addresses;
    Geocoder geocoder;
    public static final String AUTENTICACAO = "AUTENTICACAO";
    private Autenticacao autenticacao;
    private ServicoCategoria servico_categoria;
    private String markerConst;
    private Fornecedor fornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fachada = FachadaTaFeitoSQLite.getInstancia(getApplicationContext());
        fornecedores = new ArrayList<Fornecedor>();
        spn_categoria = (Spinner) findViewById(R.id.spn_categoria);
        autenticacao = new Autenticacao();
        servico_categoria = new ServicoCategoria();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(this);

        geocoder = new Geocoder(this, Locale.getDefault());
        addresses = null;
        autenticacao.setToken(AUTENTICACAO);

        try {
            addresses = geocoder.getFromLocationName("Brasil", 1);
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
            localizacao = new LatLng(latitude, longitude);
        }catch (IOException e){

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));

        spinner_adp = ArrayAdapter.createFromResource
                (this, R.array.array_categoria, android.R.layout.simple_spinner_item);
        spinner_adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_categoria.setAdapter(spinner_adp);


        spn_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                servico_categoria.setNome(parent.getItemAtPosition(position).toString());

                try {
                    fornecedores = fachada.listarPorServicoCategoriaFornecedor
                            (servico_categoria, autenticacao);
                } catch (InfraException e) {
                    autenticacao = null;
                }


                try {

                    int cont = 0;
                    while (cont < fornecedores.size()) {

                        addresses = geocoder.getFromLocationName(fornecedores.get(cont).getEndereco(), 1);
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                        localizacao = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(localizacao));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacao));
                        cont++;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        /*mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        int cont1 = 0;
        markerConst = "m";
        while (cont1 < fornecedores.size()) {

            if (marker.getId().equals(markerConst + cont1)) {

                //Toast.makeText(ClienteMapsActivity.this, fornecedores.get(cont1), Toast.LENGTH_SHORT).show();
                fornecedor = fornecedores.get(cont1);
            }
            cont1++;
        }
        return false;
    }
}

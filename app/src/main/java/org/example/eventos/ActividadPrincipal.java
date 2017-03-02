package org.example.eventos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * Created by miguel on 26/2/17.
 *
 */

public class ActividadPrincipal extends AppCompatActivity {
    @BindView(R.id.reciclerViewEventos)
    RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseRecyclerAdapter adapter;
    private static ActividadPrincipal current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); setContentView(R.layout.actividad_principal);
        if (!comprobarGooglePlayServices()) {
            Toast.makeText(this, "Error Google Play Services: no está instalado o no es válido." , Toast.LENGTH_LONG);
                    finish();
        }

        ButterKnife.bind(this);
        EventosAplicacion app = (EventosAplicacion) getApplicationContext();
        databaseReference = app.getItemsReference();
        adapter = new EventosRecyclerAdapter(R.layout.evento, databaseReference);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras!=null && extras.keySet().size()>4) {
            String evento="";
            evento ="Evento: "+extras.getString("evento")+ "\n";
            evento = evento + "Día: "+ extras.getString("dia")+ "\n";
            evento = evento +"Ciudad: "+extras.getString("ciudad")+ "\n";
            evento = evento +"Comentario: "+extras.getString("comentario");
            EventosAplicacion.mostrarDialogo(getApplicationContext(), evento);
            for (String key : extras.keySet()) {
                getIntent().removeExtra(key);
            }
            extras = null;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        current=this;
    }

    public static ActividadPrincipal getCurrentContext() {
        return current;
    }

    private boolean comprobarGooglePlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) { GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    EventosAplicacion.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }
}
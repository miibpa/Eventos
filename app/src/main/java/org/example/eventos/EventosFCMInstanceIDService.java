package org.example.eventos;

import android.app.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by miguel on 2/3/17.
 */

public class EventosFCMInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String idPush;
        idPush = FirebaseInstanceId.getInstance().getToken();
        EventosAplicacion.guardarIdRegistro(getApplicationContext(), idPush);
    }
}

package org.example.eventos;

import android.app.Service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by miguel on 26/2/17.
 */

public class EventosFCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String evento="";
            evento ="Evento: "+remoteMessage.getData().get("evento")+ "\n"; evento = evento + "Día: "+ remoteMessage.getData().get("dia")+ "\n"; evento = evento +"Ciudad: "+
                    remoteMessage.getData().get("ciudad")+"\n"; evento = evento +"Comentario: "
                    +remoteMessage.getData().get("comentario");
            EventosAplicacion.mostrarDialogo(getApplicationContext(), evento);
        } else {
            if (remoteMessage.getNotification() != null) {
                EventosAplicacion.mostrarDialogo(getApplicationContext(), remoteMessage.getNotification().getBody());
            }
        }
    }
}

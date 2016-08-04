package colors.com.example.firstplace.coloreyeze;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by FirstPlace on 19/07/2016.
 */
public class WebSocketCon {
    WebSocketClient mWebSocketClient ;

    public void sendMessage(String message){

        try{
            mWebSocketClient.send(message);
        }catch(org.java_websocket.exceptions.WebsocketNotConnectedException e){

        }

    }
    public Boolean getConected() {
        return conected;
    }

    public void setConected(Boolean conected) {
        this.conected = conected;
    }

    Boolean conected;
    public WebSocketClient getmWebSocketClient() {
        return mWebSocketClient;
    }

    public void setmWebSocketClient(WebSocketClient mWebSocketClient) {
        this.mWebSocketClient = mWebSocketClient;
    }

    public WebSocketCon(String ip){
        //COMEÇO DE CODIGO WEBSOCKET
        setConected(false);
        URI uri;
        try {

            uri = new URI("ws://" + ip);
            Log.v("URL",uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri,new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                setConected(true);
            // mWebSocketClient.send("print(\""+ "oi " +"\")");

            }

            @Override
            public void onMessage(String s) {

                Runnable runOnUiThread = new Runnable() {
                    @Override
                    public void run() {

                    }
                };
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
                conected=false;
                Log.v("Websocket state ", conected.toString());
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }

        };
        try {

            mWebSocketClient.connect();
        } catch(Exception E){


        }

//FIM WEBSOCKET

    }



}

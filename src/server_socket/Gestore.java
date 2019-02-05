package server_socket;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.net.ServerSocket;

public class Gestore {

    private int porta;                          //PORTA NECESSARIA AL SERVER SOCKET PER L'ASCOLTO DI CONNESSIONI
    private ServerSocket socketArray;           //OGGETTO SERVER SOCKET NECESSARIO ALL'ASCOLTO DI CONNESSIONI IN ENTRATA
    private ArrayList<Server> serverArray;      //ARRAYLIST DI OGGETTI SERVER, OGNIUNO RAPPRESENTA UN THREAD CONNESSO CON IL CLIENT
    private ArrayList<Socket> sockets;          //ARRAYLIST DI OGGETTI SOCKET, OGNIUNO GARANTISCE LA CONNESSIONE CON IL CLIENT

    public Gestore(int porta) {
        this.porta = porta;
        this.serverArray = new ArrayList<>();
        this.sockets = new ArrayList<>();
    }

    /**
     * Il metodo sottostante è necessario al server per connettersi con il client, in particolare viene istanziato un
     * oggetto server socket, in un ciclo infinito il ServerSocket rimane in ascolto di connessioni. A connessione avvenuta
     * viene istanziato un oggetto Socket all'interno di un ArrayList, quest'oggetto viene poi passato ad un nuovo oggetto
     * server (che eredita dall'oggetto Thread) all'interno di un ulteriore ArrayList di classe Server.
     * In fine il thread verrà avviato e verrà eseguito parallelamente al thread main.
     */
    public void nuovoGiocatore() {
        try {
            socketArray = new ServerSocket(this.porta);
            while (true) {
                sockets.add(this.socketArray.accept());
                serverArray.add(new Server(sockets.get(sockets.size() - 1)));
                serverArray.get(serverArray.size() - 1).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

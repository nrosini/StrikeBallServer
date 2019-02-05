package server_socket;

import java.io.IOException;
import java.io.DataInputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;

public class MulticastServer extends Thread {
    private MulticastSocket chat;               //SOCKET PER LA COMUNICAZIONE MULTICAST
    private final int porta;                    //PORTA SU CUI RIMARRÀ IN ASCOLTO IL SERVER MULTICAST
    private byte[] messaggio;                   //MESSAGGIO DA INVIARE IN MULTICAST

    public MulticastServer(int porta) {
        this.porta = porta;
    }

    @Override
    public void run(){
        /*Il server deve rimanere sempre in ascolto dunque ho deciso di lasciare il comando per l'ascolto in un ciclo
        * infinito*/
        for (;;) {
            this.inviaMessaggio(this.ascoltaStringa());
        }
    }

    /**
     * Dopo aver istanziato l'oggetto MulticastSocket e aver convertito la stringa da inviare in un array di byte,
     * costruisco un datagramma e lo passo al metodo send dell'oggetto MulticastSocket che provvederà a spedire
     * il datagramma a tutti gli host presenti nel gruppo che a loro volta saranno in ascolto attendendo un messaggio.
     * @param messaggioS Stringa da convertire in array di byte e da inviare in multicast
     */
    public void inviaMessaggio(String messaggioS) {
        try {
            this.chat = new MulticastSocket();
            this.messaggio = messaggioS.getBytes();
            DatagramPacket datagramma = new DatagramPacket(this.messaggio,this.messaggio.length,InetAddress.getByName("224.0.0.1"),this.getPorta());
            this.chat.send(datagramma);
        } catch (IOException e) {
            System.err.println("Errore socket multicast");
        }
    }

    private int getPorta() {
        return this.porta;
    }

    /**
     * Questo metodo rimane in ascolto di una stringa da un client, il metodo usato è lo stesso che viene usato sulla
     * classe server: si mette in ascolto un socket e all'arrivo della stringa la si restituisce, in questo caso,
     * al metodo inviaMessaggio() che rivierà la stringa in multicast.
     * @return restituisce la stringa ricevuta dai client
     */
    private String ascoltaStringa(){
        String stringaRicevuta = null;
        try {
            ServerSocket serverMessaggio = new ServerSocket(1408);
            System.out.println("Ok sono in ascolto :)");
            Socket socketMessaggio = serverMessaggio.accept();
            DataInputStream dIn = new DataInputStream(socketMessaggio.getInputStream());
            stringaRicevuta = dIn.readUTF();
            socketMessaggio.close();
            serverMessaggio.close();
        } catch (IOException e) {
            System.err.println("Errore socket messaggio");
        }
        System.out.println(stringaRicevuta);
        return stringaRicevuta;
    }
}

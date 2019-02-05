package server_socket;

public class Main {
    public static void main(String[] args) {

        //ISTANZIO UN OGGETTO MULTICAST CHE GESTISCA LA CHAT

        MulticastServer multicastServer = new MulticastServer(7520);

        //L'OGGETTO ISTANZIATO GESTISCE LE CONNESSIONI AL SERVER

        Gestore gestore = new Gestore(8000);

        multicastServer.start();    //AVVIO LA CHAT
        gestore.nuovoGiocatore();
    }
}

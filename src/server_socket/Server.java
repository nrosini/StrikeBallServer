package server_socket;

import java.net.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;

public class Server extends Thread {
    private Socket invia;               //SOCKET PASSATO DALL'OGGETTO GESTORE

    public Server(Socket invia) {
        this.invia = invia;
    }

    @Override
    public void run() {
        this.inviaNumero();
    }

    /**
     * Metodo più importante del programma: randomizza 4 cifre controllando che siano tutte diverse, una volta fatto
     * ciò invia al client il numero randomizzato attraverso il metodo writeUTF() dell'oggetto socket passato come
     * attributo.
     */
    private void inviaNumero() {
        Random rand = new Random();
        String numeroRandom ="";
        ArrayList<Integer> numeroRand;
        int numero;
        try {
            numeroRand = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                numero = rand.nextInt(9);
                if (!numeroRand.contains(numero)) {
                    numeroRand.add(numero);
                } else {
                    i--;
                }
            }
            for (Integer integer : numeroRand) {
                numeroRandom = numeroRandom + integer;
            }
            DataOutputStream dOut = new DataOutputStream(this.invia.getOutputStream());
            System.out.println(numeroRandom);
            dOut.writeUTF(numeroRandom);
            dOut.flush();
            numeroRandom = "";
        } catch (IOException erroreFlusso) {

        }
    }
}

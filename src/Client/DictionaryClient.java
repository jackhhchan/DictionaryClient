package Client;

/***
 * Class: DictionaryClient
 *
 * @author Jack Chan
 *
 * The client side functions for accessing dictionary functions
 * from the dictionary server based on user input.
 *
 */

import java.net.*;
import java.io.*;
import org.json.simple.*;

public class DictionaryClient {

    private String hostName;
    private int port;

    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private JSONObject jsonObj;
    private JSONArray jsonArray;

    public DictionaryClient() {
    }

    public DictionaryClient(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }


    public String query(String word) throws IOException{
        //Make connection to server.
        connect();
        obtainDataStreams();

        // Client input to server.
        inputToServer("query", word);

        // Server output to client.
        String result = in.readUTF();

        close();        // Close streams and socket.

        return result;
    }

    public String add(String word, String meaning) throws IOException{
        //Make connection to server.
        connect();
        obtainDataStreams();

        // Input to server.
        inputToServer("add", word, meaning);

        // Output to server.
        String message = in.readUTF();
        close();
        return message;
    }

    public String remove(String word) throws IOException{
        //Make connection to server.
        connect();
        obtainDataStreams();

        // Input to server.
        inputToServer("remove", word);

        // Output from server.
        String message = in.readUTF();
        close();

        return message;
    }



    /**
    * Mutator functions.
     **/
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setPort(int port) {
        this.port = port;
    }



    /**
    * Helper functions.
        * connect()             -- create socket and connect with server.
        * obtainDataStreams()   -- get data streams from socket connection to server.
        * close()               -- close streams and socket connection.
        * inputToServer(clientInput)          -- sends word through output stream to server.
        * inputToServer(clientInput, meaning) -- sends word and meaning through output stream to server. (Add ONLY)
     **/

    private void connect() throws IOException{
        // Create socket and connect with server.
        try {
            System.out.println("Attempting to connect with host " + hostName + " through port " + port + "...");
            socket = new Socket(hostName, port);       // Instantiate socket to connect with dictionary server.
            System.out.println("Connected.");
        }catch (UnknownHostException uhe){
            throw new UnknownHostException("Unknown Host.");
        }
        catch (IOException ioe){
            throw new IOException("Unable to connect.");
        }

    }

    private void obtainDataStreams() throws IOException{
        try {
            // Get IO from server.
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Receive welcome message from dictionary.
            System.out.println(in.readUTF());
        }catch(IOException ioe){
            throw new IOException("Unable to get streams from server.");
        }

    }

    private void close() throws IOException{
        try{
            in.close();
            out.close();
            socket.close();
        }catch(IOException e){
            throw new IOException("Unable to close streams or socket.");
        }

    }

    private void inputToServer(String operator, String clientInput) throws IOException{


        out.writeUTF(operator + "|" + clientInput);
        out.flush();
        System.out.println("Request sent to server.");
    }

    private void inputToServer(String operator, String clientInput, String meaning) throws IOException{
        out.writeUTF(operator + "|" + clientInput + "|" + meaning);
        out.flush();
        System.out.println("Request sent to server.");
    }


}

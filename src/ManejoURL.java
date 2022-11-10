import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.Scanner;
import java.util.ArrayList;

public class ManejoURL {
    public static final String TAG = ManejoURL.class.getSimpleName();
    public static final Logger LOG = Logger.getLogger(TAG);
    public static String URL;
    public static String word;
    public static int counter = 0;
    public static int pos;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese un url web ");
        URL = sc.next();
        System.out.println("Palabra que desea contar");
        word = sc.next();

        URL link = null;
        try {
            link = new URL(URL);
        } catch (MalformedURLException ex) {
            LOG.severe(ex.getMessage());
        }

        ArrayList <Character> wordList = new ArrayList<>();
        for(char ch: word.toCharArray()){
            wordList.add(ch);
        }

        // Crear un flujo para leer datos del URL
        BufferedReader htmlReader = null;
        try {
            htmlReader = new BufferedReader(
                    new InputStreamReader( link.openStream() ));
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }

        String htmlLine = null;

        try {
            while ((htmlLine = htmlReader.readLine()) != null) {
                if (htmlLine.toLowerCase().contains(word)) {
                    pos = 0;
                    counter++;
                    ArrayList<Character> lineList = new ArrayList<>();
                    for(char ch: htmlLine.toCharArray()){
                        lineList.add(ch);
                    }
                    for(int i = 0; i < lineList.size(); i++){
                        char c = Character.toLowerCase(lineList.get(i));
                        if(Objects.equals(wordList.get(0), c)){
                            int matched = 1;
                            if(wordList.size() > 1){
                                int j = 1;
                                int k = i+1;
                                c = Character.toLowerCase(lineList.get(k));
                                while(wordList.get(j) == c){
                                    j++;
                                    k++;
                                    c = Character.toLowerCase(lineList.get(k));
                                    matched++;
                                    if(matched == wordList.size()){
                                        pos = i;
                                        break;
                                    }
                                }
                            } else {
                                pos = i;
                            }
                        }
                    }
                    System.out.println("Posicion de ocurrencia " + counter + ": " + pos);
                }
            }
            System.out.println("Numero de veces que se presenta la palabra es " + counter);
        } catch (IOException ex) {
            LOG.severe(ex.getMessage());
        }
    }
}

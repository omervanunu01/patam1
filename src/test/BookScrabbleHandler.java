//Omer Vanunu 211678388
package test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {

    DictionaryManager dm;
    Scanner in;
    PrintWriter out;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {

        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient);

        String line;
        String[] words;
        boolean flag = false;

        line = in.nextLine(); // קריאת השורה הבאה
        words = line.split(","); // השורה מחולקת ע"פ פסיקים והמילים נכנסות למערך
        String[] restOfWords = new String[words.length - 1]; // מערך המילים ללא המילה הראשונה המציינת query/challenge
        System.arraycopy(words, 1, restOfWords, 0, words.length - 1); // העתקה ממערך למערך ע"פ האינדקסים המתאימים

        DictionaryManager dm = new DictionaryManager();

        // בדיקה האם המחרוזת שהתקבלה מהלקוח נמצאת במילון
        if (words[0].equals("Q")) { // words[0]= challenge/query
            flag = dm.query(restOfWords);
        }

        else { // if(method.equals("C"))

            flag = dm.challenge(restOfWords);
        }

        // החזרת תשובה ללקוח האם המילה במילון
        if (flag) {
            out.println("true");
            out.flush();
        } else {
            out.println("false");
            out.flush(); // מאפשר קריאה מהבאפר גם אם הוא אינו מלא
        }
        // סגירת משאבים
        this.close();

    }

    @Override
    public void close() {
        in.close();
        out.close();
    }

}

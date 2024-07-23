//Omer Vanunu 211678388
package test;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.IOException;
import java.net.ServerSocket;

public class MyServer {

    ClientHandler ch; // מופע של דרך ההתקשרות שנבחרה בין הלקוח לשרת
    int port; // פורט אליו נאזין
    boolean stop;

    public MyServer(int port, ClientHandler ch) { // בנאי
        this.port = port;
        this.ch = ch;
    }

    public void start() {

        stop = false;
        new Thread(() -> startServer()).start();
    }

    private void startServer() {

        try {
            ServerSocket server = new ServerSocket(port); // פתיחת סוקט ההופכת את המחשב לשרת המאזין ללקוחות
            server.setSoTimeout(1000); // זמן מוגבל שהגדרנו עד שתיזרק שגיאה שעבר הזמן שהוקצב להמתנה להתחברות לקוח
            while (!stop) {
                try {
                    Socket client = server.accept(); // קריאה חסומה- המתנה עד התחברות לקוח
                    ch.handleClient(client.getInputStream(), client.getOutputStream()); // מימוש בהתאם לדרך ההתקשרות
                    ch.close();
                    client.close();
                } catch (SocketTimeoutException e) {
                }
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // שינוי הערך הבוליאני יביא ליציאה מלולאת המתנה ללקוח וסגירת כלל המשאבים
    public void close() {
        stop = true;
    }

}

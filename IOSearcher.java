//Omer Vanunu 211678388
package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher {

    // שימוש במטודה סטטית כיוון שמדובר במטודה מחלקתית, לא ניצור מופעים של המחלקה
    public static boolean search(String word, String... fileNames) {

        for (String file : fileNames) { // חיפוש בכל אחד מהספרים שקיבלנו כמשתנה במטודה

            try {
                // קריאה מהקובץ לתוך באפר-על מנת לחסוך בפעולות כבדות
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine(); // קריאת שורות מתוך הקובץ
                while (line != null) { // כל עוד לא הסתיימה קריאת השורות
                    if (line.contains(word)) { // בדיקה האם המילה שאנו מחפשים קיימת בשורה שקראנו כעת מהקובץ
                        return true;
                    }

                    line = reader.readLine(); // קריאת השורה הבאה
                }
                reader.close(); // סגירת הקובץ בסיום השימוש בו טרם פתיחת הקובת הבא

            } catch (IOException e) { // זריקת חריגה במידה והייתה כזו בעת החיפוש
                e.printStackTrace();
            }
        }

        return false;
    }

}

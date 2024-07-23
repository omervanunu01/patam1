//Omer vanunu 211678388
package test;

import java.util.HashMap;

public class DictionaryManager {

    HashMap<String, Dictionary> hM;
    private static DictionaryManager singelton; // null

    DictionaryManager() { // בנאי

        hM = new HashMap<String, Dictionary>();

    }

    public boolean query(String... args) {

        // לולאה שמטרתה הוספת ספר מהמערך שקיבלנו במטודה למפה במידה ולא קיים בה
        for (int i = 0; i < args.length - 1; i++) {
            if (!hM.containsKey(args[i])) {
                hM.put(args[i], new Dictionary(args[i]));
            }
        }

        // נשתמש במשתנה בוליאני כדי שנוכל לעבור על כל הספרים ולא להחזיר תשובה ברגע
        // שנתקלנו במילה באחד הספרים כדי לעדכן את הקאש של כל מילון רלוונטי
        boolean flag = false;
        for (Dictionary d : hM.values()) { // עבור כל מילון (כל ספר)

            // נבדוק האם מילת החיפוש (השאילתא) נמצאת באחד הספרים
            if (d.query(args[args.length - 1])) {
                flag = true;
            }
        }

        return flag;
    }

    public boolean challenge(String... args) {

        for (int i = 0; i < args.length - 1; i++) {
            if (!hM.containsKey(args[i])) {
                hM.put(args[i], new Dictionary(args[i]));
            }
        }

        boolean flag = false;
        for (Dictionary d : hM.values()) { // עבור כל מילון (כל ספר)

            // נבדוק האם מילת החיפוש (השאילתא) נמצאת באחד הספרים
            if (d.challenge(args[args.length - 1])) {
                flag = true;
            }
        }

        return flag;
    }

    public int getSize() {
        return hM.size();
    }

    public static DictionaryManager get() {

        if (singelton == null) {
            singelton = new DictionaryManager();
        }
        return singelton;
    }

}

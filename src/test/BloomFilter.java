//Omer Vanunu 211678388
package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {

    private BitSet bitArray;
    private int size;
    private MessageDigest[] hashFuncs;

    // algs הוא מערך שמות של אלגוריתמים ויכול להכיל כמה מחרוזות שנרצה ללא הגבלה
    public BloomFilter(int size, String... algs) {
        this.size = size; // אורך מערך הביטים
        bitArray = new BitSet(size);
        hashFuncs = new MessageDigest[algs.length]; // מערך המכיל את שמות פונקציות ההאש

        for (int i = 0; i < algs.length; i++) { // אתחול המערך בהתאם לשמות הפוקנציות שקיבלנו כמשתנים בפונקציה

            try {
                String s = algs[i];
                hashFuncs[i] = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("No such algorithm");
            }

        }

    }

    public void add(String word) { // הפעלת פונקציות האש על המילה ועדכון מערך הביטים

        for (MessageDigest hashFunction : hashFuncs) {

            int index = getIndexOfBit(word, hashFunction);
            bitArray.set(index, true); // הדלקת הביט המתאים במערך הביטים המקורי
        }

    }

    public boolean contains(String word) {

        for (MessageDigest hashFunction : hashFuncs) {

            // במידה ויש ביט כבוי במערך הביטים של המילה, המילה בוודאות לא נמצאת

            if (!bitArray.get(getIndexOfBit(word, hashFunction))) {
                return false;
            }

        }
        // במידה וכלל הביטים דלוקים סבירות גבוהה שהמילה אכן נמצאת
        return true;
    }

    private int getIndexOfBit(String word, MessageDigest hashFunction) {

        // מערך של בתים החוזר בחישוב פונקציית האש על מערך של בתים
        byte[] bts = hashFunction.digest(word.getBytes());
        BigInteger b = new BigInteger(bts);// מרכיב מספר ממערך הבתים
        int bigNum = Math.abs(b.intValue());// מחזירה את המספר אי שלילי ומגולם בתור int
        int index = bigNum % size; // ביצוע מודולו כך שהערך החוזר הוא אינדקס הביט שנדרשה להדליק במערך הביטים

        return index;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder(); // אובייקטים הניתנים לשינוי מבלי ליצור מחרוזת חדשה בכל שינוי
        for (int i = 0; i < bitArray.length(); i++) { // עדכון המחרוזת באפסים ואחדות בהתאם לביטים כבויים ודלוקים
            if (bitArray.get(i)) { // במידה והביט דלוק
                s.append(1); // מטודה המוסיפה מחרוזת או תו לסופה
            }

            else { // במידה והביט כבוי
                s.append(0);
            }
        }

        return s.toString(); // המרת האובייקט למחרוזת כפי שהתבקשנו בערך ההחזרה של הפונקציה
    }

}

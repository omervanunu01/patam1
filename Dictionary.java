//Omer Vanunu 211678388
package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {

    private CacheReplacementPolicy lru;
    private CacheReplacementPolicy lfu;
    private CacheManager cacheForWordsExist;
    private CacheManager cacheForWordsNonExist;
    private BloomFilter b;
    private String[] fileNames;

    public Dictionary(String... fileNames) { // בנאי

        this.fileNames = fileNames;
        this.lru = new LRU();
        this.lfu = new LFU();

        this.cacheForWordsExist = new CacheManager(400, lru);
        this.cacheForWordsNonExist = new CacheManager(100, lfu);
        this.b = new BloomFilter(256, "MD5", "SHA1");

        for (String file : fileNames) { // הכנסת כל מילה מהקבצים לבלום פילטר

            try {

                // משתנה מסוג זה עוטף על מחרוזת ומפרק אותה למילים- הפרדה אוטומטית נעשית ע"י
                // רווחים וירידות שורה
                Scanner scan = null;
                scan = new Scanner(new BufferedReader(new FileReader(file)));
                while (scan.hasNext()) { // כל עוד יש מחרוזות נוספות בקובץ
                    String word = scan.next(); // נכניס לתוך מחרוז את המילה הבאה שנקראה מהקובץ
                    b.add(word); // נוסיף את המילה לבלום פילטר

                }
                scan.close(); // סגירת הקובץ
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // בדיקה האם המילה נמצאת בספרים ע"פ סדר המסננים
    public boolean query(String word) {

        // בדיקה האם נמצאת בקאש של המילים הקיימות
        if (cacheForWordsExist.query(word)) {
            return true;
        }

        // בדיקה האן נמצאת בקאש של המילים שאינן קיימות
        if (cacheForWordsNonExist.query(word)) {
            return false;
        }
        // בדיקה האם נמצאת בבלום פילטר ובהתאם לכך הוספה לקאש של המילים הקיימות
        if (b.contains(word)) {

            cacheForWordsExist.add(word);
            return true;
        } else {// במידה ולא נמצאת בבלום פילטר, נוסיף לקאש של המילים שאינן קיימות
            cacheForWordsNonExist.add(word);
            return false;
        }

    }

    // במידה והמשתמש החליט לאתגר את השרת שיחםש האם המילה קיימת בכל הספרים
    public boolean challenge(String word) {

        // במידה והמילה נמצאה קיימת באחד הספרים נוסיף אותה לקאש של המילים הקיימות
        if (IOSearcher.search(word, fileNames)) {
            cacheForWordsExist.add(word);
            return true;
        }

        // אחרת, נוסיף אותה לקאש של המילים שאינן קיימות
        else {
            cacheForWordsNonExist.add(word);
            return false;
        }
    }

}

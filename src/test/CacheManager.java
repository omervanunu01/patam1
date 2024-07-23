// Omer Vanunu 211678388
package test;

import java.util.*;

// יחזיק בזיכרון תשובות לשיאלתות הנפוצות ביותר- מוגבל מבחינת מקום
public class CacheManager {

    private int MaxSizeOfCache; // הגודל המקסימלי של רשימת המילים בזיכרון
    // אובייקט פולימורפי המגדיר באיזו דרך יתווספו ויימחקו מילים מהרשימה בהתאם למופע
    // שנוצר
    private CacheReplacementPolicy crp;
    private HashSet<String> wordsInCache; // the list of the words in cache

    public CacheManager(int size, CacheReplacementPolicy crp) { // בנאי

        this.MaxSizeOfCache = size;
        this.crp = crp;
        this.wordsInCache = new HashSet<String>();
    }

    public boolean query(String word) {// check if the word in cache

        if (wordsInCache.contains(word)) {
            return true;
        }

        else {
            return false;
        }
    }

    public void add(String word) {

        String wordToRemove; // המילה שנדרש למחוק כדי לפנות מקום בזיכרון

        if (wordsInCache.size() == MaxSizeOfCache) { // בדיקה האם נדרש למחוק מילה ולפנות זיכרון

            wordToRemove = this.crp.remove();
            wordsInCache.remove(wordToRemove);
        }

        crp.add(word); // נוסיף את המילה לקאש בהתאם לאלגוריתם שיצרנו מופע ממנו LRU\LFU
        wordsInCache.add(word);

    }
}

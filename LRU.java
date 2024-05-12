//Omer Vanunu 211678388
package test;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {

    private LinkedList<String> listOfWords;

    public LRU() { // בנאי

        listOfWords = new LinkedList<String>();
    }

    // מטודה הדורסת את המטודה בממשק-תפקידה להוסיף את המילה לרשימה במידה ולא קיימת בה
    // ובמידה וקיימת,למחוק אותה ולהוסיף מחדש לסוף הרשימה
    @Override
    public void add(String word) {

        if (listOfWords.contains(word)) {
            listOfWords.remove(word);
            listOfWords.add(word);
        }

        else {
            listOfWords.add(word);
        }
    }

    // המילה הראשונה ברשימה, היא למעשה ההמילה שנשאלנו עליה לפני הכי הרבה זמן
    @Override
    public String remove() {

        return listOfWords.removeFirst();
    }
}

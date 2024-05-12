//Omer Vanunu 211678388
package test;

import java.util.*;

public class LFU implements CacheReplacementPolicy {

    private LinkedHashMap<String, Integer> mapOfWords;

    public LFU() {

        mapOfWords = new LinkedHashMap<String, Integer>();
    }

    @Override
    public void add(String word) {

        // במידה והמחרוזת כבר קיימת במפה, נמצא אותו ונוסיף לערכה 1
        if (mapOfWords.containsKey(word)) {
            int num = mapOfWords.get(word);
            num++;
            mapOfWords.replace(word, num);
        }

        // במידה ואינה קיימת,נוסיף איבר למפה הכולל מפתח וערך 1 כי זו הפעם הראשונה שביקשו
        // אותו
        else {
            mapOfWords.put(word, 1);
        }
    }

    @Override
    public String remove() {

        String stringToRemove = null;
        int toRemove = Integer.MAX_VALUE;

        // לולאה למציאת מספר הבקשות המינימלי
        for (@SuppressWarnings("rawtypes")
        Map.Entry m : mapOfWords.entrySet()) {

            if ((int) m.getValue() < toRemove) {

                toRemove = (int) m.getValue();

            }
        }

        Set<Map.Entry<String, Integer>> set = mapOfWords.entrySet();
        Iterator<Map.Entry<String, Integer>> it1 = set.iterator(); // it1.next=first Entry
        // כל עוד ישנם איברים נוספים בסט וטרם נמצא האובייקט עם מספר הבקשות המינימלי
        while (it1.hasNext() && stringToRemove == null) {
            Map.Entry<String, Integer> it2 = it1.next();
            if (it2.getValue() == toRemove) {
                stringToRemove = it2.getKey();
                break;
            }
        }

        return stringToRemove;

    }

}

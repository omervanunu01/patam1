//Omer Vanunu 211678388
package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof Tile))
            return false;
        if (!super.equals(object))
            return false;
        Tile tile = (Tile) object;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), letter, score);
    }

    public static class Bag {

        private int[] letters; // מייצג את כמות האריחים מכל אות במצב נוכחי במשחק
        private final int[] constantLetters; // מייצג את כמות האריחים מכל אות במצב ההתחלתי
        private final Tile[] tiles; // מערך של 26 אריחים
        private int[] scores; // מייצג את הניקוד עבור כל אות
        private int currentAmount; // current amount of tiles
        private static Bag singelton; // null

        private Bag() {
            this.currentAmount = 98;
            this.letters = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1 };
            this.constantLetters = new int[] { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1,
                    2, 1 };
            this.scores = new int[] { 1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10 };
            this.tiles = new Tile[26];
            for (int i = 0; i < 26; i++) {
                tiles[i] = new Tile((char) ('A' + i), scores[i]);
            }
        }

        public Tile getRand() {

            if (currentAmount == 0) {
                return null;
            }

            Random rand = new Random();
            int randomNumber = rand.nextInt(26);

            while (letters[randomNumber] == 0) { // כל עוד הגענו לאינדקס המייצג אות שלא נותרו ממנה אריחים- נגריל אחרת
                randomNumber = rand.nextInt(26);
            }

            letters[randomNumber]--;

            currentAmount--;

            return tiles[randomNumber];
        }

        public Tile getTile(char letter) {

            int index = (int) letter - (int) 'A';
            if (index > 26 || index < 0 || letters[index] == 0) {
                return null;
            }

            letters[index]--;
            currentAmount--;
            return tiles[index];
        }

        public void put(Tile tile) {

            char c = tile.letter;
            int index = (int) c - (int) 'A';
            if (this.letters[index] < this.constantLetters[index]) {
                letters[index]++;
                currentAmount++;
            }
        }

        public int size() {
            return currentAmount;
        }

        public int[] getQuantities() {
            return this.letters.clone();
        }

        public static Bag getBag() {
            if (singelton == null) {
                singelton = new Bag();
            }
            return singelton;
        }
    }
}
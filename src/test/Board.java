//Omer Vanunu 211678388
package test;

import java.util.ArrayList;

public class Board {
    private static Board singl; // singleton
    private Tile[][] newBoard; // לוח המשחק המיוצג ע"י אריחים
    private final int[][] bonusBoard; // מטריצה המייצגת את הלוח עם המשבצות המעניקות ניקוד מיוחד
    private final int dl; // doubleLetter-light blue
    private final int tl; // tripleLetter-blue
    private final int dw; // doubleWord-yellow
    private final int tw; // tripleWord-red

    private Board() {
        newBoard = new Tile[15][15];
        this.dl = 2;
        this.tl = 3;
        this.dw = 200;
        this.tw = 300;

        this.bonusBoard = new int[][] {
                { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw },
                { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
                { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
                { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
                { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
                { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
                { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
                { tw, 0, 0, dl, 0, 0, 0, dw, 0, 0, 0, dl, 0, 0, tw },
                { 0, 0, dl, 0, 0, 0, dl, 0, dl, 0, 0, 0, dl, 0, 0 },
                { 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, tl, 0 },
                { 0, 0, 0, 0, dw, 0, 0, 0, 0, 0, dw, 0, 0, 0, 0 },
                { dl, 0, 0, dw, 0, 0, 0, dl, 0, 0, 0, dw, 0, 0, dl },
                { 0, 0, dw, 0, 0, 0, dl, 0, dl, 0, 0, 0, dw, 0, 0 },
                { 0, dw, 0, 0, 0, tl, 0, 0, 0, tl, 0, 0, 0, dw, 0 },
                { tw, 0, 0, dl, 0, 0, 0, tw, 0, 0, 0, dl, 0, 0, tw } };
    }

    public static Board getBoard() {
        if (singl == null) {
            singl = new Board();
        }
        return singl;
    }

    public Tile[][] getTiles() {

        return newBoard.clone();
    }

    private boolean onBoard(Word w) { // פונקציה הבודקת שהמילה בגבולות הלוח

        int sRow = w.getRow(); // start index row of word
        int sCol = w.getCol(); // start index col of word
        int fRow, fCol; // final index row and col

        if (sCol < 0 || sCol >= 15 || sRow < 0 || sRow >= 15) {
            return false;
        }

        if (w.isVertical()) { // מאונך

            fRow = sRow + w.getTiles().length - 1;
            fCol = sCol;

            if (fCol < 0 || fCol >= 15 || fRow < 0 || fRow >= 15) {

                return false;
            }
        }

        if (!w.isVertical()) { // מאוזן
            fRow = sRow;
            fCol = sCol + w.getTiles().length - 1;

            if (fCol < 0 || fCol >= 15 || fRow < 0 || fRow >= 15) {

                return false;
            }
        }

        return true;
    }

    private boolean onStar(Word w) { // בדיקה האם אחד האריחים המרכיבים את המילה נמצא על הכוכב בלוח

        int colIndex = w.getCol();
        int rowIndex = w.getRow();

        for (int i = 0; i < w.getTiles().length; i++) {
            if (colIndex == 7 && rowIndex == 7) {
                return true;
            }

            if (w.isVertical()) { // מאונך
                rowIndex++;
            }

            else { // מאוזן
                colIndex++;
            }

        }

        return false;
    }

    // בדיקת חריגה מהלוח עם פונקציה המקבלת כמשתנה שורה וטור
    private boolean tileOnBoard(int row, int col) {

        if (row < 0 || row >= 15 || col < 0 || col >= 15) {
            return false;
        }

        return true;
    }

    private boolean checkNeighbors(Word w) {

        int tileCol = w.getCol();
        int tileRow = w.getRow();

        // לולאה הבודקת אם לאחד האריחים המרכיבים את המילה קיים שכן מאחד צדדיו ,נשען על
        // אריח קיים
        for (@SuppressWarnings("unused")
        Tile t : w.getTiles()) {

            if ((tileOnBoard(tileRow + 1, tileCol) && newBoard[tileRow + 1][tileCol] != null) ||
                    (tileOnBoard(tileRow, tileCol + 1) && newBoard[tileRow][tileCol + 1] != null) ||
                    (tileOnBoard(tileRow - 1, tileCol) && newBoard[tileRow - 1][tileCol] != null) ||
                    (tileOnBoard(tileRow, tileCol - 1) && newBoard[tileRow][tileCol - 1] != null)) {

                return true;
            }

            if (w.isVertical())
                tileRow++;
            else
                tileCol++;
        }
        return false;
    }

    private boolean isNotChangeAble(Word w) {

        int colIndex = w.getCol();
        int rowIndex = w.getRow();

        for (int i = 0; i < w.getTiles().length; i++) {

            if (newBoard[rowIndex][colIndex] != null && newBoard[rowIndex][colIndex] != w.getTiles()[i]) {
                return false;
            } // האריח שונה

            if (w.isVertical()) {
                rowIndex++;
            }

            else {
                colIndex++;
            }
        }

        return true;
    }

    public boolean boardLegal(Word w) {

        if (!onBoard(w)) { // בדיקה שהמילה בגבולות הלוח
            return false;
        }

        if (newBoard[7][7] == null && !onStar(w)) { // במידה והכוכב ריק, נדרשת שתהיה מילה על הכוכב
            return false;
        }
        if (newBoard[7][7] != null && !checkNeighbors(w)) { // בדיקה שלמילה הקיימת יש אריחים צמודים
            return false;
        }
        if (!isNotChangeAble(w)) { // בדיקה שהאריחים המרכיבים את המילה לא שונו
            return false;
        }

        return true;
    }

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public ArrayList<Word> getWords(Word w) {

        Tile[][] copyOfBoard = getTiles();
        ArrayList<Word> boardBefore = wordsOnBoard(copyOfBoard); // מחזיר רשימה של המילים בלוח הנוכחי טרם השינוי
        int row = w.getRow();
        int col = w.getCol();
        for (int i = 0; i < w.getTiles().length; i++) { // הוספת המילה החדשה להעתק לוח המשחק הנוכחי
            copyOfBoard[row][col] = w.getTiles()[i];
            if (w.isVertical()) {
                row++;
            }

            else {
                col++;
            }
        }
        ArrayList<Word> boardAfter = wordsOnBoard(copyOfBoard);
        boardAfter.removeAll(boardBefore); // נמחק מרשימת המילים החדשה את כל המילים שהופיעו גם ברשימת המילים הקודמת

        return boardAfter;

    }

    /*
     * פונקציה המחזירה מערך של כלל המילים, במאונך ובמאוזן, הנמצאות בלוח הנוכחי
     * שהתקבל כמשתנה
     */
    private ArrayList<Word> wordsOnBoard(Tile[][] currentBoard) {

        ArrayList<Word> words = new ArrayList<Word>(); // יצירת רשימת המילים שתוחזר בסוף

        // לולאה המחפשת אחר מילים הכתובות במאוזן
        for (int i = 0; i < currentBoard[0].length; i++) { // רץ על שורות הלוח
            int j = 0; // רץ על עמודות הלוח
            while (j < currentBoard[i].length) {
                ArrayList<Tile> newWord = new ArrayList<Tile>();
                int rowOfWord = i;
                int colOfWord = j;
                while (j < currentBoard[i].length && currentBoard[i][j] != null) {
                    newWord.add(currentBoard[i][j]);
                    j++; // רץ על עמודות הלוח לגלות את המילה- כל עוד ישנם אריחים ברצף ואין חריגה מגובולות
                         // הלוח
                }

                if (newWord.size() > 1) { // מילה באורך 1 היא לא מילה
                    Tile[] wordOnBoard = new Tile[newWord.size()]; // יצירת מערך אריחים עבור המילה שגילינו שיועבר בבנאי
                    words.add(new Word(newWord.toArray(wordOnBoard), rowOfWord, colOfWord, false));
                }
                /*
                 * לאחר גילוי המילה, במידה וטרם חרגנו מגבולות הלוח בעמודות,
                 * יכול להיות שהמילה הבאה נמצאת באותה השורה במספר רווחים מהמילה הקודמת
                 */
                j++;
            }
        }

        // לולאה המחפשת אחר מילים הכתובות במאונך
        for (int j = 0; j < currentBoard[0].length; j++) { // רץ על עמודות הלוח
            int i = 0; // רץ על שורות הלוח
            while (i < currentBoard[j].length) {
                ArrayList<Tile> newWord = new ArrayList<Tile>();
                int rowOfWord = i;
                int colOfWord = j;
                while (i < currentBoard[i].length && currentBoard[i][j] != null) {
                    newWord.add(currentBoard[i][j]);
                    i++;
                }

                if (newWord.size() > 1) {
                    Tile[] wordOnBoard = new Tile[newWord.size()];
                    words.add(new Word(newWord.toArray(wordOnBoard), rowOfWord, colOfWord, true));
                }
                i++;
            }
        }

        return words;
    }

    public int getScore(Word w) {

        int totalScore = 0; // ערך החזרה המכיל את הניקוד המלא על המילה
        int currentRow = w.getRow();
        int currentCol = w.getCol();

        // בדיקה האם אחד האריחים במילה נמצא על משבצת המכפילה את ניקוד האות
        for (int i = 0; i < w.getTiles().length; i++) { // לולאה שרצה על כל האריחים המרכיבים את המילה
            totalScore += w.getTiles()[i].score;
            if (bonusBoard[currentRow][currentCol] == dl) {
                totalScore += w.getTiles()[i].score;
            }

            if (bonusBoard[currentRow][currentCol] == tl) {
                totalScore += 2 * (w.getTiles()[i].score);
            }

            if (w.isVertical()) { // מאונך
                currentRow++;
            }

            if (!w.isVertical()) { // מאוזן
                currentCol++;
            }

        }

        currentRow = w.getRow();
        currentCol = w.getCol();

        // בדיקה האם אחד האריחים במילה נמצא על משבצת המכפילה את ניקוד המילה
        for (int i = 0; i < w.getTiles().length; i++) {

            if (bonusBoard[currentRow][currentCol] == dw) {
                totalScore = totalScore * 2;
                break;
            }

            else if (bonusBoard[currentRow][currentCol] == tw) {
                totalScore = totalScore * 3;
                break;
            }

            if (w.isVertical()) { // מאונך
                currentRow++;
            }

            else if (!w.isVertical()) { // מאוזן
                currentCol++;
            }
        }

        return totalScore;
    }

    public int tryPlaceWord(Word w) {

        int totalScore = 0;

        // במילה שקיבלנו בפוקנציה,ישנן אותיות חופפות למילים קודמות שכבר על הלוח ולכן
        // במערך האריחים של המילה שקיבלנו במשתנה הם מצביעים על נול
        // HORN במקום _ יש נול האות החסרה כבר הונחה על הלוח במסגרת המילה הקודמת FA_M
        // דוגמא

        Tile[] TilesOfWordToCheck = w.getTiles();
        int row = w.getRow();
        int col = w.getCol();

        // נשלים את האות החופפת בהעתק מערך האריחים של המילה שיצרנו
        for (int i = 0; i < w.getTiles().length; i++) {

            if (w.getTiles()[i] == null) {

                TilesOfWordToCheck[i] = newBoard[row][col];
            }
            if (w.isVertical()) {
                row++;
            } else {
                col++;
            }
        }

        // יצירת אובייקט מסוג מילה שיועבר כשמתנה במטודות העזר הבודקות תקינות השמה על
        // הלוח
        Word wordToCheck = new Word(TilesOfWordToCheck, w.getRow(), w.getCol(), w.isVertical());

        if (!boardLegal(wordToCheck)) {
            return 0;
        }

        ArrayList<Word> listOfWords = getWords(w);

        // נבדוק תקינות מילונית של כלל המילים החדשות שנוצרו כתוצאה מהשמת המילה החדשה
        // בלוח
        for (Word word : listOfWords) {

            if (dictionaryLegal(word)) {
                totalScore += getScore(word);
            }

            // במידה ואחת המילים שנוצרו אינה תקינה מילונית, כל השמת המילה החדשה המקורית
            // פסולה ויוחזר 0
            else {
                return 0;
            }
        }

        // השמת המילה החדשה על הלוח (עבור האותיות שאינן חופפות)
        int r = w.getRow();
        int c = w.getCol();

        for (int i = 0; i < w.getTiles().length; i++) {
            if (w.getTiles()[i] != null) {
                newBoard[r][c] = w.getTiles()[i];
            }

            if (w.isVertical()) {
                r++;
            }

            else {
                c++;
            }

        }

        // לאחר השמת המילה הראשונה על הלוח, משבצת הכוכב תפוסה והחל מהשמת המילה
        // הבאה,משבצת זו אינה תעניק ניקוד

        if (newBoard[7][7] != null) {

            bonusBoard[7][7] = 0;
        }

        return totalScore;
    }
}

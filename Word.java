package test;


public class Word {

	private Tile[] tiles;
    private int row;
    private int col;
    private boolean vertical;

    public Word(Tile[] tiles, int row, int col, boolean vertical) {
        this.tiles = tiles;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Word)) return false;
        if (!super.equals(object)) return false;
        Word word = (Word) object;
        return getRow() == word.getRow() && getCol() == word.getCol() && isVertical() == word.isVertical() && java.util.Arrays.equals(getTiles(), word.getTiles());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), getRow(), getCol(), isVertical());
        result = 31 * result + Arrays.hashCode(getTiles());
        return result;
    }
}

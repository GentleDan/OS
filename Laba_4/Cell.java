public class Cell {

    private int cellStatus;

    private Cell nextCell;

    public Cell(int cellStatus) {
        this.cellStatus = cellStatus;
    }

    public int getCellStatus() {
        return cellStatus;
    }

    public void setCellStatus(int cellStatus) {
        this.cellStatus = cellStatus;
    }

    public Cell getNextCell() {
        return nextCell;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }
}

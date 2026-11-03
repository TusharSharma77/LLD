public class Seat{
   private String id;
    private SeatType seatType;
    private int row;
    private int column;

    public Seat(String id, SeatType seatType, int row, int column){
        this.id = id;
        this.seatType = seatType;
        this.row = row;
        this.column = column;
    }
    // getters and setters
    public String getId() {
        return id;
    }   

    public void setId(String id) {
        this.id = id;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }   

    public int getRow() {
        return row;
    }


    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
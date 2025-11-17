package com.quak.cinema_reservation_app.model;

public class Seat {
    public enum SeatType{
        SEAT(0), WHEELCHAIR(1), ISLE(2), EMPTY(3);

        private final int value;

        SeatType(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Long id;
    private Long room_id;
    private int seat_row;
    private int seat_col;
    private SeatType seat_type;

    public Seat() { }

    public Seat(Long id, Long roomId, int seatRow, int seatCol, SeatType seatType) {
        this.id = id;
        this.room_id = roomId;
        this.seat_row = seatRow;
        this.seat_col = seatCol;
        this.seat_type = seatType;
    }

    /** GETTERS AND SETTERS */

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRoomId() { return room_id; }
    public void setRoomId(Long roomId) { this.room_id = roomId; }

    public int getSeatRow() { return seat_row; }
    public void setSeatRow(int seatRow) { this.seat_row = seatRow; }

    public int getSeatCol() { return seat_col; }
    public void setSeatCol(int seatCol) { this.seat_col = seatCol; }

    public SeatType getSeatType() { return seat_type; }
    public void setSeatType(SeatType seatType) { this.seat_type = seatType; }

    public static SeatType getSeatTypeFromIndex(int index) { return SeatType.values()[index]; }

    @Override
    public String toString() {
        return "SEAT | " +
                "id=" + id +
                ", row='" + seat_row + '\'' +
                ", col='" + seat_col + '\'';
    }
}
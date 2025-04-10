package com.example.employee_seating_backend.model;

public class SeatingChart {

    private int floorSeatSeq;
    private int floorNo;
    private int seatNo;
    private boolean isOccupied; // 是否已被佔用

    public int getFloorSeatSeq() {
        return floorSeatSeq;
    }

    public void setFloorSeatSeq(int floorSeatSeq) {
        this.floorSeatSeq = floorSeatSeq;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }
}

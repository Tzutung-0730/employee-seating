package com.example.employee_seating_backend.repository;

import com.example.employee_seating_backend.model.SeatingChart;
import com.example.employee_seating_backend.model.SeatAssignment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SeatingChartRepository {

    private final JdbcTemplate jdbcTemplate;

    public SeatingChartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SeatingChart> getAllSeatingCharts() {
        String sql = "CALL get_all_seating_chart()";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SeatingChart seatingChart = new SeatingChart();
            seatingChart.setFloorSeatSeq(rs.getInt("floor_seat_seq"));
            seatingChart.setFloorNo(rs.getInt("floor_no"));
            seatingChart.setSeatNo(rs.getInt("seat_no"));
            seatingChart.setOccupied(rs.getBoolean("is_occupied"));
            return seatingChart;
        });
    }

    public SeatingChart getSeatingByFloorSeatSeq(int floorSeatSeq) {
        String sql = "CALL get_seating_by_floor_seat_seq(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{floorSeatSeq}, (rs, rowNum) -> {
            SeatingChart seatingChart = new SeatingChart();
            seatingChart.setFloorSeatSeq(rs.getInt("floor_seat_seq"));
            seatingChart.setFloorNo(rs.getInt("floor_no"));
            seatingChart.setSeatNo(rs.getInt("seat_no"));
            seatingChart.setOccupied(rs.getBoolean("is_occupied"));
            return seatingChart;
        });
    }

    public int createSeatingChart(SeatingChart seatingChart) {
        String sql = "CALL insert_seating_chart(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, seatingChart.getFloorSeatSeq(), seatingChart.getFloorNo(), seatingChart.getSeatNo(), seatingChart.isOccupied());
    }

    public int updateSeatingChart(SeatingChart seatingChart) {
        String sql = "CALL update_seating_chart(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, seatingChart.getFloorSeatSeq(), seatingChart.getFloorNo(), seatingChart.getSeatNo(), seatingChart.isOccupied());
    }

    public void deleteSeatingChart(int floorSeatSeq) {
        String sql = "CALL delete_seating_chart(?)";
        jdbcTemplate.update(sql, floorSeatSeq);
    }

    public int updateSeatStatusToOccupied(int floorSeatSeq) {
        String sql = "CALL update_seat_status_to_occupied(?)";
        return jdbcTemplate.update(sql, floorSeatSeq);
    }

    public int updateSeatStatusToAvailable(int floorSeatSeq) {
        String sql = "CALL update_seat_status_to_available(?)";
        return jdbcTemplate.update(sql, floorSeatSeq);
    }

    public Boolean getSeatStatusByFloorSeatSeq(int floorSeatSeq) {
        String sql = "CALL get_seat_status(?, @status);";
        jdbcTemplate.update(sql, floorSeatSeq);
        return jdbcTemplate.queryForObject("SELECT @status", Boolean.class);
    }

    public List<SeatAssignment> getEmployeeSeatAssignment() {
        String sql = "CALL get_employee_seat_assignment()";
        return jdbcTemplate.query(sql, new RowMapper<SeatAssignment>() {
            @Override
            public SeatAssignment mapRow(ResultSet rs, int rowNum) throws SQLException {
                SeatAssignment seatAssignment = new SeatAssignment();
                // 將 snake_case 資料庫欄位轉換為 camelCase DTO 屬性
                seatAssignment.setFloorSeatSeq(rs.getInt("floor_seat_seq"));
                seatAssignment.setFloorNo(rs.getInt("floor_no"));
                seatAssignment.setSeatNo(rs.getInt("seat_no"));
                seatAssignment.setIsOccupied(rs.getBoolean("is_occupied"));
                seatAssignment.setEmpId(rs.getString("emp_id"));
                return seatAssignment;
            }
        });
    }
}

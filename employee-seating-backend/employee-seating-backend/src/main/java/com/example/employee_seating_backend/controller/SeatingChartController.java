package com.example.employee_seating_backend.controller;

import com.example.employee_seating_backend.model.SeatingChart;
import com.example.employee_seating_backend.service.SeatingChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seating-charts")
public class SeatingChartController {

    private final SeatingChartService seatingChartService;

    @Autowired
    public SeatingChartController(SeatingChartService seatingChartService) {
        this.seatingChartService = seatingChartService;
    }

    // 獲取所有座位資料
    @GetMapping
    public ResponseEntity<List<SeatingChart>> getAllSeatingCharts() {
        try {
            List<SeatingChart> charts = seatingChartService.getAllSeatingCharts();
            return ResponseEntity.ok(charts);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根據 floorSeatSeq 查詢座位
    @GetMapping("/{floorSeatSeq}")
    public ResponseEntity<SeatingChart> getSeatingByFloorSeatSeq(@PathVariable int floorSeatSeq) {
        try {
            SeatingChart chart = seatingChartService.getSeatingByFloorSeatSeq(floorSeatSeq);
            if (chart != null) {
                return ResponseEntity.ok(chart);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 新增座位資料
    @PostMapping
    public ResponseEntity<String> createSeatingChart(@RequestBody SeatingChart seatingChart) {
        try {
            int result = seatingChartService.createSeatingChart(seatingChart);
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Seating chart created successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create seating chart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating seating chart: " + e.getMessage());
        }
    }

    // 修改座位資料
    @PutMapping("/{floorSeatSeq}")
    public ResponseEntity<String> updateSeatingChart(@PathVariable int floorSeatSeq, @RequestBody SeatingChart seatingChart) {
        try {
            seatingChart.setFloorSeatSeq(floorSeatSeq);
            int result = seatingChartService.updateSeatingChart(seatingChart);
            if (result > 0) {
                return ResponseEntity.ok("Seating chart updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update seating chart.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating seating chart: " + e.getMessage());
        }
    }

    // 刪除座位資料
    @DeleteMapping("/{floorSeatSeq}")
    public ResponseEntity<String> deleteSeatingChart(@PathVariable int floorSeatSeq) {
        try {
            seatingChartService.deleteSeatingChart(floorSeatSeq);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Seating chart deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting seating chart: " + e.getMessage());
        }
    }
}

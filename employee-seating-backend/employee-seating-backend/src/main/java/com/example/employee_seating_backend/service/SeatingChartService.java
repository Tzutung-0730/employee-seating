package com.example.employee_seating_backend.service;

import com.example.employee_seating_backend.model.SeatingChart;
import com.example.employee_seating_backend.repository.SeatingChartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatingChartService {

    private final SeatingChartRepository seatingChartRepository;

    public SeatingChartService(SeatingChartRepository seatingChartRepository) {
        this.seatingChartRepository = seatingChartRepository;
    }

    public List<SeatingChart> getAllSeatingCharts() {
        return seatingChartRepository.getAllSeatingCharts();
    }

    public SeatingChart getSeatingByFloorSeatSeq(int floorSeatSeq) {
        return seatingChartRepository.getSeatingByFloorSeatSeq(floorSeatSeq);
    }

    public int createSeatingChart(SeatingChart seatingChart) {
        return seatingChartRepository.createSeatingChart(seatingChart);
    }

    public int updateSeatingChart(SeatingChart seatingChart) {
        return seatingChartRepository.updateSeatingChart(seatingChart);
    }

    public void deleteSeatingChart(int floorSeatSeq) {
        seatingChartRepository.deleteSeatingChart(floorSeatSeq);
    }
}

<template>
  <div>
    <h1>員工座位管理系統</h1>
    
    <h2>員工列表</h2>
    <div v-for="employee in employees" :key="employee.empId">
      <p>{{ employee.name }} - <button @click="viewEmployeeDetails(employee.empId)">查看詳細資料</button></p>
    </div>

    <h2>座位列表</h2>
    <div v-for="seat in seatingCharts" :key="seat.floorSeatSeq">
      <p>座位：{{ seat.floorSeatSeq }} - {{ seat.isOccupied ? '已佔用' : '空閒' }}</p>
    </div>
  </div>
</template>

<script>
import { getAllEmployees } from '../api/employeeApi';
import { getAllSeatingCharts } from '../api/seatingChartApi';

export default {
  data() {
    return {
      employees: [],
      seatingCharts: []
    };
  },
  created() {
    this.loadEmployees();
    this.loadSeatingCharts();
  },
  methods: {
    async loadEmployees() {
      try {
        this.employees = await getAllEmployees();
      } catch (error) {
        console.error('Error loading employees:', error);
      }
    },
    async loadSeatingCharts() {
      try {
        this.seatingCharts = await getAllSeatingCharts();
      } catch (error) {
        console.error('Error loading seating charts:', error);
      }
    },
    viewEmployeeDetails(empId) {
      this.$router.push({ name: 'EmployeeDetails', params: { empId } });
    }
  }
};
</script>

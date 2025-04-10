<template>
  <div class="seating-arrangement">
    <h1>員工座位安排</h1>

    <div class="employee-selection">
      <label for="employeeSelect">選擇員工：</label>
      <select v-model="selectedEmpId" id="employeeSelect">
        <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
          {{ emp.name }} (員編: {{ emp.empId }})
        </option>
      </select>
    </div>

    <div class="seats">
      <div v-for="seat in seatingCharts" :key="seat.seatNo" class="seat-container">
        <button
          :class="getSeatClass(seat)"
          @click="selectSeat(seat)"
          :disabled="seat.isOccupied"
        >
        {{ seat.floorNo }} 樓：座位{{ seat.seatNo }} {{ seat.isOccupied ? '[ 員編:' + seat.empId + ' ]' : '' }}
        </button>
      </div>
    </div>

    <div class="submit-btn">
      <button @click="submitArrangement">送出</button>
    </div>
  </div>
</template>

<script>
import './SeatArrangement.css';

export default {
  data() {
    return {
      seatingCharts: [],  // 儲存所有樓層的座位資料
      employees: [],  // 儲存所有員工資料
      selectedSeat: null,  // 被選中的座位
      selectedEmpId: '',  // 被選中的員工編號
    };
  },

  created() {
    this.loadEmployees();
    this.loadSeatingCharts();
  },

  methods: {
    loadEmployees() {
      this.$apiService
        .get(this.$employees.getAllEmployees)
        .then((res) => {
          this.employees = res;
          console.log("employees", this.employees);
        })
        .catch(() => {
          // Handle error here
        });
    },
    
    loadSeatingCharts() {
      this.$apiService
        .get(this.$seatingCharts.getAllSeatingCharts)
        .then((res) => {
          this.seatingCharts = res;
          console.log("seatingCharts", this.seatingCharts);
        })
        .catch(() => {
          // Handle error here
        });
    },

    // Method to get the class for each seat based on the seat status
    getSeatClass(seat) {
      if (seat.isOccupied) {
        return 'occupied';
      } else if (seat.empId && seat.empId === this.selectedEmpId) {
        return 'selected';
      }
      return 'available';
    },

    // Method to handle seat selection
    selectSeat(seat) {
      if (!seat.isOccupied) {
        this.selectedSeat = seat;
      }
    },

    // Method to handle form submission
    submitArrangement() {
      if (this.selectedSeat && this.selectedEmpId) {
        // Perform the submit logic here, e.g., assign employee to selected seat
        console.log("Selected Seat:", this.selectedSeat);
        console.log("Selected Employee ID:", this.selectedEmpId);
        // Make an API call to update the seat assignment
      } else {
        alert("請選擇員工和座位！");
      }
    }
  }
};
</script>

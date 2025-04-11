<template>
  <div class="seating-arrangement">
    <h1 class="title">員工座位安排</h1>

    <div class="employee-selection">
      <label for="employeeSelect">選擇員工：</label>
      <select v-model="selectedEmployee.empId" id="employeeSelect" @focus="openEmployeeSelect" @change="selectEmployee($event)" ref="employeeSelect">
        <option v-for="emp in filteredEmployees" :key="emp.empId" :value="emp.empId">
          {{ emp.name }} (員編: {{ emp.empId }})
        </option>
      </select>
    </div>

    <div class="seats">
      <div v-for="seat in seatingCharts" :key="seat.seatNo" class="seat-container">
        <button
          :class="getSeatClass(seat)"
          @click="selectSeat(seat)"
        >
          {{ seat.floorNo }} 樓：座位{{ seat.seatNo }} {{ seat.isOccupied ? '[ 員編:' + seat.empId + ' ]' : '' }}
        </button>
      </div>
    </div>

    <div class="seat-status-indicators">
      <div class="status-item">
        <span class="status-indicator available"></span>
        <span class="status-text">空位</span>
      </div>
      <div class="status-item">
        <span class="status-indicator occupied"></span>
        <span class="status-text">已佔用</span>
      </div>
      <div class="status-item">
        <span class="status-indicator selected"></span>
        <span class="status-text">請選擇</span>
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
      seatingCharts: [],
      employees: [],
      selectedSeat: null,
      selectedEmployee: { empId: '', name: '', email: '' },
      searchQuery: '',
      isEmployeeSelectOpen: false
    };
  },

  computed: {
    filteredEmployees() {
      return this.employees.filter(emp => 
        (emp.empId.includes(this.searchQuery) || emp.name.includes(this.searchQuery)) && emp.floorSeatSeq === 0
      );
    }
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
        })
        .catch(() => {});
    },

    loadSeatingCharts() {
      this.$apiService
        .get(this.$seatingCharts.getEmployeeSeatAssignments)
        .then((res) => {
          this.seatingCharts = res;
        })
        .catch(() => {});
    },

    getSeatClass(seat) {
      if (seat.isOccupied) {
        return 'occupied';
      }
      if (this.selectedSeat && this.selectedSeat.seatNo === seat.seatNo && this.selectedSeat.floorNo === seat.floorNo) {
        return 'selected';
      }
      return 'available';
    },

    selectSeat(seat) {
      if (seat.isOccupied) {
        this.setSelectedEmployee(seat.empId);

        if (confirm(`該座位已被員工（員編: ${seat.empId}）佔用，是否釋放此座位？`)) {
          this.releaseSeat(seat);
        }
      } else {
        if (this.selectedSeat && this.selectedSeat.seatNo === seat.seatNo && this.selectedSeat.floorNo === seat.floorNo) {
          this.selectedSeat = null;
        } else {
          this.selectedSeat = seat; 
        }
      }
    },

    selectEmployee(event) {
      const empId = event.target.value;
      if (empId) {
        this.setSelectedEmployee(empId);
      } else {
        this.selectedEmployee = { empId: '', name: '', email: '' };
      }
    },

    setSelectedEmployee(empId) {
      const selectedEmp = this.employees.find(emp => emp.empId === empId);
      if (selectedEmp) {
        this.selectedEmployee = { 
          empId: selectedEmp.empId, 
          name: selectedEmp.name, 
          email: selectedEmp.email 
        };
      }
    },

    submitArrangement() {
      if (!this.selectedEmployee.empId || !this.selectedSeat) {
        if (!this.selectedEmployee.empId) {
          // 聚焦選擇員工並打開選單
          this.$refs.employeeSelect.focus();
          this.isEmployeeSelectOpen = true;
          alert("請選擇員工！");
        } else if (!this.selectedSeat) {
          alert("請選擇座位！");
        }
        return;
      }

      const request = {
        empId: this.selectedEmployee.empId,
        name: this.selectedEmployee.name,
        email: this.selectedEmployee.email,
        floorSeatSeq: this.selectedSeat.floorSeatSeq
      };

      this.$apiService
        .put(this.$employees.updateEmployee(this.selectedEmployee.empId), request)
        .then(() => {
          alert("座位安排更新成功！");
          this.loadSeatingCharts();
          this.selectedSeat = null;
          this.selectedEmployee = { empId: '', name: '', email: '' }; 
          this.$refs.employeeSelect.value = "";
        })
        .catch((error) => {
          console.error("更新失敗:", error);
          alert("更新失敗：" + error.message);
        });
    },

    openEmployeeSelect() {
      this.isEmployeeSelectOpen = true;
    },

    releaseSeat() {
      const request = {
          empId: this.selectedEmployee.empId,
          name: this.selectedEmployee.name,
          email: this.selectedEmployee.email,
          floorSeatSeq: null
        };

      this.$apiService
        .put(this.$employees.updateEmployee(this.selectedEmployee.empId), request)
        .then(() => {
          alert("座位已成功釋放！");
          this.selectedEmployee = { empId: '', name: '', email: '' };
          this.selectedSeat = null;
          this.$refs.employeeSelect.value = "";
          this.loadSeatingCharts(); 
        })
        .catch((error) => {
          console.error("釋放座位失敗:", error);
          alert("釋放座位失敗：" + error.message);
        });
    }
  }
};
</script>
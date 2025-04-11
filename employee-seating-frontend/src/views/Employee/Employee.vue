<template>
  <div class="employee-container">
    <h1>員工資料管理</h1>
    <input type="text" v-model="searchQuery" placeholder="請輸入員編或姓名" class="search-input" />
    <button @click="showAddEmployeeDialog" class="btn add-btn">
      <i class="pi pi-plus"></i> 新增員工
    </button>
    <table class="employee-table">
      <thead>
        <tr>
          <th>員編</th>
          <th>姓名</th>
          <th>Email</th>
          <th>座位</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="employee in filteredEmployees" :key="employee.empId">
          <td>{{ employee.empId }}</td>
          <td>{{ employee.name }}</td>
          <td>{{ employee.email }}</td>
          <td>
            <button :class="['seat-btn', employee.floorSeatSeq === 0 ? 'unassigned' : 'assigned']">
              {{ getSeatInfo(employee.floorSeatSeq) }}
            </button>
          </td>
          <td>
            <button @click="showEditEmployeeDialog(employee)" class="btn edit-btn">
              <i class="pi pi-pencil"></i> 編輯
            </button>
            <button @click="deleteEmployee(employee)" class="btn delete-btn">
              <i class="pi pi-trash"></i> 刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="isEmployeeDialogVisible" class="modal">
      <div class="modal-content">
        <h3>{{ isEdit ? '編輯員工' : '新增員工' }}</h3>

        <label>員編:</label>
        <input type="text" v-model="employeeForm.empId" :disabled="isEdit" placeholder="員編" />

        <label>姓名:</label>
        <input type="text" v-model="employeeForm.name" placeholder="姓名" />

        <label>Email:</label>
        <input type="email" v-model="employeeForm.email" placeholder="Email" />

        <label>座位:</label>
        <input type="text" :value="getSeatInfo(employeeForm.floorSeatSeq)" disabled />

        <div class="button-group">
          <button @click="submitEmployeeForm" class="modal-btn confirm-btn"><i class="pi pi-check"></i>確認</button>
          <button @click="closeDialog" class="modal-btn cancel-btn"><i class="pi pi-times"></i>取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import './Employee.css';

export default {
  name: "EmployeePage",
  data() {
    return {
      searchQuery: '',
      employees: [],
      newEmployee: { empId: '', name: '', email: '', floorSeatSeq: 0 },
      isEmployeeDialogVisible: false,
      isEdit: false,
      employeeForm: { empId: '', name: '', email: '', floorSeatSeq: 0 },
      seatingCharts: []
    };
  },
  computed: {
    filteredEmployees() {
      return this.employees.filter(emp => 
        (emp.empId && emp.empId.includes(this.searchQuery)) || 
        (emp.name && emp.name.includes(this.searchQuery))
      );
    }
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
    getSeatInfo(floorSeatSeq) {
      if (floorSeatSeq === 0) {
        return '未分配座位';
      }
      const seat = this.seatingCharts.find(s => s.floorSeatSeq === floorSeatSeq);
      return seat ? `${seat.floorNo}樓: 座位${seat.seatNo}` : '座位資料無法找到';
    },
    showAddEmployeeDialog() {
      this.isEmployeeDialogVisible = true;
      this.isEdit = false;
      this.employeeForm = { empId: '', name: '', email: '', floorSeatSeq: 0 };
    },
    showEditEmployeeDialog(employee) {
      this.isEmployeeDialogVisible = true;
      this.isEdit = true;
      this.employeeForm = { ...employee };
    },
    submitEmployeeForm() {
      if (this.isEdit) {
        const request = {
          empId: this.employeeForm.empId,
          name: this.employeeForm.name,
          email: this.employeeForm.email,
          floorSeatSeq: this.employeeForm.floorSeatSeq,
        };
        this.$apiService
          .put(this.$employees.updateEmployee(this.employeeForm.empId), request)
          .then(() => {
            const index = this.employees.findIndex(emp => emp.empId === this.employeeForm.empId);
            if (index !== -1) {
              this.employees[index] = { ...this.employeeForm };
            }
            alert("員工資料更新成功！");
            this.closeDialog();
          })
          .catch(() => {
            alert("更新員工資料失敗！");
          });
      } else {
        const request = {
          empId: this.employeeForm.empId,
          name: this.employeeForm.name,
          email: this.employeeForm.email,
          floorSeatSeq: null,
        };
        this.$apiService
          .post(this.$employees.createEmployee, request)
          .then((res) => {
            this.employees.push(res);
            alert("員工新增成功！");
            this.closeDialog();
          })
          .catch(() => {
            alert("新增員工失敗！");
          });
      }
    },
    closeDialog() {
      this.isEmployeeDialogVisible = false;
      this.employeeForm = { empId: '', name: '', email: '', floorSeatSeq: null };
      this.isEdit = false;
      this.loadEmployees();
    },
    deleteEmployee(employee) {
      this.$apiService
        .delete(this.$employees.deleteEmployee(employee.empId))
        .then(() => {
          const index = this.employees.indexOf(employee);
          if (index !== -1) {
            this.employees.splice(index, 1);
          }
          alert("員工已刪除！");
        })
        .catch(() => {
          alert("刪除員工失敗！");
        });
    }
  },
  created() {
    this.loadEmployees();
    this.loadSeatingCharts();
  }
};
</script>

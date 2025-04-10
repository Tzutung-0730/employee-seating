<template>
  <div>
    <h1>員工詳細資料</h1>
    <div v-if="employee">
      <p>員工編號：{{ employee.empId }}</p>
      <p>姓名：{{ employee.name }}</p>
      <p>電子郵件：{{ employee.email }}</p>
      <p>座位編號：{{ employee.floorSeatSeq }}</p>
    </div>
  </div>
</template>

<script>
import { getEmployeeById } from '../api/employeeApi';

export default {
  props: ['empId'],
  data() {
    return {
      employee: null
    };
  },
  created() {
    this.loadEmployeeDetails();
  },
  methods: {
    async loadEmployeeDetails() {
      try {
        this.employee = await getEmployeeById(this.empId);
      } catch (error) {
        console.error('Error loading employee details:', error);
      }
    }
  }
};
</script>

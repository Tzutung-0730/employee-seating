import { createRouter, createWebHistory } from 'vue-router';
import SeatArrangement from '../views/SeatArrangement/SeatArrangement.vue';
import Employee from '@/views/Employee/Employee.vue';
import SeatChart from '@/views/SeatChart/SeatChart.vue';

const routes = [
  {
    path: '/',
    name: 'SeatArrangement',
    component: SeatArrangement,
  },
  {
    path: '/employee',
    name: 'Employee',
    component: Employee,
  },
  {
    path: '/seatChart',
    name: 'SeatChart',
    component: SeatChart,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
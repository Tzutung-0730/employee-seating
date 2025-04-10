import { createRouter, createWebHistory } from 'vue-router';
import SeatArrangement from '../views/SeatArrangement/SeatArrangement.vue';

const routes = [
  {
    path: '/',
    name: 'SeatArrangement',
    component: SeatArrangement,
  },
  // 更多路由設置
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
import { createApp } from 'vue';
import App from './App.vue';
import { ApiService } from './services/ApiService';
import { Employees, SeatingCharts } from './config/apiConfig';
import router from './router';

const app = createApp(App);

app.config.globalProperties.$apiService = ApiService; 
app.config.globalProperties.$employees = Employees;
app.config.globalProperties.$seatingCharts = SeatingCharts;

app.use(router).mount('#app');

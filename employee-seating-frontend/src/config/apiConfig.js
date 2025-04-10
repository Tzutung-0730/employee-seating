export const BASE_URL = 'http://localhost:8080';

export const Employees = {
  getAllEmployees: `${BASE_URL}/employees`,
  getEmployeeById: (empId) => `${BASE_URL}/employees/${empId}`,
  createEmployee: `${BASE_URL}/employees`, 
  updateEmployee: (empId) => `${BASE_URL}/employees/${empId}`,
  deleteEmployee: (empId) => `${BASE_URL}/employees/${empId}`,
};

export const SeatingCharts = {
  getAllSeatingCharts: `${BASE_URL}/seating-charts`, 
  getSeatingByFloorSeatSeq: (floorSeatSeq) => `${BASE_URL}/seating-charts/${floorSeatSeq}`,
  createSeatingChart: `${BASE_URL}/seating-charts`,   
  updateSeatingChart: (floorSeatSeq) => `${BASE_URL}/seating-charts/${floorSeatSeq}`,
  deleteSeatingChart: (floorSeatSeq) => `${BASE_URL}/seating-charts/${floorSeatSeq}`,
  updateSeatOccupied: (floorSeatSeq) => `${BASE_URL}/seating-charts/updateOccupied/${floorSeatSeq}`,
  updateSeatAvailable: (floorSeatSeq) => `${BASE_URL}/seating-charts/updateAvailable/${floorSeatSeq}`, 
}
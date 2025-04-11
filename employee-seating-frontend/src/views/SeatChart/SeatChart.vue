<template>
  <div class="seating-container">
    <h1>座位資料管理</h1>
    <input type="text" v-model="searchQuery" placeholder="請輸入樓層或座位號" class="search-input" />
    <button @click="showAddSeatDialog" class="btn add-btn">
      <i class="pi pi-plus"></i> 新增座位
    </button>
    <table class="seating-table">
      <thead>
        <tr>
          <th>樓層</th>
          <th>座位號</th>
          <th>佔用狀態</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="seat in filteredSeats" :key="seat.floorSeatSeq">
          <td>{{ seat.floorNo }}</td>
          <td>{{ seat.seatNo }}</td>
          <td>
            <button :class="['seat-btn', seat.occupied ? 'unassigned' : 'assigned']">
              {{ seat.occupied ? '佔用' : '空閒' }}
            </button>
          </td>
          <!-- <td>{{ seat.occupied ? '佔用' : '空閒' }}</td> -->
          <td>
            <button @click="showEditSeatDialog(seat)" class="btn edit-btn">
              <i class="pi pi-pencil"></i> 編輯
            </button>
            <button @click="deleteSeat(seat)" class="btn delete-btn">
              <i class="pi pi-trash"></i> 刪除
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="isSeatDialogVisible" class="smodal">
      <div class="smodal-content">
        <h3>{{ isEdit ? '編輯座位' : '新增座位' }}</h3>

        <label>樓層:</label>
        <input type="number" v-model="seatForm.floorNo" placeholder="輸入樓層" />

        <label>座位:</label>
        <input type="number" v-model="seatForm.seatNo" placeholder="輸入座位號" />

        <label>狀態:</label>
        <input type="text" value="未占用" disabled />

        <div class="button-group">
          <button @click="submitSeatForm" class="modal-btn confirm-btn">
            <i class="pi pi-check"></i>送出
          </button>
          <button @click="closeDialog" class="modal-btn cancel-btn">
            <i class="pi pi-times"></i>取消
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import './SeatChart.css';

export default {
  name: "SeatChartPage",
  data() {
    return {
      searchQuery: '',
      seatingCharts: [],
      isSeatDialogVisible: false,
      seatForm: { floorNo: '', seatNo: '', isOccupied: false },
      isEdit: false,
      currentSeat: null,
    };
  },
  computed: {
    filteredSeats() {
      return this.seatingCharts.filter(seat =>
        (seat.floorNo && seat.floorNo.toString().includes(this.searchQuery)) ||
        (seat.seatNo && seat.seatNo.toString().includes(this.searchQuery))
      );
    }
  },
  methods: {
    loadSeatingCharts() {
      this.$apiService
        .get(this.$seatingCharts.getAllSeatingCharts)
        .then((res) => {
          this.seatingCharts = res;
        })
        .catch(() => {});
    },

    showAddSeatDialog() {
      this.isSeatDialogVisible = true;
      this.isEdit = false;
      this.seatForm = { floorNo: '', seatNo: '', isOccupied: false };
    },

    showEditSeatDialog(seat) {
      this.isSeatDialogVisible = true;
      this.isEdit = true;
      this.currentSeat = seat;
      this.seatForm = { ...seat };
    },

    submitSeatForm() {
      if (this.isEdit) {
        this.$apiService
          .put(this.$seatingCharts.updateSeatingChart(this.currentSeat.floorSeatSeq), this.seatForm)
          .then(() => {
            const index = this.seatingCharts.findIndex(s => s.floorSeatSeq === this.currentSeat.floorSeatSeq);
            if (index !== -1) {
              this.seatingCharts[index] = { ...this.seatForm };
            }
            alert("座位更新成功！");
            this.closeDialog();
          })
          .catch((error) => {
            console.error("Error updating seat:", error);
            alert("更新座位失敗！");
          });
      } else {
        this.$apiService
          .post(this.$seatingCharts.createSeatingChart, this.seatForm)
          .then((res) => {
            this.seatingCharts.push(res);
            alert("座位新增成功！");
            this.closeDialog();
          })
          .catch((error) => {
            console.error("Error adding seat:", error);
            alert("新增座位失敗！");
          });
      }
    },

    closeDialog() {
      this.isSeatDialogVisible = false;
      this.seatForm = { floorNo: '', seatNo: '', isOccupied: false };
      this.currentSeat = null;
    },

    deleteSeat(seat) {
      this.$apiService
        .delete(this.$seatingCharts.deleteSeatingChart(seat.floorSeatSeq))
        .then(() => {
          const index = this.seatingCharts.indexOf(seat);
          if (index !== -1) {
            this.seatingCharts.splice(index, 1);
          }
          alert("座位已刪除！");
        })
        .catch((error) => {
          console.error("Error deleting seat:", error);
          alert("刪除座位失敗！");
        });
    }
  },
  created() {
    this.loadSeatingCharts();
  }
};
</script>

<template>
  <header>
    <nav>
      <ul>
        <li><router-link to="/" class="nav-item">座位安排</router-link></li>
        <li><router-link to="/employee" class="nav-item">員工設定</router-link></li>
        <li><router-link to="/seatChart" class="nav-item">座位設定</router-link></li>
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent'
};
</script>

<style scoped>
@import './HeaderComponent.css';
</style>

<template>
  <div id="app">
    <header-component></header-component>
    <router-view></router-view>
  </div>
</template>

<script>
import headerComponent from './components/Header/HeaderComponent.vue';

export default {
  name: 'App',
  components: {
    headerComponent
  }
};
</script>

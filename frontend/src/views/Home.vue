<template>
  <div class="home">
    <Main/>
    <!-- <Intro/> -->
  </div>
</template>

<script>
// @ is an alias to /src
// import Intro from '@/components/Intro.vue';
import Main from '@/components/Main.vue';

export default {
  name: 'Home',
  components: {
    Main,
    // Intro,
  },
};
</script>
<style>
  /* .home{
    background-color: rgba(0, 0, 0, 0.89);
  } */
</style>

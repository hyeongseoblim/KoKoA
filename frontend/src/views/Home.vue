<template>
  <div class="home">
    <Intro v-if="!isLogin" />
    <Main v-else />
  </div>
</template>

<script>
// @ is an alias to /src
import Main from '@/components/Main.vue';
import Intro from '@/components/Intro.vue';
import { mapState } from 'vuex';

export default {
  name: 'Home',
  components: {
    Main,
    Intro,
  },
  computed: {
    ...mapState(['isLogin']),
  },
};
</script>

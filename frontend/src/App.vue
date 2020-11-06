<template>
  <v-app id="create">
    <!-- KOKOA LOGO -->
    <div id="logo" @click="goHome">
      <img src="@/assets/kokoa.png" alt="KOKOA" style="width: 30vh;">
      <!-- <v-btn v-if="!isLogin" @click="getAuth()">
        <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
        login
      </v-btn> -->
    </div>
    <!-- circular menu -->
    <quick-menu
      class="circular"
      :menu-count="count"
      :icon-class="icons"
      :menu-url-list="list"
      :background-color="backgroundColor"
      :color="color"
      :position="position"
    ></quick-menu>
    <!-- :is-open-new-tab=getIsOpenNewTab @process=print -->
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import quickMenu from 'vue-quick-menu';

export default {
  name: 'App',
  components: {
    quickMenu,
  },
  data() {
    return {
      isLogin: false,
      drawer: false,
      // circular menu settings
      count: 3,
      icons: ['fas fa-book', 'fas fa-history', 'fas fa-book-reader', 'fas fa-sign-out-alt'],
      list: [
        { isLink: true, url: '/note' },
        { isLink: true, url: '/review' },
        // { isLink: true, url: '/foo' },
        { isLink: false },
      ],
      backgroundColor: 'rgb(255, 127, 0)',
      color: '#ffffff',
      position: 'top-right',
      isOpenNewTab: false,
    };
  },

  methods: {
    getAuth() {
      this.$gAuth
        .getAuthCode()
        .then((authCode) => {
          this.$store.dispatch('googleLogin', authCode);
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goHome() {
      this.$router.push('/');
    },
    handleClick(item) {
      this.lastClicked = item;
    },
  },
};
</script>
<style>
#create{
  background-color: rgba(0, 0, 0, 0.89);
}
.circular {
  position: fixed;
  z-index: 999;
}

#logo {
  position: fixed;
  z-index: 999;
  color: rgb(255, 127, 0);
}
</style>

<template>
  <div>
    <div style="color:#fff; font-size:5vw; display: flex; justify-content: center;"
  >Choose Your Artist</div>
    <div class="example-3d">
      <swiper class="swiper" :options="swiperOption">
        <swiper-slide
          v-for="(item, index) in items"
          :key="index"
          :style="{ 'background-image': 'url(' + require(`@/assets${item.img}`) + ')' }"
        ></swiper-slide>
        <!-- <div class="swiper-pagination" slot="pagination"></div> -->
        <div class="swiper-button-prev" slot="button-prev"></div>
        <div class="swiper-button-next" slot="button-next"></div>
      </swiper>
    </div>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in items" :key="index">
          <v-card class="scale ma-3" @click="enter(data.id)">
            <v-img aspect-ratio="1" :src="require(`@/assets` + data.img)" />
            <v-card-title class="temp justify-center">
              <h4>{{ data.title }}</h4></v-card-title>
          </v-card>
        </v-flex>
      </v-layout>
    </v-flex>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';
import channelList from './core/channelList.json';

export default {
  name: 'Channels',
  components: {
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      items: channelList,
      swiperOption: {
        effect: 'coverflow',
        spaceBetween: 30,
        grabCursor: true,
        loop: true,
        centeredSlides: true,
        slidesPerView: 'auto',
        autoplay: {
          delay: 2500,
          disableOnInteraction: false,
        },
        coverflowEffect: {
          rotate: 90,
          stretch: 0,
          depth: 200,
          modifier: 3,
          slideShadows: true,
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
      },
    };
  },
  methods: {
    enter(id) {
      this.$router.push({ name: 'Artist', query: { groupid: String(id) } });
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  // font-family: 'Signika', sans-serif;
  font-family: 'Patua One', cursive;
}
.scale {
  transform: scale(1);
  /* -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1); */
  transition: all 0.3s ease-in-out;
}
.temp{
  background-color: rgba(0, 0, 0, 0.89);
  padding: 1;
  color: white;
}
.scale:hover {
  transform: scale(1.1);
  z-index: 1;
  /* -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -ms-transform: scale(1.2);
  -o-transform: scale(1.2); */
}
.example-3d {
  width: 100%;
  height: 400px;
  padding-top: 50px;
  padding-bottom: 50px;
}
.title {
  font-size: 30px;
  font-family: 'Patua One', cursive;
}
.swiper {
  height: 100%;
  width: 100%;

  .swiper-slide {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 300px;
    height: 300px;
    text-align: center;
    font-weight: bold;
    // font-size: $font-size-huge * 2;
    background-color: #2c8dfb;
    background-position: center;
    background-size: cover;
    // color: $white;
  }

}
</style>

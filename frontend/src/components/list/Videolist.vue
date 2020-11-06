<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">{{ toggled === true ? "Speaking" : "Dictation" }}</h4>
            <h4>{{filters}}</h4>
          </div>
          <toggle-button
            :color="{ checked: '#00FF00', unchecked: '#FF0000', disabled: '#CCCCCC' }"
            :switch-color="{ checked: '#25EF02', unchecked: 'linear-gradient(red, yellow)' }"
             @change="toggled = $event.value"
            :width="100"
            :labels="{ checked: 'Speaking', unchecked: 'Dictation' }"
          />
          <div class="upcoming-contents ma-5">
            <VueSlickCarousel v-bind="settings" v-if='items.length'>
              <img
                v-for="(item, index) in items"
                :src="'http://img.youtube.com/vi/' + item.url + '/0.jpg'"
                :key="index"
                @click="goTraining(index)"
                class="pa-1"
              />
            </VueSlickCarousel>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </section>
</template>
<script>
// import axios from 'axios';
import VueSlickCarousel from 'vue-slick-carousel';
import 'vue-slick-carousel/dist/vue-slick-carousel.css';
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css';
import http from '../../util/http-common';
import channelList from '../core/channelList.json';

export default {
  name: 'Popular',
  components: { VueSlickCarousel },
  props: ['filters'],
  data() {
    return {
      items: [],
      groupid: this.$route.query.groupid,
      teams: channelList,
      team: '',
      page: 0,
      settings: {
        arrows: true,
        infinite: true,
        speed: 3000,
        slidesToShow: 3,
        slidesToScroll: 3,
        // rows: 2,
      },
      modalShow: false,
      toggled: false,
      timerInterval: '',
      correctAnswer: {
        title: 'Loading....',
        html: 'I will close in <b></b> milliseconds.',
        timer: 200,
        timerProgressBar: true,
        willOpen: () => {
          this.$swal.showLoading();
          this.timerInterval = setInterval(() => {
            const content = this.$swal.getContent();
            if (content) {
              const b = content.querySelector('b');
              if (b) {
                b.textContent = this.$swal.getTimerLeft();
              }
            }
          }, 100);
        },
        onClose: () => {
          clearInterval(this.timerInterval);
        },
      },
    };
  },
  async created() {
    this.team = this.teams[this.groupid - 1].title;
    this.items = [];
    const pages = await this.getTotalPages(this.team);
    await this.getTotal(pages, this.team, this.items);
  },
  watch: {
    async filters() {
      // 나중에 여기서 if문에 따라서 멘트를 바꿔야할듯?
      // pages === 0 이면 해당 멤버 영상 X -> 모든 멤버를 불러옵니다.
      //  pages !== 0 이면 해당 멤버 영상 O -> {{member}} 영상을 불러옵니다.
      this.$swal.fire(this.correctAnswer);
      if (this.filters === this.team) {
        this.getStart();
      } else {
        this.items = [];
        const pages = await this.getMemberPages(this.filters);
        if (pages !== 0) {
          await this.getMember(pages, this.filters, this.items);
        } else {
          this.getStart();
        }
      }
    },
  },
  methods: {
    goTraining(index) {
      if (this.toggled) {
        this.goTalk(index);
      } else {
        this.goWrite(index);
      }
    },
    goWrite(index) {
      this.$router.push({ name: 'Write', query: { index: String(index) } });
    },
    goTalk(index) {
      this.$router.push({ name: 'Talk', query: { index: String(index) } });
    },
    async getStart() {
      this.items = [];
      const pages = await this.getTotalPages(this.team);
      await this.getTotal(pages, this.team, this.items);
    },
    getTotalPages(team) {
      return new Promise((resolve) => {
        http.get(`/search/idol/0?groupname=${team}`).then((res) => {
          resolve(res.data.totalPages);
        });
      });
    },
    getMemberPages(data) {
      return new Promise((resolve) => {
        http.get(`/search/member/0?membername=${data}`)
          .then((res) => {
            resolve(res.data.totalPages);
          });
        // .catch(() => {
        //   resolve(-1);
        // });
      });
    },
    async getTotal(page, team, items) {
      for (let i = 0; i < page; i += 1) {
        // eslint-disable-next-line no-await-in-loop
        await http.get(`/search/idol/${i}?groupname=${team}`).then((res) => res.data.content.forEach((element) => {
          items.push(element);
        }));
      }
    },
    async getMember(page, name, items) {
      for (let i = 0; i < page; i += 1) {
        // eslint-disable-next-line no-await-in-loop
        await http.get(`/search/member/${i}?membername=${name}`).then((res) => res.data.content.forEach((element) => {
          items.push(element);
        }));
      }
    },
  },
};
</script>
<style>
*{
  font-family: 'Bangers', cursive;
}
.slick-prev:before {
  color: red !important;
  background-color: #eee;
}
.slick-next:before {
  color: red !important;
  background-color: #eee;
}
.choice img {
  height: 100%;
}
</style>

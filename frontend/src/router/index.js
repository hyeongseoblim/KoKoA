import Vue from 'vue';
import VueRouter from 'vue-router';
import Write from '@/components/training/Write.vue';
import Talk from '@/components/training/Talk.vue';
import Home from '@/views/Home.vue';
import Login from '@/components/user/Login.vue';
import Artist from '@/components/Artist.vue';
import Note from '@/components/user/Note.vue';
import Review from '@/components/user/Review.vue';
import VueYoutube from 'vue-youtube';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import ToggleButton from 'vue-js-toggle-button';
import VueAwesomeSwiper from 'vue-awesome-swiper';
// import 'swiper/swiper-bundle.css';
import 'swiper/css/swiper.css';

Vue.use(VueAwesomeSwiper);
Vue.use(ToggleButton);
Vue.use(VueYoutube);
Vue.use(VueRouter);
Vue.use(VueSweetalert2);
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/Artist',
    name: 'Artist',
    component: Artist,
  },
  {
    path: '/write',
    component: Write,
    name: 'Write',
    props: true,
  },
  {
    path: '/talk',
    component: Talk,
    name: 'Talk',
    props: true,
  },
  {
    path: '/note',
    component: Note,
    name: 'Note',
    props: true,
  },
  {
    path: '/review',
    component: Review,
    name: 'Review',
    props: true,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;

<template>
  <div>
    <full-page :options='options' id='fullpage' ref='fullpage'>
      <div class='section s0'>
        <h3>Learn Korean</h3>
        <h3>with K-pop Artist</h3>
      </div>
      <div class='section s1'>
        <h3>select your Artist</h3>
      </div>
      <div class='section s2'>
        <div class='slide'>
          <h3>Speaking</h3>
        </div>
        <div class='slide'>
          <h3>Check your pronunciation</h3>
        </div>
      </div>
      <div class='section s3'>
        <div class='slide'>
          <h3>Listening</h3>
          <h3>&</h3>
          <h3>Writing</h3>
        </div>
        <div class='slide'>
          <h3>Matching Sentences</h3>
        </div>
      </div>
      <div class='section'>
        <h3>Let's go to learn</h3>
        <h3>with K-pop Artist</h3>
        <button>login</button>
      </div>
    </full-page>

    <ul class='actions'>
      <!-- <li @click='$refs.fullpage.api.moveSectionDown()' class='actions-button'>Down</li>
      <li @click='$refs.fullpage.api.moveSectionUp()' class='actions-button'>Up</li> -->
      <li @click='$refs.fullpage.api.silentMoveTo(5, 5)' class='actions-button'>go to learn 📚</li>
    </ul>
  </div>
</template>
<script>
import Vue from 'vue';
import 'fullpage.js/vendors/scrolloverflow'; // Optional. When using scrollOverflow:true
// import './fullpage.scrollHorizontally.min'; // Optional. When using fullpage extensions
import VueFullPage from 'vue-fullpage.js';

Vue.use(VueFullPage);

export default {
  name: 'Intro',
  data() {
    return {
      options: {
        afterLoad: this.afterLoad,
        navigation: true,
        anchors: ['page1', 'page2', 'page3'],
        sectionsColor: [
          '#41b883',
          '#ff5f45',
          '#0798ec',
          '#fec401',
          '#1bcee6',
          '#ee1a59',
          '#2c3e4f',
          '#ba5be9',
          '#b4b8ab',
        ],
      },
    };
  },
  methods: {
    afterLoad(origin, destination, direction) {
      console.log('After load....');
      console.log(destination);
      console.log(direction);
    },
    addSection() {
      const newSectionNumber = document.querySelectorAll('.fp-section').length + 1;

      // creating the section div
      const section = document.createElement('div');
      section.className = 'section';
      section.innerHTML = `<h3>Section ${newSectionNumber}</h3>`;

      // adding section
      document.querySelector('#fullpage').appendChild(section);

      // creating the section menu element
      const sectionMenuItem = document.createElement('li');
      sectionMenuItem.setAttribute('data-menuanchor', 'page');
      sectionMenuItem.append(newSectionNumber);
      sectionMenuItem.innerHTML = `<a href='#page${newSectionNumber}'>Section${newSectionNumber}</a>`;

      // adding anchor for the section
      this.options.anchors.push(`page${newSectionNumber}`);

      this.$refs.fullpage.build();
    },

    removeSection() {
      const sections = document.querySelector('#fullpage').querySelectorAll('.fp-section');
      const lastSection = sections[sections.length - 1];

      // removing the last section
      lastSection.parentNode.removeChild(lastSection);

      // removing the last anchor
      this.options.anchors.pop();

      // removing the last item on the sections menu
      const sectionsMenuItems = document.querySelectorAll('#menu li');
      const lastItem = sectionsMenuItems[sectionsMenuItems.length - 1];
      lastItem.parentNode.removeChild(lastItem);
    },
  },
};
</script>

<style>
@charset 'ISO-8859-1';
/* Reset CSS
 * --------------------------------------- */
body,
div,
dl,
dt,
dd,
ul,
ol,
li,
h1,
h2,
h3,
h4,
h5,
h6,
pre,
form,
fieldset,
input,
textarea,
p,
blockquote,
th,
td {
  padding: 0;
  margin: 0;
}
a {
  text-decoration: none;
}
table {
  border-spacing: 0;
}
fieldset,
img {
  border: 0;
}
address,
caption,
cite,
code,
dfn,
em,
strong,
th,
const {
  font-weight: normal;
  font-style: normal;
}
strong {
  font-weight: bold;
}
ol,
ul {
  list-style: none;
  margin: 0;
  padding: 0;
}
caption,
th {
  text-align: left;
}
h1,
h2,
h3,
h4,
h5,
h6 {
  font-weight: normal;
  font-size: 100%;
  margin: 0;
  padding: 0;
  /* font-family: 'Space Grotesk', sans-serif; */
  font-family: 'Merriweather', serif;
}
q:before,
q:after {
  content: '';
}
abbr,
acronym {
  border: 0;
}
* {
  -webkit-box-sizing: border-box; /* Safari<=5 Android<=3 */
  -moz-box-sizing: border-box; /* <=28 */
  box-sizing: border-box;
}

/* Custom
 * --------------------------------------- */
body {
  font-family: arial, helvetica;
}
.section {
  position: relative;
  text-align: center;
}
.s0 {
    background: url(https://www.c-mw.net/wp-content/uploads/2017/06/Seoul-City-Landscape-CNEW-1600x1000.jpeg);
    /* background: url(https://s3-eu-west-1.amazonaws.com/eflanguagesblog/wp-content/uploads/2018/12/05222611/Why-is-the-world-obsessed-with-all-things-Korean.jpg); */
    /* background-image: url('../assets/korea1.jpg'); */
    background-size: cover;
    position: relative;
}
.s0::before{
    content: "";
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: #000;
}
.section h3{
    position: relative;
    font-size: 6rem;
}
.s1 {
    background: url(https://static-assets.hallyulife.com/kpop-girl-group-reputation-may-2020.jpg);
    background-size: cover;
    position: relative;
}
.s1::before{
    content: "";
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: #000;
}
.s2 {
    background: url(https://ieltsprofi.com/blog/wp-content/uploads/2020/08/speaking_part1-scaled-6.jpg);
    background-size: cover;
    position: relative;
}
.s2::before{
    content: "";
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: #000;
}
.s3 {
    background: url(https://d2wldp4wpenmih.cloudfront.net/wp-content/uploads/2018/11/effective-listening-skills.jpg);
    background-size: cover;
    position: relative;
}
.s3::before{
    content: "";
    opacity: 0.5;
    position: absolute;
    top: 0px;
    left: 0px;
    right: 0px;
    bottom: 0px;
    background-color: #000;
}
#section-1 h2 {
  color: #fff;
  font-size: 10em;
  font-weight: 900;
}
#section-1 h1 {
  font-size: 2em;
  font-weight: 100;
  -webkit-font-smoothing: antialiased;
  -moz-font-smoothing: antialiased;
  margin: 1.5em auto 1em auto;
  color: #35495e;
  padding-right: 30px;
  padding-left: 30px;
}
#section-1 li {
  display: inline-block;
  margin: 1.25em 0.3em;
}
.section-1-button {
  padding: 0.93em 1.87em;
  background: #35495e;
  border-radius: 5px;
  display: block;
  color: #fff;
}
h3 {
  font-size: 5em;
  text-align: center;
  color: #fff;
  font-weight: bold;
}
#logo {
  position: fixed;
  top: 20px;
  left: 20px;
  color: #fff;
  font-weight: bold;
  z-index: 99;
  font-size: 1.9em;
  -webkit-font-smoothing: antialiased;
  -moz-font-smoothing: antialiased;
}

/* Menu
 * --------------------------------------- */
#menu-line {
  position: absolute;
  bottom: -4px;
  left: 0;
  width: 159px;
  height: 2px;
  background: #fff;
}

#menu {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 70;
  -webkit-font-smoothing: antialiased;
  -moz-font-smoothing: antialiased;
  letter-spacing: 1px;
  font-size: 1.1em;
}
#menu li {
  display: inline-block;
  margin: 10px 0px;
  position: relative;
}
#menu a {
  color: #fff;
  padding: 0 1.1em 1.1em 1.1em;
}
#menu li.active a:after {
  content: '';
  margin: 0 1.1em 0 1.1em;
  height: 2px;
  background: #fff;
  display: block;
  position: absolute;
  bottom: -6px;
  left: 0;
  right: 0;
  display: block;
}

/* Actions buttons
 * --------------------------------------- */
.actions {
  position: fixed;
  bottom: 2%;
  margin: 0 auto;
  z-index: 99;
  left: 0;
  right: 0;
  text-align: center;
}
.actions li {
  display: inline-block;
  margin: 0.1em 0.1em;
  font-size: 2.5em;
  font-family: 'Lobster', cursive;
}
.actions-button {
  padding: 0.23em 1.07em;
  background: rgba(245, 128, 11, 0.47);
  border-radius: 5px;
  display: block;
  color: #fff;
  cursor: pointer;
}

/* Twitter
 * --------------------------------------- */
.twitter-share i {
  vertical-align: middle;
  position: relative;
  top: 2px;
  display: inline-block;
  width: 38px;
  height: 14px;
  color: white;
  top: -4px;
  left: -2px;
  fill: #fff;
}
.twitter-share svg {
  height: 40px;
  margin-top: -10px;
}
.view-github {
  position: fixed;
  top: 20px;
  left: 20px;
  z-index: 999;
  padding: 0.93em 1.87em;
  background: #35495e;
  border-radius: 5px;
  display: block;
  color: #fff;
}
</style>

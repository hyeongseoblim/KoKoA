<template>
  <v-row :style="{ marginTop: path === '/talk' ? '100px' : '0px' }" @click="closeOverlay">
    <v-col class="youtubeContainer" cols="12" lg="8">
      <div class="d-flex justify-center youtube pa-5">
        <youtube
          @playing="playing"
          @ended="ended"
          :video-id="url"
          ref="youtube"
          :player-vars="playerVars"
          flex
          width="100%"
          height="550"
        ></youtube>
        <div
          class="middle d-flex flex-column justify-space-around eng"
          :style="{
            backgroundColor: path === '/talk' ? '#1C1C1C' : 'lightgoldenrodyellow',
            opacity: screen === false ? 0 : 0.9
          }"
        >
          <div
            class="mt-auto"
            style="font-size: calc(1vw + 40px);"
            :style="{ color: path !== '/talk' ? '#1C1C1C' : 'lightgoldenrodyellow' }"
          >
            | How to Use |
          </div>

          <div class="ma-auto mt-8">
            <img v-if="path === '/talk'" src="@/assets/screenspeak-sephia.gif" style="width:90%" />
            <img v-else src="@/assets/screenspeak-neg.gif" style="width:67%" />
          </div>
        </div>
      </div>
      <div style="float:right;">
        <v-switch v-model="autoMode"  dark
        label="mode" color="orange" v-show="!notemode" class="custom-red"
          ><template v-slot:label>
            <span v-if="autoMode" class="input__label">advanced</span>
            <span v-else class="input__label">basic</span>
          </template>
        </v-switch>
      </div>
      <div class="d-flex justify-space-around mt-5" v-if="!autoMode">
        <v-btn v-if="this.current !== 0" @click="previous" icon>
          <v-icon color="white" style="font-size: 35px;">
            fas fa-backward
          </v-icon>
        </v-btn>
        <span v-else></span>
        <v-btn v-if="!pause" @click="playVideo" class="stickypink" icon>
          <img v-show="!screen" src="@/assets/play-pink.gif" style="width:70px;margin:0.2em;" />

          <v-icon v-show="screen" style="font-size: 45px; margin:0.2em">
            mdi-replay
          </v-icon>
          <span
            v-show="!screen"
            class="eng stickypink"
            :class="{ note: notemode }"
            style="font-size: 2em;"
            >PLAY</span
          >
          <span
            v-show="screen"
            class="eng stickypink"
            :class="{ note: notemode }"
            style="font-size: 2em;"
            >REPLAY</span
          >
        </v-btn>
        <v-btn v-else icon @click="pauseVideo">
          <v-icon class="stickypink" style="font-size:45px;">far fa-stop-circle</v-icon>
        </v-btn>
        <v-btn v-if="this.current !== this.video.length - 1" @click="next" icon>
          <v-icon color="white" style="font-size: 35px;">
            fas fa-forward
          </v-icon>
        </v-btn>
        <span v-else></span>
      </div>

      <!-- 자동재생일때 버튼 -->
      <div class="d-flex justify-space-around mt-5" v-else-if="autoMode">
        <span></span>
        <v-btn v-if="!pauseFlag" @click="playAdvanced" class="stickypink" icon>
          <img src="@/assets/play-pink.gif" style="width:70px;margin:0.2em;" />

          <span class="eng stickypink" :class="{ note: notemode }" style="font-size: 2em;"
            >PLAY</span
          >
        </v-btn>

        <v-btn v-else @click="stopAdvanced" class="stickypink" icon>
          <v-icon style="font-size: 45px; margin:0.2em">
            mdi-pause
          </v-icon>
          <span class="eng stickypink" :class="{ note: notemode }" style="font-size: 2em;"
            >PAUSE &amp; practice</span
          >
        </v-btn>
        <v-btn v-if="!pauseFlag" @click="playVideo" class="stickypink" icon>
          <v-icon style="font-size: 45px; margin:0.2em">
            mdi-replay
          </v-icon>
          <span class="eng stickypink" :class="{ note: notemode }" style="font-size: 2em;"
            >REPLAY</span
          >
        </v-btn>
        <span v-else></span>
      </div>
      <div
        class="myTitle d-flex justify-space-around my-5 mx-5"
        :class="{ note: notemode }"
        style="font-size:2em"
      >
        {{ answer }}
      </div>
      <div class="myTitle d-flex justify-space-around my-5 mx-5" :class="{ note: notemode }">
        {{ answerEng }}
      </div>
    </v-col>
    <!-- right side -->
    <v-col cols="12" lg="4">
      <v-row class="d-flex justify-center pa-5">
        <v-col cols="12">
          <div>
            <Record @child-event="receiveText" />
          </div>
          <!-- 나의 발음 -->
          <h2
            class="myTitle my-5"
            id="result"
            :class="{ note: notemode }"
            :style="{ 'border-color': notemode ? 'black' : 'white' }"
          ></h2>
          <div v-if="path === '/talk'" class="d-flex justify-space-around">
            <v-btn
              class="mb-5 eng stickypink"
              text
              icon
              style="font-size:20px;"
              @click="insertNote"
            >
              <v-icon class="mr-3" large>mdi-clipboard-edit-outline</v-icon>
              Add to Note
            </v-btn>
          </div>

          <div class="py-5 dictionary" v-for="(item, index) in dict" :key="index">
            <h4 class="kor stickypink" style="display:inline">
              {{ item.kor }}
            </h4>
            <h5 class="eng note" style="display: inline">(eng) {{ item.eng }}</h5>
            <div class="eng note">
              {{ item.dfn }}
            </div>
          </div>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>
<script>
import { mapActions } from 'vuex';
import Record from './Record.vue';
import http from '../../util/http-common';

export default {
  name: 'Talk',
  components: {
    Record,
  },
  props: ['notemode', 'noteitem', 'noteoverlay'],
  async created() {
    if (!this.notemode) {
      await this.getData();
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[0].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
    } else {
      this.id = this.noteitem.videoid;
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
      });
      this.video.push({
        starttime: this.noteitem.starttime,
        endtime: this.noteitem.endtime,
        kor: this.noteitem.content,
        eng: this.noteitem.engcontent,
        subtitleid: this.noteitem.subtitleid,
        engsubtitleid: this.noteitem.engsubtitleid,
      });
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[0].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
    }
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  data() {
    return {
      pause: false,
      path: this.$route.path,
      screen: false,
      id: this.$route.query.index,
      speechText: '',
      url: '',
      current: 0,
      video: [],
      playerVars: {
        modestbranding: 1,
        controls: 0,
        loop: 1,
        fs: 0,
        rel: 1,
        showinfo: 0,
        playlist: '',
        cc_load_policy: 0,
      },
      answer: '',
      answerEng: '',
      answerTrim: '',
      romaza: '',
      dict: [],
      overlay: this.$store.state.overlayTalk,
      zIndex: 10,
      flag: false,
      settimer: '',
      starttimer: '',
      autoMode: false,
      startFlag: false,
      retryFlag: false,
      moveFlag: false,
      pauseFlag: false,
    };
  },
  mounted() {
    if (this.noteoverlay || !this.overlay) this.closeOverlay();
  },
  methods: {
    ...mapActions(['overlayTalk']),
    playing() {
      this.screen = false;
      this.pause = true;
    },
    ended() {
      this.screen = true;
      this.pause = false;
      this.playCheck();
    },
    play() {
      const start = this.timer(this.video[this.current].starttime);
      const end = this.timer(this.video[this.current].endtime);

      this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
      this.retryFlag = true;
      window.clearTimeout(this.settimer);
    },
    playCheck() {
      const end = this.timer(this.video[this.current].endtime);
      this.player.loadVideoById({
        videoId: this.url,
        startSeconds: end,
        suggestedQuality: 'default',
      });
      setTimeout(() => {
        this.player.pauseVideo();
      }, 500);
    },
    playVideo() {
      this.screen = false;
      this.pauseFlag = false;
      this.play();
    },
    timer(input) {
      const hms = input.replace(/'/g, '');
      const a = hms.split(':');
      const s = a[2].split(',');
      let ms = Number(a[0] * 60 * 60) + Number(a[1] * 60) + Number(s[0]) + Number(s[1] / 1000);
      if (ms === 0) {
        ms += 0.001;
      }
      return ms;
    },
    previous() {
      if (this.current > 0) {
        if (this.autoMode) {
          this.retryFlag = true;
          this.moveFlag = true;
        }
        this.current -= 1;
      }
    },
    next() {
      if (this.current < this.video.length - 1) {
        if (this.autoMode) {
          this.retryFlag = true;
          this.moveFlag = true;
        }
        this.current += 1;
      }
    },
    async receiveText(text) {
      this.dict = [];
      const tmp = this.answer;
      this.answerTrim = tmp
        .replace(/[&/\\#,+\-()$~%@.'":*?!<>{}[]]/g, ' ')
        .replace('[', '')
        .replace(']', '');
      await http.get('/subtitle/roma', { params: { word: this.answerTrim } }).then((res) => {
        this.romaza = res.data;
      });
      this.speechText = text.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      const { subtitleid } = this.video[this.current];
      await http.get('/subtitle/dict', { params: { subtitleid } }).then((res) => {
        for (let i = 0; i < res.data.length; i += 1) {
          if (res.data[i].dfn !== 'Cannot find the meaning') {
            this.dict.push(res.data[i]);
          }
        }
      });
    },
    async getData() {
      this.video = [];
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
        for (let i = 0; i < res.data.Korean.length; i += 1) {
          this.video.push({
            starttime: res.data.Korean[i].starttime,
            endtime: res.data.Korean[i].endtime,
            eng: res.data.English[i].content,
            kor: res.data.Korean[i].content,
            subtitleid: res.data.Korean[i].id,
            engsubtitleid: res.data.English[i].id,
          });
        }
      });
    },
    insertNote() {
      const fd = new FormData();
      fd.append('email', this.$store.state.email);
      fd.append('subtitleid', this.video[this.current].subtitleid);
      fd.append('engsubtitleid', this.video[this.current].engsubtitleid);
      fd.append('type', 0);
      fd.append('videoid', this.id);
      http.post('/note/insert/', fd).then(() => {});
      this.$swal.fire({
        icon: 'success',
        title: '<span style="color:white">Saved</span>',
        timer: 2000,
        background: '#1C1C1C',
        backdrop: 'rgba(0,0,0,0.89)',
      });
    },
    closeOverlay() {
      const OVERLAY = document.querySelector('.overlay');
      OVERLAY.style.opacity = 0;
      this.overlay = false;
      if (this.flag) {
        this.overlay = !this.overlay;
        OVERLAY.style.opacity = 1.0;
        this.flag = false;
      }
      this.$store.dispatch('overlayTalk');
    },
    question() {
      this.flag = true;
    },
    async getCurrentTime() {
      return this.$refs.youtube.player.getCurrentTime();
    },
    subAdvanced() {
      if (this.moveFlag) return;
      this.getCurrentTime().then((res) => {
        this.settimer = window.setTimeout(() => {
          this.current += 1;
        }, this.timer(this.video[this.current].endtime) * 1000 - res * 1000);
      });
    },
    playAdvanced() {
      if (this.pauseFlag) return;
      let start = 0.001;
      if (this.retryFlag || this.moveFlag) {
        this.resumeAdvanced(this.retryFlag);
        this.retryFlag = false;
        this.moveFlag = false;
        this.settimer = window.setTimeout(() => {
          this.current += 1;
        }, this.timer(this.video[this.current].endtime) * 1000
        - this.timer(this.video[this.current].starttime) * 1000);
      } else {
        this.getCurrentTime().then((res) => {
          start = res;
          this.resumeAdvanced(this.retryFlag);
          this.retryFlag = false;
          this.settimer = window.setTimeout(() => {
            this.current += 1;
          }, this.timer(this.video[this.current].endtime) * 1000 - start * 1000);
        });
      }
      this.startFlag = true;
      this.pauseFlag = true;
    },
    stopAdvanced() {
      window.clearTimeout(this.settimer);
      this.$refs.youtube.player.pauseVideo();
      this.pauseFlag = false;
    },
    resumeAdvanced(retryFlag) {
      if (retryFlag) {
        this.player.loadVideoById({
          videoId: this.url,
          startSeconds: this.timer(this.video[this.current].starttime),
          endSeconds: this.timer(this.video[this.video.length - 1].endtime),
          suggestedQuality: 'default',
        });
      } else {
        this.$refs.youtube.player.playVideo();
      }
    },
    playMode() {
      if (this.autoMode) {
        if (this.moveFlag) this.playAdvanced();
        else if (this.startFlag) this.subAdvanced();
        else this.playAdvanced();
      } else this.playVideo();
    },
    pauseVideo() {
      this.player.pauseVideo();
      this.pause = false;
      this.screen = true;
    },
  },
  watch: {
    current() {
      this.answer = '';
      this.dict = '';
      this.answer = this.video[this.current].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[this.current].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.screen = false;
      this.playMode();
    },
    speechText() {
      const answerTrimTrim = this.answerTrim.trim();
      const speechTextTrim = this.speechText;
      const pos = document.getElementById('result');
      pos.innerHTML = '';
      let text;
      let container;
      let i;
      for (i = 0; i < answerTrimTrim.length; i += 1) {
        container = document.createElement('font');
        if (speechTextTrim.length > i) {
          if (speechTextTrim.charAt(i) === answerTrimTrim.charAt(i)) {
            text = document.createTextNode(speechTextTrim.charAt(i));
            if (this.notemode) {
              container.style.color = 'black';
            } else {
              container.style.color = 'white';
            }
          } else {
            text = document.createTextNode(speechTextTrim.charAt(i));
            container.style.color = 'rgb(255, 127, 0)';
          }
        } else {
          text = document.createTextNode(speechTextTrim.charAt(i));
          container.style.color = 'rgb(255, 127, 0)';
          container.appendChild(text);
          pos.appendChild(container);
          break;
        }
        container.appendChild(text);
        pos.appendChild(container);
      }
      const left = speechTextTrim.substr(i);
      for (i = 0; i < left.length; i += 1) {
        container = document.createElement('font');
        text = document.createTextNode(left.charAt(i));
        container.style.color = 'rgb(255, 127, 0)';
        container.appendChild(text);
        pos.appendChild(container);
      }
      container.appendChild(document.createElement('br'));
      container.appendChild(document.createElement('br'));

      container = document.createElement('font');
      text = document.createTextNode(`[ ${this.romaza} ]`);
      if (this.notemode) {
        container.style.color = 'black';
      } else {
        container.style.color = 'white';
      }
      container.appendChild(text);
      pos.appendChild(container);
    },
  },
};
</script>
<style scoped>
font {
  padding: 0;
}
.myTitle {
  color: white;
  font-family: "Do Hyeon", sans-serif;
  font-size: 1.5em;
}
.myTitle * {
  font-family: "Do Hyeon", sans-serif;
  font-size: 1.5em;
}
.speech-bubble {
  position: relative;
  background: #cfcdce;
  border-radius: 0.4em;
  padding: 10px;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: #cfcdce;
  border-left: 0;
  margin-top: -20px;
  margin-left: -10px;
}

.note {
  color: black;
}
.dictionary {
  background: lightgoldenrodyellow;
  padding-left: 5%;
}
.stickypink {
  color: rgb(233, 103, 131) !important;
}

.youtube {
  position: relative;
}

.middle {
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: 0.4s ease;
  position: absolute;
  text-align: center;
  font-size: calc(1vw + 10px);
  /* background-color: rgba(0, 0, 0, 1); */
}
.blocking {
  top: 0;
  height: 100%;
  width: 100%;
}

.gif-write {
  width: 55%;
  height: 55vh;
  margin: 0px auto;
  display: block;
  z-index: 11;
}
.overlay {
  background-color: rgba(0, 0, 0, 0.702);
}
.question-btn {
  position: absolute;
  bottom: 8px;
  right: 5%;
}
.input__label {
  margin-top: 7px;
  color: white;
}
.custom-red .v-input--selection-controls__input div {
  color: red;
}
</style>

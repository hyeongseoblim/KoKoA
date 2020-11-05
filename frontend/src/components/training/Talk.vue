<template>
  <v-row>
    <!-- left side -->
    <v-col class="youtubeContainer" cols="12" lg="8">
      <div class="d-flex justify-center mt-3">
        <youtube
          :video-id="url"
          ref="youtube"
          :player-vars="playerVars"
          flex
          @ended="onEnded"
        ></youtube>
      </div>
      <div class="d-flex justify-space-around my-5">
        <b-button class="mx-5" variant="success" @click="playVideo">play</b-button>
        <b-button variant="danger" @click="stopVideo">stop</b-button>
      </div>
      <!-- 자막, 이전.다음, like -->
      <div class="d-flex justify-space-around mt-3">
        <div>
          <v-btn @click="goPrevious" icon>
            <v-icon>
              mdi-arrow-left
            </v-icon>
          </v-btn>
        </div>
        <div>
          {{ video[replay].kor }}
          <v-btn class="ma-2" text icon color="purple lighten-2" @click="like">
            <v-icon>mdi-thumb-up</v-icon>
          </v-btn>
        </div>
        <div>
          <v-btn @click="goNext" icon>
            <v-icon>
              mdi-arrow-right
            </v-icon>
          </v-btn>
        </div>
      </div>
      <div class="d-flex justify-center mt-3">
        {{ video[replay].eng }}
      </div>
    </v-col>
    <!-- right side -->
    <v-col class="testContainer" cols="12" lg="4">
      <v-row class="d-flex justify-center ma-5">
        <v-col cols="12">
          <div>
            <Record @child-event="receiveText" />
          </div>
          <!-- 나의 발음 -->
          <h2>
            {{ speechText }}
          </h2>
          <div class="d-flex justify-space-around">
            <v-btn class="ma-2" text icon color="purple lighten-2">
              <v-icon>mdi-clipboard-edit-outline</v-icon>
              오답노트
            </v-btn>
            <div class="speech-bubble">표시된 부분에 유의해서<br />발음해보세요 :)</div>
          </div>
          <h4>
            romaza baleum giho pyoshi
          </h4>
          <div style="background:purple">
            //사전 field//
          </div>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>
<script>
import Record from './Record.vue';

export default {
  name: 'Talk',
  components: {
    Record,
  },
  props: {
    index: {
      type: String,
      default: '',
    },
  },
  mounted() {},
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  data() {
    return {
      speechText: '',
      url: 'mLx7D98zP_A',
      replay: 0,
      video: [
        {
          starttime: '00:00:00,001',
          endtime: '00:00:01,831',
          eng: '[National treasure zombie beast 2PM]',
          kor: '[국보급 짐승 좀비 2PM]',
        },
        {
          starttime: '00:00:01,832',
          endtime: '00:00:04,034',
          eng: '"Chansung lifts people up"',
          kor: '"찬성은 사람을 들어"',
        },
        {
          starttime: '00:00:04,035',
          endtime: '00:00:06,406',
          eng: '"Taecyeon is mad"',
          kor: '"화가 잔뜩 난 택연"',
        },
      ],
    };
  },
  methods: {
    play() {
      console.log(this.replay);
      const start = this.timer(this.video[this.replay].starttime);
      const end = this.timer(this.video[this.replay].endtime);
      console.log(start, end);
      this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
      console.log(this.fail);
      // if (this.fail === true) {
      //   this.replay += 1;
      //   this.fail = false;
      //   this.choicelist = [];
      // }
    },
    playVideo() {
      this.play();
    },
    async stopVideo() {
      await this.player.pauseVideo();
    },
    timer(input) {
      const hms = input;
      const a = hms.split(':');
      const s = a[2].split(',');
      const ms = Number(a[0] * 60 * 60) + Number(a[1] * 60) + Number(s[0]) + Number(s[1] / 1000);
      return ms;
    },
    onEnded() {
      this.play();
    },
    goPrevious() {
      this.replay -= 1;
    },
    goNext() {
      this.replay += 1;
    },
    like() {
      // like sentences list에 추가
    },
    receiveText(text) {
      this.speechText = text;
    },
  },
};
</script>
<style>
.speech-bubble {
  position: relative;
  background: #cfcdce;
  border-radius: 0.4em;
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
  margin-top: -10px;
  margin-left: -10px;
}
</style>

<template>
  <div class="container">
    <div
      class="eng bookmarks bookmark-top"
      style="color: black;"
      @click="type = 'Speaking'"
      :class="{ expandbmk: type === 'Speaking', basic: type !== 'Speaking' }"
    >
      Speaking
    </div>
    <div
      class="eng bookmarks bookmark-bottom"
      style="color: black;"
      @click="type = 'Writing'"
      :class="{ expandbmk: type === 'Writing', basic: type !== 'Writing' }"
    >
      Writing
    </div>
    <div class="paper">
      <h3 class="eng notecolor ml-15 mt-4">Review your sentences. - {{ this.type }} Note</h3>
      <div class="lines">
        <div class="text box">
          <br />
          <!-- 문장 목록 -->
          <div v-if="type === 'Speaking'">
            <v-list
              v-for="(item, index) in speechnote"
              :key="index"
              class="py-1"
              style="background: transparent;"
            >
              <div class="check d-flex">
                <v-btn @click="remove(item.id)" fab small depressed>
                  <v-icon>mdi-close-circle-outline</v-icon>
                </v-btn>
                <div
                  class="kor sentences"
                  @click="expanded === index ? (expanded = -'Writing') : setToTop(index)"
                  :id="'sentence' + index"
                >
                  ({{ index + 1 }}) {{ item.content }}
                </div>
              </div>
              <div v-if="expanded === index">
                <Talk
                  :notemode="true"
                  :noteitem="item"
                  :noteoverlay="true"
                  style="background: lightgoldenrodyellow; z-index:2"
                />
              </div>
            </v-list>
            <v-pagination
              color="none"
              v-model="currentSpeechPage"
              :length="speechTotalPage"
              :total-visible="7"
            >
            </v-pagination>
          </div>
          <!-- writing 문장 목록 -->
          <div v-else>
            <v-list
              v-for="(item, index) in writenote"
              :key="index"
              class="py-1"
              style="background: transparent;"
            >
              <div class="check d-flex">
                <v-btn @click="remove(item.id)" fab small depressed>
                  <v-icon>mdi-close-circle-outline</v-icon>
                </v-btn>
                <div
                  class="kor sentences"
                  @click="expanded === index ? (expanded = -1) : setToTop(index)"
                  :id="'sentence' + index"
                >
                  ({{ index + 1 }}) {{ item.content }}
                </div>
              </div>
              <div v-if="expanded === index">
                <Write
                  :notemode="true"
                  :noteitem="item"
                  :noteoverlay="true"
                  style="background: lightgoldenrodyellow; z-index:2"
                />
              </div>
            </v-list>
            <v-pagination
              color="none"
              v-model="currentWritePage"
              :length="writeTotalPage"
              :total-visible="7"
            >
            </v-pagination>
          </div>
        </div>
      </div>
      <div class="holes hole-top"></div>
      <div class="holes hole-middle"></div>
      <div class="holes hole-bottom"></div>
    </div>
  </div>
</template>

<script>
import http from '../../util/http-common';
import Talk from '../training/Talk.vue';
import Write from '../training/Write.vue';

export default {
  name: 'Note',
  components: {
    Talk,
    Write,
  },
  data: () => ({
    email: '',
    singleExpand: true,
    itemsPerPage: 4,
    speechnote: [],
    writenote: [],
    expanded: -1,
    type: 'Speaking',
    speechTotalPage: 1,
    currentSpeechPage: 1,
    writeTotalPage: 1,
    currentWritePage: 1,
    hover: false,
    removeTry: {
      icon: 'error',
      title: '<span style="color:white">Oops...</span>',
      html: '<span style="color:white">Do you want to remove?</span>',
      showDenyButton: true,
      confirmButtonText: 'YES',
      denyButtonText: 'NO',
      background: '#1C1C1C',
      backdrop: 'rgba(0,0,0,0.89)',
    },
  }),
  async created() {
    this.email = this.$store.state.email;
    await this.getData();
  },
  watch: {
    currentSpeechPage() {
      this.getData();
    },
  },
  methods: {
    setToTop(index) {
      const elmnt = document.getElementById(`sentence${index}`);
      elmnt.scrollIntoView(true);
      this.expanded = index;
    },
    async remove(noteid) {
      let type = 1;
      if (this.type === 'Speaking') {
        type = 0;
      }
      const tmp = await this.$swal.fire(this.removeTry);
      if (tmp.isConfirmed) {
        await http
          .delete('/note/delete/', { params: { email: this.email, noteid, type } })
          .then(() => {});
        await this.getData();
      }
    },
    async getData() {
      this.speechnote = [];
      this.writenote = [];
      await http
        .get('/note/load/', { params: { email: this.email, page: this.currentSpeechPage - 1 } })
        .then((res) => {
          this.speechTotalPage = res.data.speechnote[0].totalPages;
          for (let i = 0; i < res.data.speechnote[0].content.length; i += 1) {
            this.speechnote.push({
              id: res.data.speechnote[0].content[i].id,
              subtitleid: res.data.speechnote[0].content[i].subtitleid,
              engsubtitleid: res.data.speechnote[0].content[i].engsubtitleid,
              videoid: res.data.speechnote[0].content[i].videoid,
              content: res.data.speechnote_sub[0][2 * i].content,
              engcontent: res.data.speechnote_sub[0][2 * i + 1].content,
              starttime: res.data.speechnote_sub[0][2 * i].starttime,
              endtime: res.data.speechnote_sub[0][2 * i].endtime,
            });
          }

          this.writeTotalPage = res.data.writenote[0].totalPages;
          for (let i = 0; i < res.data.writenote[0].content.length; i += 1) {
            this.writenote.push({
              id: res.data.writenote[0].content[i].id,
              subtitleid: res.data.writenote[0].content[i].subtitleid,
              engsubtitleid: res.data.writenote[0].content[i].engsubtitleid,
              videoid: res.data.writenote[0].content[i].videoid,
              content: res.data.writenote_sub[0][2 * i].content,
              engcontent: res.data.writenote_sub[0][2 * i + 1].content,
              starttime: res.data.writenote_sub[0][2 * i].starttime,
              endtime: res.data.writenote_sub[0][2 * i].endtime,
            });
          }
        });
    },
  },
};
</script>

<style lang="scss">
$topval: 440px;

.paper {
  position: absolute;
  width: 90%;
  height: 105%;
  background: lightgoldenrodyellow;
  margin: -350px -25% 30px;
  left: 30%;
  top: $topval;
  box-shadow: 0px 0px 5px 0px #888;
  overflow: hidden;
}
/* red line on left side of paper */
.paper::before {
  content: "";
  position: absolute;
  left: 45px;
  height: 100%;
  width: 2px;
  background: rgba(255, 0, 0, 0.4);
}
.lines {
  margin-top: -15px;
  height: calc(100% - 40px);
  width: 100%;
  background-image: repeating-linear-gradient(
    lightgoldenrodyellow 0px,
    lightgoldenrodyellow 24px,
    steelblue 25px
  );
}
.text {
  position: absolute;
  top: 65px;
  left: 55px;
  bottom: 10px;
  right: 10px;
  line-height: 25px;
  font-family: "Indie Flower";
  outline: none;
  overflow: auto;
}
.holes {
  position: absolute;
  left: 10px;
  height: 25px;
  width: 25px;
  background: rgb(250, 250, 247);
  border-radius: 50%;
  box-shadow: inset 0px 0px 2px 0px #888;
}
.hole-top {
  top: 10%;
}
.hole-middle {
  top: 50%;
}
.hole-bottom {
  bottom: 10%;
}
.sentences {
  cursor: pointer;
  font-size: 30px;
  margin: 10px;
}
.bookmarks {
  position: absolute;
  height: 129px;
  box-shadow: inset 0px 0px 2px 0px #888;
  cursor: pointer;
  writing-mode: vertical-lr;
  text-align: center;
}

.bookmark-top {
  top: $topval * 0.26;
  background: rgb(233, 103, 131);
}
.bookmark-bottom {
  top: $topval * 0.57;
  background: rgb(85, 219, 163);
}
.expandbmk {
  width: 7vw;
  left: 1%;
  font-size: 1.75vw;
  padding-left: 0.7vw;
}
.basic {
  width: 3vw;
  left: 2%;
  font-size: 1.55vw;
  padding-left: 0.3vw;
}
.notecolor {
  color: black;
}
.v-pagination * {
  background-color: transparent !important;
  color: black !important;
  box-shadow: none !important;
  font-family: "Merriweather", serif;
  font-size: 30px !important;
}
.v-pagination__item--active .primary {
  background-color: transparent !important;
}
.v-pagination__item--active {
  color: rgb(255, 127, 0) !important;
  background-color: transparent !important;
}
button:focus {
  outline: 0;
}
.check button {
  display: none;
  background-color: transparent !important;
  color: red !important;
}
.check:hover button {
  display: inherit;
}
.box {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.box::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>

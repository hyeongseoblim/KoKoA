<template>
  <div class="artist">
    <!-- header -->
    <div
      class="header mr-auto"
      :style="{ 'background-image': 'url(' + require(`@/assets/img/${team}/${team}.jpg`) + ')' }"
    >
      <div class="group">{{ team }}</div>
    </div>
    <!-- 멤버 -->
    <v-row justify="space-around" class="member">
      <div class="list" v-for="(member,index) in members" :key="index">
        <img :class="{selected: isActive==index}"
          :src="require(`@/assets/img/${team}/${member.name}.jpg`)"
          alt=""
          @click="changeSelected(index)"
        />
        <div><h4 class="member" style="color: white;">{{ member.name }}</h4></div>
      </div>
    </v-row>
    <!-- 영상 리스트 -->
    <Videolist :filters="filters"/>
  </div>
</template>

<script>
import Videolist from './list/Videolist.vue';
import channelList from './core/channelList.json';
import memberList from './core/memberList.json';

export default {
  name: 'Artist',
  components: {
    Videolist,
  },
  data() {
    return {
      groupid: this.$route.query.groupid,
      team: '',
      title: '',
      teams: channelList,
      stars: memberList,
      members: [],
      filters: '',
      isActive: '',
    };
  },
  created() {
    this.team = this.teams[this.groupid - 1].groupname;
    this.title = this.teams[this.groupid - 1].title;
    this.filters = this.team;
    this.members = [];
    this.members.push({ name: this.title });
    for (let i = 0; i < this.stars.length; i += 1) {
      if (String(this.stars[i].groupid) === this.groupid) {
        this.members.push(this.stars[i]);
      }
    }
    if (this.members.length === 2) {
      this.members.pop();
    }
  },
  methods: {
    changeFilter(name) {
      this.filters = name;
    },
    changeSelected(index) {
      this.isActive = (index === this.isActive ? '' : index);
      this.filters = this.members[index].name;
    },
  },
};
</script>

<style>
/* *{
  font-family: 'Bangers', cursive;
} */
.teamImg {
  height: 350px;
  width: auto;
}
.artist{
  top: 0;
}
.header {
  height: 300px;
  /* background-size: 80% 80%; */
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
  resize: both;
  position: relative;
  display: flex;
  align-content: center;
  justify-content: center;
  /* display: flex; */
}
.group{
  font-size: 7vw;
  font-family: 'Bangers', cursive;
  color: rgb(255, 127, 0);
  /* margin-bottom: 0; */
  position: absolute;
  bottom: 0px;
  /* padding-top: 25vh; */
}

.member {
  margin-top: 20px;
  margin-bottom: 0.4vw;
  justify-content: center;
  font-family: 'Bangers', cursive;
  text-align: center;
  font-size: 2vw;
  justify-items: center;
}
.member img {
  border-radius: 50%;
  height: 3em;
  width: 3em;
}
.selected {
  /* background-color: #fdb165; */
  border: 3px solid rgb(255, 127, 0);
}

</style>

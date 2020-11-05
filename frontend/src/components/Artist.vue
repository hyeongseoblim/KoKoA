<template>
  <div class="artist">
    <!-- header -->
    <div
      class="header mr-auto"
      :style="{ 'background-image': 'url(' + require(`@/assets/img/${team}/${team}.jpg`) + ')' }"
    >
      <div>{{ team }}</div>
    </div>
    <!-- 멤버 -->
    <v-row justify="space-around" class="member">
      <div class="list" v-for="(member,index) in members" :key="index">
        <img :class="{selected: isActive==index}"
          :src="require(`@/assets/img/${team}/${member.name}.jpg`)"
          alt=""
          @click="changeSelected(index)"
        />
        <div>{{ member.name }}</div>
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
.teamImg {
  height: 350px;
  width: auto;
}

.header {
  background-color: #e7d0e2; /* Grey background */
  /* padding: 50px; */
  margin-bottom: 30px;
  height: 500px;
  /* background-size: 80% 80%; */
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
  border: 2px solid;
  color: rgb(180, 91, 180);
  resize: both;
  text-align: center;
  font-size: 60px;
  font-weight: bold;
}

.member {
  text-align: center;
}

.member img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}
.selected {
  border: 10px solid #0F0;
}

</style>

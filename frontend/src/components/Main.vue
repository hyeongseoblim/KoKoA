<template>
  <v-container>
    <Channels />
  </v-container>
</template>

<script>
import Popular from '@/components/list/Popular.vue';
import Test from '@/components/list/test.vue';
import Channels from './Channels.vue';

export default {
  name: 'Main',
  components: {
    Channels,
  },
  data() {
    return {

    };
  },
};
</script>

<template>
  <div>
    <h2>Note</h2>
    <h3>Review your incorrect sentences.</h3>
    <!-- 문장 목록 -->
    <v-container fluid>
      <v-data-iterator
        :items="items"
        item-key="name"
        :items-per-page.sync="itemsPerPage"
        :single-expand="singleExpand"
        hide-default-footer
      >
        <template v-slot:header>
          <v-toolbar class="mb-2" color="indigo darken-5" dark flat>
            <v-toolbar-title>This is a header</v-toolbar-title>
          </v-toolbar>
        </template>

        <template v-slot:default="{ items, isExpanded, expand }">
          <v-row>
            <v-col v-for="item in items" :key="item.name" cols="12" sm="12" md="4" lg="3">
              <v-card>
                <v-card-title class="subheading font-weight-bold">
                  <v-chip class="ma-2" color="red" text-color="white">
                    RED VELVET
                  </v-chip>
                  <v-chip class="ma-2" color="orange" text-color="white">
                    Yeri
                  </v-chip>
                  {{ item.name }}
                </v-card-title>
                <v-switch
                  :input-value="isExpanded(item)"
                  :label="isExpanded(item) ? 'Expanded' : 'Closed'"
                  class="pl-4 mt-0"
                  @change="v => expand(item, v)"
                ></v-switch>

                <v-divider></v-divider>

                <v-list v-if="isExpanded(item)" dense>
                  <!-- <v-list-item>
                    <v-list-item-content>Calories:</v-list-item-content>
                    <v-list-item-content class="align-end">
                      {{ item.calories }}
                    </v-list-item-content>
                  </v-list-item> -->
                </v-list>
              </v-card>
            </v-col>
          </v-row>
        </template>

        <template v-slot:footer>
          <v-toolbar class="mt-2" color="indigo" dark flat>
            <v-toolbar-title class="subheading">
              This is a footer
            </v-toolbar-title>
          </v-toolbar>
        </template>
      </v-data-iterator>
    </v-container>
  </div>
</template>

<script>
export default {
  data: () => ({
    singleExpand: true,
    itemsPerPage: 4,
    items: [
      {
        name: '찬성은 사람을 들어',
      },
      {
        name: 'Ice cream sandwich',
      },
      {
        name: 'Eclair',
      },
      {
        name: 'Cupcake',
      },
    ],
  }),
};
</script>

<style></style>

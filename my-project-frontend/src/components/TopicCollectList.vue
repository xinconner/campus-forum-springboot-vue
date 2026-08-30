<script setup>
import {ref} from "vue";
import LightCard from "@/components/LightCard.vue";
import router from "@/router";
import TopicTag from "@/components/TopicTag.vue";
import {ElMessage} from "element-plus";
import {apiForumCollect, apiForumCollectDelete} from "@/net/api/forum";

defineProps({
  show: Boolean
})

const emit = defineEmits(['close'])

const list = ref([])

function init() {
  apiForumCollect(data => list.value = data)
}

function deleteCollect(index, tid) {
  apiForumCollectDelete(tid, () => {
    ElMessage.success('已取消收藏！')
    list.value.splice(index, 1)
  })
}

</script>

<template>
  <el-drawer :model-value="show" @close="emit('close')" @open="init" title="我的帖子收藏列表">
    <div class="collect-list">
      <light-card v-for="(item, index) in list" class="topic-card"
                  @click="router.push(`/index/topic-detail/${item.id}`)">
        <topic-tag :type="item.type"/>
        <div class="title">
          <b>{{item.title}}</b>
        </div>
        <el-link type="danger" @click.stop="deleteCollect(index, item.id)">取消收藏</el-link>
      </light-card>
    </div>
  </el-drawer>
</template>


<style scoped>
.collect-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.topic-card {
  background-color: rgba(128, 128, 128, 0.2);
  transition: .3s;
  display: flex;
  justify-content: space-between;

  .title {
    margin-left: 5px;
    font-size: 14px;
    flex: 1;
    white-space: nowrap;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  &:hover {
    scale: 1.02;
    cursor: pointer;
  }
}
</style>
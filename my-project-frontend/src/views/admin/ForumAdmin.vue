<script setup>
import {ref} from "vue";
import ForumTopicAdmin from "@/views/admin/section/ForumTopicAdmin.vue";
import ForumTopicProhibitedAdmin from "@/views/admin/section/ForumTopicProhibitedAdmin.vue";
import {apiForumTypes} from "@/net/api/forum";
import ForumTopicTypeAdmin from "@/views/admin/section/ForumTopicTypeAdmin.vue";

const initTypes = () => apiForumTypes(data => types.value = data)
const types = ref([])

apiForumTypes(data => types.value = data)
initTypes()
</script>
<template>
<div class="forum-admin">
    <div>
        <forum-topic-admin :types="types"/>
    </div>
    <forum-topic-type-admin :types="types" @update="initTypes"/>

    <forum-topic-prohibited-admin/>
</div>
</template>


<style lang="less" scoped>
.forum-admin {
    display: flex;
    flex-direction: column;
    gap: 50px;
}


</style>
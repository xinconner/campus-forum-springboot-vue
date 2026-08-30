<script setup>

import {Hide, Lock, Top, User} from "@element-plus/icons-vue";
import {reactive, ref, watchEffect} from "vue";
import {
    apiForumProhibit, apiForumProhibitedList,
    apiForumTopicAllList,
    apiForumTopicDelete, apiForumTopicInvisible,
    apiForumTopicLocked,
    apiForumTopicTop,
    apiForumTypes, apiTopicChangeType
} from "@/net/api/forum";
import {useStore} from "@/store";
import {ElMessage, ElMessageBox} from "element-plus";
import ForumTopicAdmin from "@/views/admin/section/ForumTopicAdmin.vue";
import ForumTopicProhibitedAdmin from "@/views/admin/section/ForumTopicProhibitedAdmin.vue";
const findType = type => props.types.find(item => item.id === type)

const store = useStore();
const topicList = reactive({
    list: [],
    page: 1,
    size: 10,
    total: 0
})
const props = defineProps({
    types: Array,
})


const keyword = ref('')
const searchText = ref('')

const deleteTopic = id => {
    ElMessageBox.confirm('您确定要删除这个帖子吗，删除后永久无法找回？', '删除帖子', {
        callback: value => {
            if(value === 'confirm') {
                apiForumTopicDelete(id, () => {
                    refreshList()
                    ElMessage.success('帖子删除成功')
                })
            }
        }
    })
}

const topTopic = (tid, status) => {
    apiForumTopicTop({ tid, status }, () => {
        ElMessage.success('帖子置顶状态更新成功')
        refreshList()
    })
}

const invisibleTopic = (tid, status) => {
    apiForumTopicInvisible({ tid, status }, () => {
        ElMessage.success('帖子屏蔽状态更新成功')
        refreshList()
    })
}

const lockTopic = (tid, locked) => {
    apiForumTopicLocked({ tid, locked }, () => {
        ElMessage.success('帖子锁定状态更新成功')
        refreshList()
    })
}

const refreshList = () => {
    apiForumTopicAllList(topicList.page, topicList.size,keyword.value, data => {
        topicList.list = data.list;
        topicList.total = data.total;
    })
}

const changeTopicType = (tid, type) => {
    apiTopicChangeType(tid, type, () => {
        ElMessage.success('帖子类型修改成功')
    })
}


watchEffect(() => refreshList())
</script>

<template>
    <div class="forum-admin-header">
        <div>
            <div class="title">
                <el-icon><User/></el-icon>
                论坛帖子列表
            </div>
            <div class="desc">
                在这里管理论坛所有的帖子，并对帖子进行各种操作和管理
            </div>
        </div>
        <div>
            <el-input :prefix-icon="Search" placeholder="搜索帖子标题..."
                      clearable @clear="keyword = ''"
                      @keydown.enter="keyword = searchText"
                      v-model="searchText"/>
        </div>
    </div>
    <el-table :data="topicList.list" height="400">
        <el-table-column prop="id" label="帖子ID" width="80" align="center"/>
        <el-table-column prop="title" label="帖子标题" width="300" show-overflow-tooltip>
            <template #default="{ row }">
                <el-link :href="`/index/topic-detail/${row.id}`">
                    <div style="display: inline-flex;gap: 5px;color: dodgerblue;margin-right: 5px">
                        <el-icon v-if="row.locked">
                            <Lock/>
                        </el-icon>
                        <el-icon v-if="row.top">
                            <Top/>
                        </el-icon>
                        <el-icon v-if="row.invisible">
                            <Hide/>
                        </el-icon>
                    </div>
                    {{ row.title }}
                </el-link>
            </template>
        </el-table-column>
        <el-table-column label="帖子类型" width="180">
            <template #default="{ row }">
                <el-select v-model="row.type" @change="id => changeTopicType(row.id, id)">
                    <el-option :label="type.name" :value="type.id" v-for="type in types">
                        <template #default>
                            <div class="topic-type">
                                <div class="type-dot" :style="{ backgroundColor: findType(type.id)?.color ?? '#bababa' }"></div>
                                <div>{{ findType(type.id)?.name ?? '未知类型' }}</div>
                            </div>
                        </template>
                    </el-option>
                </el-select>
            </template>
        </el-table-column>
        <el-table-column label="帖子作者" width="150">
            <template #default="{ row }">
                <div class="topic-username">
                    <el-avatar :size="20" :src="store.avatarUserUrl(row.avatar)"/>
                    <div>{{ row.username }}</div>
                </div>
            </template>
        </el-table-column>
        <el-table-column prop="time" label="发表时间" width="180" align="center"
                         :formatter="row => new Date(row.time).toLocaleString()"/>
        <el-table-column label="操作" width="270" fixed="right" align="center">
            <template #default="{ row }">
                <el-button size="small" type="info" @click="invisibleTopic(row.id,false)" v-if="row.invisible">取消</el-button>
                <el-button size="small" type="info" @click="invisibleTopic(row.id,true)" plain v-else>屏蔽</el-button>
                <el-button size="small" type="warning" @click="lockTopic(row.id,false)" v-if="row.locked">取消</el-button>
                <el-button size="small" type="warning" @click="lockTopic(row.id,true)" plain v-else>锁定</el-button>
                <el-button size="small" type="success" @click="topTopic(row.id,false)" v-if="row.top">取消</el-button>
                <el-button size="small" type="success" @click="topTopic(row.id,true)" plain v-else>置顶</el-button>
                <el-button size="small" type="danger" plain @click="deleteTopic(row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <div class="pagination">
        <el-pagination :total="topicList.total"
                       v-model:current-page="topicList.page"
                       v-model:page-size="topicList.size"
                       layout="total, sizes, prev, pager, next, jumper"/>
    </div>
</template>

<style lang="less" scoped>
.forum-admin-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.title {
    font-weight: bold;
}

.desc {
    color: #bababa;
    font-size: 13px;
    margin-bottom: 20px;
}

.pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
}

.topic-username {
    gap: 10px;
    display: flex;
    align-items: center;
}
.topic-type {
    gap: 10px;
    display: flex;
    align-items: center;

    .type-dot {
        height: 7px;
        width: 7px;
        border-radius: 50%;
    }
}
</style>
<script setup>

import {reactive} from "vue";
import {apiTopicTypeCreate, apiTopicTypeDelete, apiTopicTypeUpdate} from "@/net/api/forum";
import {ElMessage, ElMessageBox} from "element-plus";
import {Plus} from "@element-plus/icons-vue";

const props = defineProps({
    types: Array,
})

const findType = type => props.types.find(item => item.id === type)

const emit = defineEmits(['update'])

const editor = reactive({
    display: false,
    target: null,
    type: 'update'
})

const startEdit = (target) => {
    editor.target = { ...target }
    editor.type = 'update'
    editor.display = true
}

const createTopic = () => {
    editor.target = { name: '', desc: '', color: '' }
    editor.type = 'create'
    editor.display = true
}

const updateOrCreateType = () => {
    if(editor.type === 'create') {
        apiTopicTypeCreate(editor.target, () => {
            ElMessage.success('创建成功')
            editor.display = false
            emit('update')
        })
    } else  {
        apiTopicTypeUpdate(editor.target, () => {
            ElMessage.success('更新成功')
            editor.display = false
            emit('update')
        })
    }
}

const deleteType = (id) => {
    ElMessageBox.confirm('删除后将永远无法恢复，您确定要这样做吗？', '删除帖子分类', {
        callback: action => {
            if(action === 'confirm') {
                apiTopicTypeDelete(id, () => {
                    ElMessage.success('删除成功')
                    emit('update')
                })
            }
        }
    })
}
</script>

<template>
    <div>
        <div class="topic-type-header">
            <div>
                <div class="title">
                    论坛帖子类型管理
                </div>
                <div class="desc">
                    在这里管理论坛所有的帖子类型
                </div>
            </div>
            <el-button type="primary" :icon="Plus" @click="createTopic">新建</el-button>
        </div>
        <el-table :data="types" height="400">
            <el-table-column prop="id" label="类型ID" width="80" align="center"/>
            <el-table-column prop="name" label="类型名称" width="200" align="center">
                <template #default="{ row }">
                    <div class="topic-type">
                        <div class="type-dot" :style="{ backgroundColor: findType(row.id)?.color ?? '#bababa' }"></div>
                        <div>{{ findType(row.id)?.name }}</div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="desc" label="类型简介" min-width="400" show-overflow-tooltip/>
            <el-table-column label="操作" width="150" fixed="right" align="center">
                <template #default="{ row }">
                    <el-button size="small" type="info" @click="startEdit(row)" plain>编辑</el-button>
                    <el-button size="small" type="danger" @click="deleteType(row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog title="编辑帖子类型" width="400" v-model="editor.display">
            <el-form label-position="top">
                <el-form-item label="类型ID">
                    <el-input v-model="editor.target.id" disabled/>
                </el-form-item>
                <el-form-item label="类型名称">
                    <div style="display: flex;gap: 10px">
                        <el-input v-model="editor.target.name"/>
                        <el-color-picker v-model="editor.target.color"/>
                    </div>
                </el-form-item>
                <el-form-item label="类型介绍">
                    <el-input v-model="editor.target.desc" type="textarea" :rows="7"/>
                </el-form-item>
                <div style="text-align: right">
                    <el-button type="primary" @click="updateOrCreateType">保存</el-button>
                </div>
            </el-form>
        </el-dialog>
    </div>
</template>

<style scoped>
.topic-type-header {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .title {
        font-weight: bold;
    }

    .desc {
        color: #bababa;
        font-size: 13px;
        margin-bottom: 20px;
    }
}


.topic-type {
    gap: 10px;
    display: flex;
    align-items: center;
    width: fit-content;
    margin: auto;

    .type-dot {
        height: 7px;
        width: 7px;
        border-radius: 50%;
    }
}
</style>

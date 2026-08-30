<script setup>

import {EditPen} from "@element-plus/icons-vue";
import {reactive} from "vue";
import {apiUserDetailTotal, apiUserSave} from "@/net/api/user";
import {ElMessage} from "element-plus";

const props = defineProps({
    userTable: Object
})

const editor = reactive({
    id: 0,
    display: false,
    temp: {},
    loading: false
})

defineExpose({openUserEditor})

function saveUserDetail() {
    editor.display = false
    apiUserSave(editor.temp, () => {
        const user = props.userTable.data.find(user => user.id === editor.id)
        Object.assign(user, editor.temp)
        ElMessage.success('数据保存成功')
    })
}

function openUserEditor(user) {
    editor.id = user.id
    editor.display = true
    editor.loading = true
    apiUserDetailTotal(editor.id, data => {
        editor.temp = { ...data, ...user }
        editor.loading = false
    })
}


</script>

<template>
    <el-drawer v-model="editor.display">
        <template #header>
            <div>
                <div style="font-weight: bold">
                    <el-icon><EditPen/></el-icon> 编辑用户信息
                </div>
                <div style="font-size: 13px">
                    编辑完成后请点击下方完成按钮
                </div>
            </div>
        </template>
        <div v-loading="editor.loading" element-loading-text="数据正在加载中，请稍后..." style="height: 100%">
            <el-form label-position="top" v-if="!editor.loading">
                <el-form-item label="用户名">
                    <el-input v-model="editor.temp.username"/>
                </el-form-item>
                <el-form-item label="电子邮件">
                    <el-input v-model="editor.temp.email"/>
                </el-form-item>
                <div style="display: flex;font-size: 14px;gap: 20px">
                    <div>
                        <span style="margin-right: 10px">禁言</span>
                        <el-switch v-model="editor.temp.mute"/>
                    </div>
                    <el-divider style="height: 30px" direction="vertical"/>
                    <div>
                        <span style="margin-right: 10px">帐户封禁</span>
                        <el-switch v-model="editor.temp.banned"/>
                    </div>
                </div>
                <div style="margin-top: 10px;color: #606266;font-size: 14px;">
                    注册时间：{{new Date(editor.temp.registerTime).toLocaleString()}}
                </div>
                <el-divider/>
                <el-form-item label="性别">
                    <el-radio-group v-model="editor.temp.detail.gender">
                        <el-radio :label="0">男</el-radio>
                        <el-radio :label="1">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input v-model="editor.temp.detail.phone"/>
                </el-form-item>
                <el-form-item label="QQ账号">
                    <el-input v-model="editor.temp.detail.qq"/>
                </el-form-item>
                <el-form-item label="微信账号">
                    <el-input v-model="editor.temp.detail.wx"/>
                </el-form-item>
                <el-form-item label="个人简介">
                    <el-input type="textarea" :rows="4" v-model="editor.temp.detail.desc"/>
                </el-form-item>
                <el-divider/>
                <div>
                    <div>隐私设置</div>
                    <el-checkbox v-model="editor.temp.privacy.phone">
                        公开展示用户的手机号
                    </el-checkbox>
                    <el-checkbox v-model="editor.temp.privacy.email">
                        公开展示用户的电子邮件地址
                    </el-checkbox>
                    <el-checkbox v-model="editor.temp.privacy.wx">
                        公开展示用户的微信号
                    </el-checkbox>
                    <el-checkbox v-model="editor.temp.privacy.qq">
                        公开展示用户的QQ号
                    </el-checkbox>
                    <el-checkbox v-model="editor.temp.privacy.gender">
                        公开展示用户的性别
                    </el-checkbox>
                </div>
            </el-form>
        </div>
        <template #footer>
            <div style="text-align: center">
                <el-button type="success" @click="saveUserDetail">保存</el-button>
                <el-button type="info" @click="editor.display =false">取消</el-button>
            </div>
        </template>
    </el-drawer>
</template>

<style scoped>

</style>

<script setup>
import {apiEmailRecordList, apiEmailResend} from "@/net/api/email";
import {reactive, watchEffect} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import {RefreshRight, User} from "@element-plus/icons-vue";

const emailList = reactive({
    list: [],
    total: 0,
    page: 1,
    size: 10
})

const resendEmail = (row) => {
    const { id, email } = row
    ElMessageBox.confirm(`这将会重新发送此邮件到邮箱: ${email}，您确定要这样做吗？`, '重发邮件', {
        callback: value => {
            if(value === 'confirm') {
                apiEmailResend(id, () => {
                    ElMessage.success('邮件重发成功')
                    row.status = 0
                }, () => {
                    ElMessage.error('邮件重发失败')
                    row.status = 2
                })
            }
        }
    })
}

watchEffect(() => {
    apiEmailRecordList(emailList.page, emailList.size, data => {
        emailList.list = data.list
        emailList.total = data.total
    })
})

</script>

<template>
    <div class="email-admin">
        <div class="title">
            <el-icon><User/></el-icon>
            论坛邮件列表
        </div>
        <div class="desc">
            在这里管理论坛的所有发送的邮件，并操作重发。
        </div>
        <el-table :data="emailList.list" height="400">
            <el-table-column prop="id" label="ID" width="100" align="center"/>
            <el-table-column prop="email" label="收件人" width="200" align="center" show-overflow-tooltip/>
            <el-table-column prop="status" label="发送状态" width="100" align="center">
                <template #default="{ row }">
                    <el-tag v-if="row.status === 0" type="info">发送中</el-tag>
                    <el-tag v-if="row.status === 1" type="success">已发送</el-tag>
                    <el-tag v-if="row.status === 2" type="danger">失败</el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="title" label="邮件主题" width="200" align="center" show-overflow-tooltip/>
            <el-table-column prop="content" label="邮件内容" width="300" align="center" show-overflow-tooltip/>
            <el-table-column label="发送时间" width="200">
                <template #default="{ row }">
                    {{ new Date(row.time).toLocaleString() }}
                </template>
            </el-table-column>
            <el-table-column width="120" label="操作" fixed="right">
                <template #default="{ row }">
                    <el-button size="small" type="primary" :icon="RefreshRight"
                               @click="resendEmail(row)"
                               :disabled="row.status !== 2">重新发送</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagination">
            <el-pagination :total="emailList.total"
                           v-model:current-page="emailList.page"
                           v-model:page-size="emailList.size"
                           layout="total, sizes, prev, pager, next, jumper"/>
        </div>
    </div>
</template>

<style scoped>
.email-admin {
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
}

</style>
<script setup>

import {EditPen, Search, Unlock, User} from "@element-plus/icons-vue";
import {apiUserList, apiUserModifyPassword} from "@/net/api/user";
import {reactive, ref, watchEffect} from "vue";
import {useStore} from "@/store";
import UserEditor from "@/components/UserEditor.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const editorRef = ref()
const store = useStore()
const userTable = reactive({
    page: 1,
    size: 10,
    total: 0,
    data: []
})

const keyword = ref('')
const searchText = ref('')

function userStatus(user) {
    if(user.mute && user.banned)
        return '禁言中、封禁中'
    else if(user.mute)
        return '禁言中'
    else if(user.banned)
        return '封禁中'
    else
        return '正常'
}

function changePassword({ id, username }) {
    ElMessageBox.prompt(`您确定要修改用户 ${username} 的密码吗，修改后用户将不能使用原密码登录？`, '修改密码', {
        inputPattern: /^.{6,20}$/,
        inputErrorMessage: '密码长度必须在6-20个字符之间',
        callback: ({ action, value }) => {
            if(action === 'confirm') {
                apiUserModifyPassword({id, newPassword: value},
                    () => ElMessage.success('密码修改成功'))
            }
        }
    })
}


watchEffect(() => apiUserList(userTable.page, userTable.size,keyword.value,data => {
    userTable.total = data.total
    userTable.data = data.list
}))

</script>

<template>
  <div class="user-admin">
    <div class="user-admin-header">
        <div>
            <div class="title">
                <el-icon><User/></el-icon>
                论坛用户列表
            </div>
            <div class="desc">
                在这里管理论坛的所有用户，包括账号信息、封禁和禁言处理。
            </div>
        </div>
        <div>
            <el-input :prefix-icon="Search" placeholder="搜索帖子标题..."
                      clearable @clear="keyword = ''"
                      @keydown.enter="keyword = searchText"
                      v-model="searchText"/>
        </div>
    </div>
      <el-table :data="userTable.data" height="320">
          <el-table-column prop="id" label="编号" width="80"/>
          <el-table-column label="用户名" width="180">
              <template #default="{row}">
                  <div class="table-username">
                      <el-avatar :size="30" :src="store.avatarUserUrl(row.avatar)"/>
                      <div>{{row.username}}</div>
                  </div>
              </template>
          </el-table-column>
          <el-table-column label="角色" width="100" align="center">
              <template #default="{row}">
                  <el-tag type="danger" v-if="row.role==='admin'">管理员</el-tag>
                  <el-tag v-else>普通用户</el-tag>
              </template>
          </el-table-column>
          <el-table-column prop="email" label="电子邮件" width="200" show-overflow-tooltip/>
          <el-table-column label="注册时间" width="200">
          <template #default="{ row }">
              {{ new Date(row.registerTime).toLocaleString() }}
          </template>
          </el-table-column>
          <el-table-column label="状态"  align="center">
              <template #default="{ row }">
                  {{ userStatus(row) }}
              </template>
          </el-table-column>
          <el-table-column label="操作" align="center" fixed="right" width="200">
              <template #default="{ row }">
                  <el-button type="warning" size="small" :icon="Unlock"
                             @click="changePassword(row)"
                             :disabled="row.role === 'admin'">修改密码</el-button>
                  <el-button type="primary" size="small" :icon="EditPen"
                             @click="editorRef.openUserEditor(row)"
                             :disabled="row.role === 'admin'">编辑</el-button>
              </template>
          </el-table-column>
      </el-table>
      <div class="pagination">
          <el-pagination :total="userTable.total"
                         v-model:current-page="userTable.page"
                         v-model:page-size="userTable.size"
                         layout="total, sizes, prev, pager, next, jumper"/>
      </div>
      <user-editor :user-table="userTable" ref="editorRef"/>
  </div>
</template>

<style lang="less" scoped>
.user-admin {
  .user-admin-header {
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

  .table-username {
    height: 30px;
    display: flex;
    align-items: center;
    gap: 15px;
  }

  .pagination {
    margin-top: 20px;
    display: flex;
    justify-content: right;
  }

  :deep(.el-drawer__header) {
    margin-bottom: 0;
  }
}
</style>

<script setup>
import {Back, Message, Operation, Right, Sort} from "@element-plus/icons-vue";
import {useStore} from "@/store";
import {isRoleAdmin, logout} from "@/net";
import router from "@/router";
import {useRoute} from "vue-router";
import {computed} from "vue";

const route = useRoute()
const store = useStore()

const isAdminPage = computed(() => route.fullPath.startsWith("/admin"))

function userLogout() {
  logout(() => router.push("/"))
}
</script>

<template>
  <div class="user-info">
    <template v-if="isRoleAdmin()">
      <el-button type="primary" size="small"
                 @click="router.push('/index')"
                 v-if="isAdminPage">
        回到用户端
        <el-icon style="margin-left: 5px">
          <Right/>
        </el-icon>
      </el-button>
      <el-button type="danger" size="small"
                 @click="router.push('/admin')"
                 v-else>
        前往管理端
        <el-icon style="margin-left: 5px">
          <Right/>
        </el-icon>
      </el-button>
    </template>
    <slot/>
    <div class="profile">
      <div>{{ store.user.username }}</div>
      <div>{{ store.user.email }}</div>
    </div>
    <el-dropdown>
      <el-avatar :src="store.avatarUrl"/>
      <template #dropdown>
        <el-dropdown-item>
          <el-icon>
            <Operation/>
          </el-icon>
          个人设置
        </el-dropdown-item>
        <el-dropdown-item>
          <el-icon>
            <Message/>
          </el-icon>
          消息列表
        </el-dropdown-item>
        <el-dropdown-item @click="userLogout" divided>
          <el-icon>
            <Back/>
          </el-icon>
          退出登录
        </el-dropdown-item>
      </template>
    </el-dropdown>
  </div>
</template>

<style scoped>
.user-info {
  width: 320px;
  display: flex;
  gap: 20px;
  justify-content: flex-end;
  align-items: center;

  .el-avatar:hover {
    cursor: pointer;
  }

  .profile {
    text-align: right;

    :first-child {
      font-size: 18px;
      font-weight: bold;
      line-height: 20px;
    }

    :last-child {
      font-size: 10px;
      color: grey;
    }
  }
}
</style>

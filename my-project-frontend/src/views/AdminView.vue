<script setup>

import {
  Bell, ChatDotSquare, Collection,
  DataLine, Document,
  Files,
  Location,
  Lock, Message, Monitor,
  Notification,
  Operation, Position,
  Umbrella,
  User,
  WindPower
} from "@element-plus/icons-vue";
import {get} from "@/net";
import {useStore} from "@/store";
import {inject, onMounted, ref} from "vue";
import UserInfo from "@/components/UserInfo.vue";
import router from "@/router";
import {useRoute} from "vue-router";

const adminMenu = [
  {
    title: '校园论坛管理', icon: Location, sub: [
      { title: '用户管理', icon: User, index: '/admin/user'},
      { title: '邮件管理', icon: Message, index: '/admin/email' },
      { title: '帖子广场管理', icon: ChatDotSquare, index:'/admin/forum'},
      { title: '失物招领管理', icon: Bell },
      { title: '校园活动管理', icon: Notification },
      { title: '表白墙管理', icon: Umbrella },
      { title: '合作机构管理', icon: WindPower }
    ]
  }, {
    title: '探索与发现管理', icon: Position, sub: [
      { title: '成绩管理', icon: Document },
      { title: '课程表管理', icon: Files },
      { title: '教务通知管理', icon: Monitor },
      { title: '在线图书馆管理', icon: Collection },
      { title: '预约教室管理', icon: DataLine }
    ]
  }
]

const route = useRoute()
const loading = inject('userLoading')
const pageTabs = ref([])

function handleTabClick({ props }) {
  router.push(props.name)
}

function handleTabClose(name) {
  const index = pageTabs.value.findIndex(tab => tab.name === name)
  const isCurrent = name === route.fullPath
  pageTabs.value.splice(index, 1)
  if(pageTabs.value.length > 0) {
    //删除后，标签列表中还有剩余的Tab且关闭的是当前的，则自动进行切换，默认切换到上一个，如果没有上一个，则切换到下一个
    if(isCurrent) {
      router.push(pageTabs.value[Math.max(0, index - 1)].name)
    }
  } else {
    router.push('/admin')
  }
}

function addAdminTab(menu) {
  if(!menu.index) return
  if(pageTabs.value.findIndex(tab => tab.name === menu.index) < 0) {
    pageTabs.value.push({
      title: menu.title,
      name: menu.index
    })
  }
}

onMounted(() => {
  const initPage = adminMenu
      .flatMap(menu => menu.sub)
      .find(sub => sub.index === route.fullPath)
  if(initPage) {
    addAdminTab(initPage)
  }
})
</script>

<template>
  <div class="admin-content" v-loading="loading" element-loading-text="正在进入，请稍后...">
    <el-container style="height: 100%">
      <el-aside width="230px" class="admin-content-aside">
        <div class="logo-box">
          <el-image class="logo" src="https://www.gzasc.edu.cn/images/logo_blue.png"/>
        </div>
        <el-scrollbar style="height: calc(100vh - 57px)">
          <el-menu
              router
              :default-active="$route.path"
              :default-openeds="['1','2']"
              style="min-height: calc(100vh - 57px);border: none">
            <el-sub-menu :index="(index+1).toString()"
                         v-for="(menu,index) in adminMenu">
              <template #title>
                <el-icon>
                  <component :is="menu.ic"/>
                </el-icon>
                <span><b>{{menu.title}}</b></span>
              </template>
              <el-menu-item :index="subMenu.index"
                            @click="addAdminTab(subMenu)"
                            v-for="subMenu in menu.sub">
                <template #title>
                  <el-icon>
                    <component :is="subMenu.icon"/>
                  </el-icon>
                  {{subMenu.title}}
                </template>
              </el-menu-item>
            </el-sub-menu>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-container>
        <el-header class="admin-content-header">
          <div style="flex: 1">
            <el-tabs type="card"
                     :model-value="route.fullPath"
                     closable
                     @tab-remove="handleTabClose"
                     @tabClick="handleTabClick">
              <el-tab-pane v-for="tab in pageTabs"
                           :label="tab.title"
                           :name="tab.name"
                           :key="tab.name"/>
            </el-tabs>
          </div>
          <user-info/>
        </el-header>
        <el-main>
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component"/>
            </keep-alive>
          </router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.admin-content{
  height: 100vh;
  width: 100vw;

  .admin-content-aside {
    border-right: solid 1px var(--el-border-color);

    .logo-box {
      text-align: center;
      padding: 15px 0 10px;
      height: 32px;

      .logo {
        height: 32px;
      }
    }
  }

  .admin-content-header {
    border-bottom: solid 1px var(--el-border-color);
    height: 55px;
    display: flex;
    align-items: center;
    box-sizing: border-box;

    :deep(.el-tabs__header) {
      height: 32px;
      margin-bottom: 0;
      border-bottom: none;
    }

    :deep(.el-tabs__nav) {
      gap: 10px;
      border: none;
    }

    :deep(.el-tabs__item) {
      height: 32px;
      padding: 0 15px;
      border-radius: 6px;
      border: solid 1px var(--el-border-color);
    }
  }
}


</style>
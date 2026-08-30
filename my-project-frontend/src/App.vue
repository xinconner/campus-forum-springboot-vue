<script setup>
import { useDark, useToggle } from '@vueuse/core'
import {onMounted, provide, ref} from "vue";
import {apiUserInfo} from "@/net/api/user";
import {isUnauthorized} from "@/net";
import zhCn from "element-plus/es/locale/lang/zh-cn";

useDark({
  selector: 'html',
  attribute: 'class',
  valueDark: 'dark',
  valueLight: 'light'
})

useDark({
  onChanged(dark) { useToggle(dark) }
})


const loading =ref(false)
provide('userLoading', loading)

onMounted(() => {
  if(!isUnauthorized()) {
    apiUserInfo(loading)
  }
})
</script>

<template>
    <el-config-provider :locale="zhCn">
        <div class="wrapper">
            <router-view/>
        </div>
    </el-config-provider>
</template>

<style scoped>
.wrapper {
  line-height: 1.5;
}
</style>

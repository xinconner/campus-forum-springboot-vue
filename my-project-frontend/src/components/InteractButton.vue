<script setup>
const props = defineProps({
  name: String,
  checkName: String,
  color: String,
  check: Boolean,
  disabled: Boolean
})

const onClick = () => {
  if(!props.disabled){
    emit('check')
  }
}

const emit = defineEmits(['check'])
</script>

<template>
  <div class="interact-button" :class="{'locked': disabled}">
        <span class="icon" :style="{'color': check ? color : 'unset'}" @click="onClick">
            <slot/>
        </span>
    <span class="name" :style="{'color': color}">
            {{check ? checkName : name}}
        </span>
  </div>
</template>

<style scoped>
.interact-button {
  display: inline-block;
  height: 20px;

  .name {
    font-size: 13px;
    margin-left: 5px;
    opacity: 0.7;
  }

  .icon {
    vertical-align: middle;
    transition: .3s;

    &:hover {
      cursor: pointer;
      font-size: 18px;
    }
  }

  &.locked {
    opacity: 0.5;

    .icon:hover {
      font-size: unset;
      cursor: not-allowed;
    }
  }
}
</style>

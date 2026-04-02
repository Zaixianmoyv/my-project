<template>
    <div class="common-layout">
      <el-container style="height: 100%">
        <el-header><HeaderUser /></el-header>
            <el-main>
              <router-view />
              <el-backtop :right="20" :bottom="60" style="background-color: #e6e3e3;"/>
            </el-main>
          </el-container>
    </div>
  </template>
  
  <script>
  import { defineComponent } from 'vue'
  import HeaderUser from '../components/HeaderUser.vue'
  
  export default defineComponent ({
      components: {
          HeaderUser,
      },
  });
  
  // 解决ERROR ResizeObserver loop completed with undelivered notifications.
  
  //问题的
  
  const debounce = (fn, delay) => {
    let timer = null;
  
    return function () {
      let context = this;
  
      let args = arguments;
  
      clearTimeout(timer);
  
      timer = setTimeout(function () {
        fn.apply(context, args);
      }, delay);
    };
  };
  
  // 解决ERROR ResizeObserver loop completed with undelivered notifications.
  
  const _ResizeObserver = window.ResizeObserver;
  
  window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
    constructor(callback) {
      callback = debounce(callback, 16);
  
      super(callback);
    }
  };
  </script>
  
  <style scoped>
  .common-layout {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
  }
  .el-header {
    padding: 0;
  }

  .el-main {
  --el-main-padding: 0px;
  }
  </style>
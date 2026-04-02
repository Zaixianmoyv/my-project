<template>
    <div class="common-layout">
      <el-container style="height: 100%;">
        <el-header><Header /></el-header>
          <el-container>
          <el-aside width="180px"><Aside /></el-aside>
          <el-container>
            <el-main >
              <router-view />
              <el-backtop :right="20" :bottom="60" style="background-color: #e6e3e3;"/>
            </el-main>
          </el-container>
        </el-container>
      </el-container>
    </div>
  </template>
  
  <script>
  import { defineComponent } from 'vue'
  import Header from '../components/Header.vue'
  import Aside from '../components/Aside.vue'
  
  export default defineComponent ({
      components: {
          Header,
          Aside,
    
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
  .el-aside {
    background-color: #d0d2d5;
  }
  .el-main {
    margin: 15px;
    overflow: hidden;
    background: var(--el-color-info-light-9);
  }
  </style>
<template>
    <div class="affix-container">
        <span style="font-size: 50px">公告</span>
      <div class="loader">
        <el-table :data="tableData" style="width: 100%">
        <el-table-column align="center" label="公告" prop="content" />
        <el-table-column align="center" label="发布时间" prop="createtime" :formatter="dateFormat"/>
      </el-table>
      <el-row>
          <el-col>
            <el-pagination background
            layout="prev, pager, next"
            :page-size="limit" 
            :total="totalCount" 
            :current-page="currentPage"
            @current-change="pageChange" />
          </el-col>
        </el-row>
      </div>	  
    </div>
  </template>
  
  <script setup>
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import moment from "moment";
import axios from "axios";
  
  //时间格式
const dateFormat = (row, column) => {
  //时间格式化
  var date = row[column.property];
  if (date == undefined) {
    return "";
  }
  return moment(date).format("YYYY-MM-DD HH:mm:ss");
};
  // 当前页
  let currentPage = ref(1)
  // 总条数
  let totalCount = ref(10)
  // 每页显示限制
  let limit = ref(5)
  const pageChange = (p) => {
    currentPage.value = p
    console.log(p);
  // 发起接口请求数据 , 请求参数中使用 currentPage.value 作为查询当前页码
  axios.post(
      "http://localhost:8086/book/message/findall/" +
        currentPage.value +
        "/" +
        limit.value
    )
    .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum);
      tableData.value = reqs.data.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

//列表
const tableData = ref([]);
//异步发送请求到后端
axios.post(
    "http://localhost:8086/book/message/findall/" +
      currentPage.value +
      "/" +
      limit.value
  )
  .then((reqs) => {
    console.log(reqs);
    totalCount.value = parseInt(reqs.data.totalNum);
    tableData.value = reqs.data.data;
  })
  .catch((error) => {
    console.log(error);
  });
  </script>
  
  <style scoped>
  .affix-container {
    text-align: center;
    height: 100%;
    border-radius: 4px;
    background: var(--el-color-info-light-9);
  }
  .loader {
    padding: 5px 150px;
  }
  </style>
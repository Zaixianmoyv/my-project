<template>
    <div class="emps">
    <div class="emps_1">
      <el-row>
        <el-col :span="5" :offset="1">
          <el-input
            v-model="input.name"
            class="w-50 m-2"
            placeholder="请输入查询姓名"
            :prefix-icon="Search"
          />
        </el-col>
        <el-col :span="1" :offset="1">
          <el-button type="info" @click="search" style="margin-left: 5px" round>查询</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="emps_2">
  <el-table :data="tableData" style="width: 100%">
    <el-table-column align="center" label="账号名" prop="username" />
    <el-table-column align="center" label="姓名" prop="name" />
    <el-table-column align="center" label="性别" prop="sex" />
    <el-table-column align="center" label="电话" prop="phone" />
    <el-table-column align="center" label="住址" prop="address" />
    <el-table-column align="center" label="信誉值" prop="reputation" />
    <el-table-column label="操作" >
    <template #default="scope">
        <el-button size="small" v-if="scope.row.status=='1'" type="success" @click="handleDelete(scope.row)" round>已启用</el-button>
        <el-button size="small" v-else type="danger" @click="handleDelete(scope.row)" round>已禁用</el-button>
    </template>
    </el-table-column>
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
import { reactive, ref } from 'vue'
import { Search } from "@element-plus/icons-vue";
import { ElMessage,ElMessageBox } from 'element-plus'
import axios  from 'axios'

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
axios.post("http://localhost:8086/book/student/findall/" + currentPage.value + "/"+ limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value= parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

const input = ref({
  name: ""
})

const search = () => {
        // tableData.value = tableData.value.filter(v => v.name.toLowerCase().includes(name.value.toLowerCase()))
        //异步发送请求到后端
  axios.post("http://localhost:8086/book/student/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum)
      tableData.value = reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}


//禁用or启用
const handleDelete = (row) => {
  console.log(row.status);
  if(row.status==1){
    axios.post("http://localhost:8086/book/student/status/0/"+row.id)
      .then((reqs) => {
          console.log(reqs);
          search();
      }).catch((error) => {
            console.log(error);
      });
   }
   if(row.status==0){
    axios.post("http://localhost:8086/book/student/status/1/"+row.id)
      .then((reqs) => {
          console.log(reqs);
          search();
      }).catch((error) => {
            console.log(error);
      });
   }
};

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/student/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value=parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
</script>

<style scoped>
.emps_1 {
  padding: 10px ;
}
.el-button {
  margin-right: 12px;
}
.el-button+.el-button {
  margin-left: 0;
}
</style>
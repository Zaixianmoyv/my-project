<template>
    <div class="emps">
    <div class="emps_1">
      <el-row>
        <el-col :span="5" :offset="1">
          <el-input
            v-model="input.studentid"
            class="w-50 m-2"
            placeholder="请输入查询ID"
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
    <el-table-column align="center" label="借阅人ID" prop="studentid" />
    <el-table-column align="center" label="图书名称" prop="bookName" />
    <el-table-column align="center" label="借阅人姓名" prop="studentName" />
    <el-table-column align="center" label="借阅时间" prop="createtime" :formatter="dateFormat"/>
    <el-table-column align="center" label="归还时间" prop="backtime" :formatter="dateFormat"/>
    <el-table-column align="center" label="还书状态" prop="status">
    <template #default="scope">
            <span>{{ scope.row.sback == '0' ? '未归还': (scope.row.mback == '0' ? '归还中...' : '已归还') }}</span>
          </template>
    </el-table-column>
    <el-table-column label="操作" >
    <template #default="scope">
        <el-button size="small" v-if="scope.row.status=='1'" type="info" @click="handleEdit(scope.row)" round disabled>已确认</el-button>
        <el-button size="small" v-else-if="scope.row.sback=='1'" type="success" @click="handleEdit(scope.row)" round>请确认</el-button>
        <el-button size="small" v-else-if="scope.row.sback=='0'" type="info" @click="handleEdit(scope.row)" round disabled>不可操作</el-button>
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
import { ref } from 'vue'
import { Search } from "@element-plus/icons-vue";
import { ElMessage } from 'element-plus'
import moment from 'moment'
import axios  from 'axios'

//时间格式
const dateFormat=(row,column)=>{
   //时间格式化
    var date = row[column.property];
    if (date == undefined) {
      return "";
    }
    return moment(date).format("YYYY-MM-DD");
}

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
axios.post("http://localhost:8086/book/borrow/findall/" + currentPage.value + "/"+ limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value= parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

//编辑
const handleEdit = (row) => {
  console.log(row.mback);
  if(row.mback==0) {
  axios.post("http://localhost:8086/book/borrow/updateStatus/1/"+ row.id)
      .then((reqs) => {
          console.log(reqs);
          search();
      }).catch((error) => {
            console.log(error);
      })
    }
};

const input = ref({
  studentid: ""
})

const search = () => {
  axios.post("http://localhost:8086/book/borrow/findall/"+currentPage.value+"/"+limit.value, input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum)
      tableData.value = reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/borrow/findall/"+currentPage.value+"/"+limit.value,input.value)
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
<template>
        <div class="s-content">
            <div class="block text-center">
              <el-carousel height="400px">
            <el-carousel-item v-for="item in dataList" :key="item">
            <div class="user-w">
              <img class="user" :src="item.img" :alt="null" />
            </div>
            </el-carousel-item>
            </el-carousel>
            </div> 
        </div>
    <el-divider />
    <el-divider />
    <div class="dishes">
      <div class="dishes_1">
        <el-row>
          <el-col :span="20" :offset="1">
            <el-input
              v-model="input.name"
              class="w-50 m-2"
              placeholder="请输入查询书名"
              :prefix-icon="Search"/>
          </el-col>
          <el-col :span="1" :offset="1">
            <el-button @click="search" style="margin-left: 5px; background-color: #2FB2DE; color: white;">查询</el-button>
          </el-col>
        </el-row>
      </div>
      <div class="dishes_2">
    <el-table :data="tableData" style="width: 100%" border>
      <el-table-column align="center" label="图书编号" prop="id" />
       <el-table-column align="center" label="图书图片" prop="img"> <!-- text-align="center" width="100px" -->
    <template #default="scope">
              <div class="demo-image__preview" prop="img">
                <el-image 
                fit= "cover" 
                style="width: 100px; height: 100px"
                :zoom-rate="1.2"
                :max-scale="7"
                :min-scale="0.2"
                :preview-src-list="[scope.row.image]"
                :preview-teleported="true"
                :close-on-press-escape="true"
                :src="scope.row.image"  alt="null"/>
              </div>
            </template>
      </el-table-column>
      <el-table-column align="center" label="图书名称" prop="name" />
      <el-table-column align="center" label="图书类型" prop="sortname" />
      <el-table-column align="center" label="库存量" prop="number" />
      <el-table-column align="center" label="作者" prop="author" />
      <el-table-column align="center" label="描述" prop="description" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
            <span  class="cz0" v-if="scope.row.number != 0" @click="handleAdd(scope.row)" round>
              <Edit class="icons"/><span>借阅</span>
            </span>
            <span  class="cz1" v-else @click="handleDelete(scope.row)" round disabled>无库存</span>
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
import { ElMessage, ElMessageBox } from "element-plus";
import axios  from 'axios'
import { useRouter} from "vue-router";

const sid = ref(sessionStorage.getItem("sid"));
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
axios.post("http://localhost:8086/book/books/findall/" + currentPage.value + "/"+ limit.value, input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}


  const dataList = ref ([
  {
    img: require('../assets/img/book1-1.png'),
  },
  {
    img: require('../assets/img/book1-2.png'),
  },
  {
    img: require('../assets/img/book1-3.png'),
  },
  {
    img: require('../assets/img/book1-5.png'),
  },
]);

const input = ref({
  name: "",
})

const handleAdd = (row) => {
      console.log(row.id);
      console.log(sid.value)
      axios.post("http://localhost:8086/book/borrow/borrows/" + row.id + "/" + sid.value)
        .then((reqs) => {
          if (reqs.data.code === 1000) {
            ElMessage({
              showClose: true,
              message: "借阅成功",
              type: "success",
            });
            return;
          }
          ElMessage({
            showClose: true,
            message: "借阅失败",
            type: "error",
          });
        })
        .catch((error) => {
          console.log(error);
        }).finally(() => {
          search();
        });
}

const search = () => {
  axios.post("http://localhost:8086/book/books/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value =parseInt(reqs.data.totalNum)
      tableData.value = reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/books/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value=parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
  </script>
  
<style scoped>
.el-divider {
  margin: 0px;
}
.el-button {
    margin-right: 12px;
  }
.el-button+.el-button {
    margin-left: 0;
  }

.el-carousel__item h3 {
  background: var(--el-color-info-light-9);
  opacity: 0.75;
  line-height: 250px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background: var(--el-color-info-light-9);
}

.el-carousel__item:nth-child(2n + 1) {
  background: var(--el-color-info-light-9);
}

.s-content {
    width: 100%;
    padding: 30px 0px 12px;
    height: 400px;
    background: var(--el-color-info-light-9);
}

.user-w {
  display: flex;
  justify-content: center;
}
.user {
  padding-top: 10px;
  width: 80%;
  height: 380px;
}

.icons {
    width: 15px;
    height: 15px;
}

.cz0 {
  cursor: pointer;
  color: rgb(113, 113, 236);
  
}
.cz0:hover {
  color: blue;
}
.cz1 {
  cursor: pointer;
  color: rgb(164, 164, 226);
}
.cz1:hover {
  cursor: not-allowed;
}

.dishes {
  padding: 12px 100px;
  background: var(--el-color-info-light-9);
  padding-bottom: 10px;
}

.dishes_1 {
  padding: 10px 50px;
  display: flex;
  justify-content: center;
}
.dishes_2 {
  padding-bottom: 30px;
}
  </style>
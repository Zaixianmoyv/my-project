<template>
    <el-container class="all">
        <div class="s-content">
            <div class="block text-center">
            <el-carousel height="400px" :autoplay="false">
            <el-carousel-item v-for="item in dataList" :key="item">
            <div class="user-w">
              <img class="user" :src="item.img" :alt="null" />
            </div>
            </el-carousel-item>
            </el-carousel>
            </div> 
        </div>
        <el-divider />
        <div class="z-content">
            <p style="padding: 10px 0px; color: #2FB2DE; font-size: large;">热门推荐</p>
            <div class="scroll-image">
                <el-carousel :interval="4000" type="card" height="420px">
                <el-carousel-item v-for="item in dataList1" :key="item">
                <div class="book-w">
              <img class="book" :src="item.img" :alt="null" />
                </div>
                </el-carousel-item>
                </el-carousel>
            </div>
        </div>
        <el-divider />
        <div class="x-content">
            <p style="padding: 10px 0px; color: #2FB2DE; font-size: large;">新书推荐</p>
            <div class="dishes">
            <div class="dishes_2">
  <el-table :data="tableData" style="width: 100%" border>
    <el-table-column align="center" label="图书编号" prop="id" />
     <el-table-column align="center" label="图书图片" prop="image"> <!-- text-align="center" width="100px" -->
  <template #default="scope">
            <div class="demo-image__preview" prop="image">
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
</div>
    </div>
        </div>
      </el-container>
</template>

<script setup>
import {ref } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios  from 'axios'

const sid = ref(sessionStorage.getItem("sid"));
const dataList = ref ([
  {
    img: require('../assets/img/home1-8.jpg'),
  },
  {
    img: require('../assets/img/home1-7.jpg'),
  },
  {
    img: require('../assets/img/home1-6.jpg'),
  },
  {
    img: require('../assets/img/home1-5.jpg'),
  },
]);

const dataList1 = ref ([
  {
    img: require('../assets/img/home2-1.png'),
  },
  {
    img: require('../assets/img/home2-2.png'),
  },
  {
    img: require('../assets/img/home2-3.png'),
  },
  {
    img: require('../assets/img/home2-4.png'),
  },
  // {
  //   img: require('../assets/img/home2-5.png'),
  // },
  // {
  //   img: require('../assets/img/home2-6.png'),
  // },
  // {
  //   img: require('../assets/img/home2-7.png'),
  // },
]);

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
          axios.post("http://localhost:8086/book/books/findallss/1")
  .then((reqs) => {
      console.log(reqs);
      // totalCount.value=parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
        });
}

const handleDelete = (index, row) => {
  // tableData.value.value.scope.row.status = "0";
  console.log(index, row)
}

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/books/findallss/1")
  .then((reqs) => {
      console.log(reqs);
      // totalCount.value=parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
</script>

<style scoped>
.el-divider {
  margin: 0px;
}
.all {
    display: inline;
    width: 100%;
    height: 100%;
    background: var(--el-color-info-light-9);
}

.s-content {
    width: 100%;
    padding: 30px 0px 12px;
    height: 400px;
    background: var(--el-color-info-light-9);
    /* background-color: rgb(251, 252, 252); */
}

.z-content {
  padding: 12px 150px;
  background: var(--el-color-info-light-9);
}

.el-carousel__item h3 {
  background: var(--el-color-info-light-9);
  opacity: 0.75;
  line-height: 250px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background: var(--el-color-info-light-9), 0.1;
}

.el-carousel__item:nth-child(2n + 1) {
  background: var(--el-color-info-light-9), 0.1;
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

.book-w {
  display: flex;
  justify-content: center;
}

.x-content {
  padding: 12px 150px;
  background: var(--el-color-info-light-9);
  padding-bottom: 20px;
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


.dishes2 {
    text-align: center;
}
</style>
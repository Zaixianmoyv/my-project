<template>
  <div class="dishes">
    <div class="dishes_1">
      <el-row>
        <el-col :span="5" :offset="1">
          <el-input
            v-model="input.name"
            class="w-50 m-2"
            placeholder="请输入查询书名"
            :prefix-icon="Search"/>
        </el-col>
        <el-col :span="1" :offset="1">
          <el-button type="info" @click="search()" style="margin-left: 5px" round>查询</el-button>
        </el-col>
        <el-col :span="4" :offset="9">
          <el-button 
          type="info" 
          @click="addBook('add')"
          round>添加图书</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="dishes_2">
  <el-table :data="tableData" style="width: 100%" border>
    <el-table-column align="center" label="图书编号" prop="id" />
     <el-table-column align="center" label="图书图片" prop="image"> 
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
    <el-table-column align="center" label="作者" prop="author" />
    <el-table-column align="center" label="价格" prop="price" />
    <el-table-column label="操作">
      <template #default="scope">
        <el-button size="small" type="info" @click="addBook('update',scope.row)" round>编辑</el-button>
        <el-button size="small" type="danger" @click.prevent="handleDelete(scope.$index, scope.row)" round>下架</el-button>
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

//新增菜品
const addBook=(str,obj)=>{
  if(str=="add"){
    console.log(str);
    router.push({ path: '/main/addbook', query: {'comms':str}})
  }
  if(str=="update"){
    console.log(str+"   "+obj.id)
    let did=obj.id;
    router.push({ path: '/main/addbook', query: {'did':did,'comms':str}})
  }
}

const router = useRouter();
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

//删除
const handleDelete = (index,row) => {
  ElMessageBox.confirm("确定删除此书籍吗?", "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      console.log(index+"    "+row.id);
      tableData.value.splice(index,1)
      axios.post("http://localhost:8086/book/books/delete/"+row.id)
        .then((reqs) => {
            console.log(reqs);
        axios.post("http://localhost:8086/book/books/findAllBook/"+currentPage.value+"/"+limit.value,input.value)
        .then((reqs) => {
        console.log(reqs);
        totalCount.value =parseInt(reqs.data.totalNum)
        tableData.value = reqs.data.data
    }).catch((error) => {
        console.log(error);
  });

            ElMessage({
              type: "success",
              message: reqs.data.msg,
            });
        }).catch((error) => {
              console.log(error);
      });
    }).catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      })
    });
};

const input = ref({
  name: "",
})

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
.dishes_1 {
  padding: 10px ;
}
.el-button {
  margin-right: 12px;
}
.el-button+.el-button {
  margin-left: 0;
}
</style>
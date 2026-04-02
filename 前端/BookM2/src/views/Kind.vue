<template>
    <div class="menu">
    <div class="menu_1">
      <el-row>
        <el-col :span="5" :offset="1">
          <el-input
            v-model="input.name"
            class="w-50 m-2"
            placeholder="请输入查询分类名称"
            :prefix-icon="Search"/>
        </el-col>
        <el-col :span="1" :offset="1">
          <el-button type="info" @click="search()" style="margin-left: 5px" round>查询</el-button>
        </el-col>
        <el-col :span="4" :offset="9">
          <el-button
            type="info"
            @click="add('add')"
            round>添加图书分类</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="menu_2">
  <el-table :data="tableData" style="width: 100%">
    <el-table-column align="center" label="类型名称" prop="name" />
    <el-table-column align="center" label="备注" prop="description" />
    <el-table-column label="操作">
      <template #default="scope">
          <el-button size="small" type="info" @click="handleEdit(scope.row)" round>编辑</el-button>
          <el-button size="small" type="danger" @click.prevent="handleDelete(scope.$index, scope.row)" round>删除</el-button>
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
    <!-- 添加弹出框 -->
    <el-dialog
    v-model="dialogFormVisible"
    :model="type"
    :title="title"
    width="30%"
    align-center>
    <el-form
      ref="ruleFormRef"
      label-width="90px"
      :model="form"
      :rules="rules">
      <el-form-item label="分类名称:" required prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item label="描述:" required prop="description">
        <el-input type="textarea" v-model="form.description" autosize/>
      </el-form-item>
      <el-form-item>
        <el-button type="info" @click="submitForm(ruleFormRef)" round>
          保存
        </el-button>
        <el-button @click="dialogFormVisible = false" round>取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import {ref } from 'vue'
import { Search } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from 'element-plus'
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
axios.post("http://localhost:8086/book/booksort/findall/" + currentPage.value + "/"+ limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value= parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

const ruleFormRef = ref(null);
const form = ref({
  id: "",
  name: "",
  description: "",
});

//表单验证
const rules = ref({
  name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
  description: [{ required: true, message: "描述不能为空", trigger: "blur" }],
});


//重置的操作;
const resetForm = (val) => {
  debugger;
  if (!val) return;
  val.resetFields(); // resetFields 函数：是将表单字段的值重置为其默认值;
};

const dialogFormVisible = ref(false);
const type = ref();
const title = ref("")

const add = (types)=>{
   dialogFormVisible.value=true;
   console.log(type);
   if(types=="add"){
    title.value="添加图书分类"
    type.value=types
   }
   if(types=="update"){
    title.value="修改图书分类"
    type.value=types
   }
}

//编辑
const handleEdit = (row) => {
  dialogFormVisible.value=true;
  title.value="修改图书分类"
  type.value="update"
  console.log(row);
  form.value=row;
};

const submitForm = () => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      //添加员工信息
      if(type.value==="add"){
        console.log(type.value);
          console.log(form.value);
          console.log(type.value);
          // 注意：只有当所有的规则都满足后，此时的 valid 的值才为 true，才能执行下面的值;
        axios.post("http://localhost:8086/book/booksort/addSort",form.value)
          .then((reqs) => {
            if(reqs.data.code===1003){
              dialogFormVisible.value=false;
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: 'success',
              })
              return
            }
            ElMessage({
                  showClose: true,
                  message:reqs.data.msg, 
                  type: 'error',
              })
          }).catch((error) => {
                console.log(error);
          }).finally(()=>{
            search()
          })
      }
      //修改员工信息
      if(type.value==="update"){
          console.log(type.value);
          console.log(form.value);
          axios.post("http://localhost:8086/book/booksort/updateSort",form.value)
          .then((reqs) => {
            if(reqs.data.code===10013){
              dialogFormVisible.value=false;
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: 'success',
              })
              return
            }
            ElMessage({
                  showClose: true,
                  message:reqs.data.msg, 
                  type: 'error',
              })
          }).catch((error) => {
                console.log(error);
          }).finally(()=>{
            search()
          })
      }
    } else {
      console.log("error submit!");
      return false;
    }
  });
};

const input = ref({
  name: ""
})

const search = () => {
        // tableData.value = tableData.value.filter(v => v.name.toLowerCase().includes(name.value.toLowerCase()))
        //异步发送请求到后端
  axios.post("http://localhost:8086/book/booksort/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum)
      tableData.value = reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

//删除
const handleDelete = (index, row) => {
  ElMessageBox.confirm("确定删除此图书分类吗?", "警告", {
    cancelButtonText: "取消",
    confirmButtonText: "确定",
    type: "warning",
  })
    .then(() => {
      console.log(index + "    " + row.id);
      tableData.value.splice(index, 1);
    axios.post("http://localhost:8086/book/booksort/deleteSort/"+row.id)
    .then((reqs) => {
          console.log(reqs);
          search();
          ElMessage({
            type: "success",
            message: reqs.data.msg,
          });
        })
        .catch((error) => {
          console.log(error);
        });
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/booksort/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value=parseInt(reqs.data.totalNum)
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  })
</script>

<style scoped>
.menu_1 {
  padding: 10px ;
}
.el-button {
  margin-right: 12px;
}
.el-button+.el-button {
  margin-left: 0;
}
</style>
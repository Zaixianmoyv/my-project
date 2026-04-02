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
        <el-button type="info" @click="search()" style="margin-left: 5px" round>查询</el-button>
      </el-col>
      <el-col :span="4" :offset="9">
        <el-button
          type="info"
          @click="add('add')"
          round>添加管理员</el-button>
      </el-col>
    </el-row>
  </div>
  <div class="emps_2">
<el-table :data="tableData" style="width: 100%">
  <el-table-column align="center" label="编号" prop="id"/>
  <el-table-column align="center" label="姓名" prop="name" />
  <el-table-column align="center" label="性别" prop="sex" />
  <el-table-column align="center" label="账号" prop="username" />
  <el-table-column align="center" label="电话" prop="phone" />
  <el-table-column align="center" label="操作" >
    <template #default="scope">
        <el-button size="small" type="info" @click="handleEdit(scope.row)" round>编辑</el-button>
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
    :rules="rules"
  >
    <el-form-item label="姓名:" required prop="name">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item required label="性别:">
      <el-radio-group required v-model="form.sex">
        <el-radio label="男" />
        <el-radio label="女" />
      </el-radio-group>
    </el-form-item>
    <el-form-item label="账号名:" required prop="username">
      <el-input v-model="form.username" />
    </el-form-item>
    <el-form-item label="手机号码:" required prop="phone">
      <el-input v-model="form.phone" />
    </el-form-item>
    <el-form-item>
      <el-button type="info" @click="submitForm(ruleFormRef)" round>
        保存
      </el-button>
      <el-button @click="resetForm(ruleFormRef)" round>取消</el-button>
    </el-form-item>
  </el-form>
</el-dialog>
</template>

<script setup>
import {ref } from 'vue'
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
axios.post("http://localhost:8086/book/manegers/findall/" + currentPage.value + "/"+ limit.value,input.value)
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
username: "",
phone: "",
sex: "",
status: "",
});

//表单验证
const rules = ref({
name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
username: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
phone: [{ required: true, message: "手机号码不能为空", trigger: "blur" }],
sex: [{ required: true, message: "不能为空", trigger: "blur" }],
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
    title.value="添加管理员"
    type.value=types
   }
   if(types=="update"){
    title.value="修改管理员"
    type.value=types
   }
}

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
        axios.post("http://localhost:8086/book/manegers/reggie",form.value)
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
        title.value = "修改管理员";
          console.log(type.value);
          console.log(form.value);
          axios.post("http://localhost:8086/book/manegers/update",form.value)
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
  axios.post("http://localhost:8086/book/manegers/findall/"+currentPage.value+"/"+limit.value,input.value)
  .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum)
      tableData.value = reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
}

//编辑
const handleEdit = (row) => {
  dialogFormVisible.value=true;
  title.value="修改管理员"
  type.value="update"
  console.log(row);
  form.value=row;
};

//禁用or启用
const handleDelete = (row) => {
  console.log(row.status);
  if(row.status==1){
    axios.post("http://localhost:8086/book/manegers/status/0/"+row.id)
      .then((reqs) => {
          console.log(reqs);
          search();
      }).catch((error) => {
            console.log(error);
      });
   }
   if(row.status==0){
    axios.post("http://localhost:8086/book/manegers/status/1/"+row.id)
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
axios.post("http://localhost:8086/book/manegers/findall/"+currentPage.value+"/"+limit.value,input.value)
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

.img {
  width: 300px;
  height: 200px;
  background-color: aqua;
}
.el-button {
margin-right: 12px;
}
.el-button+.el-button {
margin-left: 0;
}
</style>
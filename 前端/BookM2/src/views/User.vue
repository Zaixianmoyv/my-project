<template>
    <div class="background">
    <div class="s-content">
      <div style="display: flex; width: 34%;">
       <div class="userimage">
        <img class="user" :src="imageUrl" alt="" />
      </div>
       <div class="username">
        <p class="name">
        <span>昵称：</span>
        <span class="nname">{{ form.name }}</span>
        </p> 
        <el-button class="edit" @click="handleEdit()">修改信息</el-button>
       </div>
      </div>
       <div class="book">
        <span class="lmbook">已借图书：</span>
        <span class="mbook">{{ 2 - form.number }}/2</span>
      </div>
       <div class="time">
        <span class="lmbook">信誉度：</span>
        <span class="mbook">{{ form.reputation }}</span>
      </div>
    </div>
<el-divider />
<el-divider />
  <div class="dishes">
<el-table :data="tableData"  style="width: 90%">
  <el-table-column align="center" label="ID" prop="bookid" />
  <el-table-column align="center" label="书名" prop="bookName" />
  <!-- <el-table-column align="center" label="图书类型" prop="sortname" /> -->
  <el-table-column align="center" label="作者" prop="author" />
  <el-table-column align="center" label="借书时间" prop="createtime" :formatter="dateFormat"/>
  <el-table-column align="center" label="应归还时间" prop="endtime" :formatter="dateFormat"/>
  <el-table-column align="center" label="操作">
    <template #default="scope">
        <!-- <span  class="cz0" v-if="scope.row.sback == 1 && scope.row.mback == 0" @click="handleDelete(scope.row)" round>
          <SoldOut class="icons"/><span>归还z</span>
        </span> -->
        <span class="cz0"  v-if="scope.row.status=='1'" type="info" @click="handleDelete(scope.row)" round disabled>已归还</span>
        <span class="cz0" v-else-if="scope.row.sback=='1'" type="success" @click="handleDelete(scope.row)" round>归还中</span>
        <span class="cz0" v-else-if="scope.row.sback=='0'" type="info" @click="handleDelete(scope.row)" round disabled><SoldOut class="icons"/><span>归还</span></span>
    </template>
  </el-table-column>
</el-table>
</div>
</div>
 <!-- 添加弹出框 -->
 <el-dialog 
    v-model="dialogFormVisible"
    title="个人信息"
    width="350px"
    align-center>
    <el-form
      ref="ruleFormRef"
      label-width="90px"
      :model="form"
      :rules="rules">
    <el-form-item label="头像:" prop="image">
      <span>
      <el-upload
              class="avatar-uploader"
              action="http://localhost:8086/book/books/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :on-change="onChange"
              ref="uploadRef">
              <img v-if="imageUrl" :src="imageUrl" class="avatar" /> 
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
      </span>
      </el-form-item>
      <el-form-item label="昵称:" required prop="name">
        <el-input v-model="form.name" />
      </el-form-item>
      <el-form-item>
        <el-button style="background-color: #2FB2DE; color: white;" @click="submitForm(ruleFormRef)" round>
          保存
        </el-button>
        <el-button @click="dialogFormVisible = false" round>取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import { ref , onMounted} from 'vue'
import { Search } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";
import moment from 'moment'
import axios  from 'axios'
import { useRouter} from "vue-router";

const sid = ref(sessionStorage.getItem("sid"));
const imageUrl = ref();
const uploadRef = ref();
const ruleFormRef = ref(null);
//时间格式
const dateFormat=(row,column)=>{
   //时间格式化
    var date = row[column.property];
    if (date == undefined) {
      return "";
    }
    return moment(date).format("YYYY-MM-DD");
}
let dialogFormVisible = ref(false)
const form = ref({
id: "",
image: "",
name: "",
});
//表单验证
const rules = ref({
  name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
});

  //编辑数据 先赋值到表单再弹窗
const handleEdit = () => {
  dialogFormVisible.value=true;
  axios.post("http://localhost:8086/book/student/update",form.value)
      .then((reqs) => {
          console.log(reqs);
      }).catch((error) => {
            console.log(error);
      })
    }

const handleDelete = (row) => {
  if(row.sback == 0) {
    axios.post("http://localhost:8086/book/borrow/updatess/1/" + row.id)
    .then((reqs) => {
      console.log(reqs);
      tableData.value=reqs.data.data
    }).catch((error) => {
        console.log(error);
    }).finally(() => {
      axios.post("http://localhost:8086/book/borrow/findallss/0/"  + sid.value)
      .then((reqs) => {
      console.log(reqs);
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
    })
}
}

//保存
const submitForm = () => {
  console.log(imageUrl.value)
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
        let params = { ...form.value };
        params.image = imageUrl.value;
        if(!imageUrl.value){
          ElMessage({
              showClose: true,
              message: "请上传图片",
              type: "error",
            });
          return
        }
        axios
          .post("http://localhost:8086/book/student/update", params)
          .then((reqs) => {
            if (reqs.data.code == 1000) {
              dialogFormVisible.value=false;
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: "success",
              });
              return
            }
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "error",
            });
          })
          .catch((error) => {
            console.log(error);
          }).finally(() => {
            initDish();
          })
    }
  });
};

const handleAvatarSuccess = (response, file, fileList) => {
  console.log("$###" + response.data);
  console.log("$###"+ response.code);
  console.log("$###"+ response.msg);
  console.log("$###" + file.name);
  console.log("$###" + fileList);
  if(response.code=="10016"){
    ElMessage({
        showClose: true,
        message: response.msg,
        type: "success",
    });
    //图片回显
    imageUrl.value = "http://localhost:8086/book/photo/"+response.data;
    return
  }
  ElMessage({
        showClose: true,
        message: response.msg,
        type: "error",
    });
};
const onChange = (file) => {
  if (file) {
    const suffix = file.name.split(".")[1];
    const size = file.size / 1024 / 1024 < 2;
    if (["png", "jpeg", "jpg", "JPG"].indexOf(suffix) < 0) {
      ElMessage({
        showClose: true,
        message: "上传图片只支持 png、jpeg、jpg 、JPG格式！",
        type: "error",
      });
      uploadRef.value.clearFiles();
      return false;
    }
    if (!size) {
      ElMessage({
        showClose: true,
        message: "上传文件大小不能超过 2MB!",
        type: "error",
      });
      return false;
    }
    return file;
  }
};

//数据初始化
onMounted(() => {
    initDish();
});
//根据id查询菜品信息
const initDish = () => {
  axios
    .post("http://localhost:8086/book/student/query/" + sid.value)
    .then((reqs) => {
      console.log(reqs);
      form.value = reqs.data.data;
      imageUrl.value = reqs.data.data.image;
    })
    .catch((error) => {
      console.log(error);
    });
};

//列表
const tableData = ref([])
//异步发送请求到后端
axios.post("http://localhost:8086/book/borrow/findallss/0/" + sid.value)
  .then((reqs) => {
      console.log(reqs);
      tableData.value=reqs.data.data
  }).catch((error) => {
        console.log(error);
  });
</script>

<style scoped>
.background {
    height: 100%;
    background: var(--el-color-info-light-9);
}

.el-button {
margin-right: 12px;
}
.el-button+.el-button {
margin-left: 0;
}

.el-carousel__item h3 {
color: #475669;
opacity: 0.75;
line-height: 250px;
margin: 0;
text-align: center;
}

.el-carousel__item:nth-child(2n) {
background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
background-color: #d3dce6;
}

.s-content {
    padding: 10px 60px 10px 60px;
    height: 200px;
    display: flex;
}

.userimage {
    width: 200px;
    height: 200px;
}

.user {
  width: 200px;
  height: 200px;
  border-radius: 50%;
}

.username {
    padding: 90px 20px 60px 20px;
    text-align: center;
    align-items: center;
}
.name {
  padding-bottom: 5px;
}

.nname {
  color: #2FB2DE;
  font-size: 30px;
  font-weight: bold;
  cursor: pointer;
}

.nname:hover {
  color: #FB7299;
}

.edit {
  margin: 0px;
  margin-top: 8px;
}

.book {
  width: 33%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.mbook {
  font-size: 40px;
  font-weight: 600;
  color: #2FB2DE;
}

.lmbook {
  padding-top: 15px;
}

.time {
  width: 33%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.edit:hover {
  color: #00A1D6;
  background-color: white;
}

.icons {
width: 15px;
height: 15px;
}

.cz0 {
cursor: pointer;
color: red;

}
.cz0:hover {
color: rgb(113, 113, 236);
}

.dishes {
  padding: 20px 0px 0px 0px;
  width: 100%;
  display: flex;
  justify-content: center;
}

.dashboard-container .container .tableBox .el-image img {
    width: 40px;
    border-radius: 5px;
  }
  
  .dashboard-container .container .pageList {
    text-align: center;
    margin-top: 30px;
  }
  
  .dashboard-container .container .tableLab .span-btn {
    cursor: pointer;
    display: inline-block;
    font-size: 14px;
    padding: 0 20px;
    color: #818693;
    border-right: solid 1px #d8dde3;
  }
  
  .dashboard-container .container .tableLab .el-button {
    margin-left: 10px;
  }
  
  .el-table-column--selection .cell {
    padding-left: 10px;
  }
  
  /* 添加 */
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
.avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 160px;
    line-height: 160px;
    text-align: center;
  }
.avatar {
    width: 160px;
    height: 160px;
    display: block;
  }
</style>
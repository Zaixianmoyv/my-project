<template>
  <el-header>
    <div class="l-content">
       <img class="title" src="../assets/img/title.jpg" alt="logo"/>
       <!-- <h3 class="title">Book</h3> -->
    </div>
<div class="r-content">
  <el-dropdown >
    <span class="el-dropdown-link" prop="image">
      <el-upload 
            class="avatar-uploader"
              action="http://localhost:8086/book/manegers/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :on-change="onChange"
              ref="uploadRef">
              <img class="user" :src="imageUrl" alt="" />
            </el-upload>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="clickMenu(item)">
        <span>退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  </div>
  </el-header>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted  } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios  from 'axios'
const router = useRouter();

const cid = ref(sessionStorage.getItem("userid"));
const mid = ref(sessionStorage.getItem("userimage"));
const imageUrl = ref("");
const uploadRef = ref();
//表单数据
const form = ref({
    id: "",
    image: '',
  });
let params = { ...form.value };   
const clickMenu = () => {
    sessionStorage.clear();
        axios.post("http://localhost:8086/book/manegers/loginout")
            .then((reqs) => {
            if (reqs.data.code == 6) {
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: "success",
              });
              return router.push ({
                path: "/managerlogin"
              })
            }
          })
      };

  const handleAvatarSuccess = (response) => {
  if(response.code=="10016"){
    params.image = imageUrl.value;
    params.id = cid.value;
    //图片回显
    imageUrl.value = "http://localhost:8086/book/photo/"+response.data;
    params.image = imageUrl.value;
    axios.post("http://localhost:8086/book/manegers/updateimage",params)
            .then((reqs) => {
            if (reqs.data.code == 10013) {
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
          });
        }
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

 //根据id查询管理员信息
const initDish = () => {
  axios
    .post("http://localhost:8086/book/manegers/query/" + cid.value)
    .then((reqs) => {
      console.log(reqs);
      form.value = reqs.data.data;
      imageUrl.value = reqs.data.data;
      console.log(imageUrl.value);
    })
    .catch((error) => {
      console.log(error);
    });
};
</script>

<style scoped>
header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width:100%;
    background-color:#545c64;
    border: none;
}
.el-dropdown-link {
  border: none;
  resize: none;
}
.user, .title {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
    resize: none;
}
.title {
  width: 50px;
  height: 50px;
  margin-left: 50px;
}

.l-content {
    display: flex;
    align-items: center;
}
h3 {
  /* padding: 0px 0px 0 50px; */
  color: rgb(247, 247, 247);
  font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
  font-size: larger;
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
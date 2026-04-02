<template>
    <div class="addBrand-container" id="food-add-app">
      <div class="container">
        <el-form
          ref="ruleForm"
          :model="form"
          :rules="rules"
          :inline="true"
          label-width="180px"
          class="demo-ruleForm"
        >
          <div>
            <el-form-item label="图书名称:" required  prop="name">
              <el-input
                v-model="form.name"
                placeholder="请填写图书名称"
                maxlength="20"
              />
            </el-form-item>
            <el-form-item
              label="图书分类:"
              required
              prop="sortid"
            >
              <el-select
                v-model="form.sortid"
                required
                placeholder="请选择图书分类"
              >
                <el-option v-for="(item,index) in dishList" :key="index" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </div>
          <div>
          <el-form-item
              label="作者:"
              required
              prop="author"
            >
              <el-input
                v-model="form.author"
                placeholder="请填写图书作者"
              />
            </el-form-item>
          <el-form-item
              label="版本:"
              prop="version"
            >
              <el-input
                v-model="form.version"
                placeholder="请填写图书版本"
              />
            </el-form-item>
          </div>
          <div>
          <el-form-item
              label="语言:"
              prop="language"
            >
              <el-input
                v-model="form.language"
                placeholder="请填写图书语言"
              />
            </el-form-item>
            <el-form-item
              label="图书价格:"
              required
              prop="price"
            >
              <el-input
                v-model="form.price"
                placeholder="请填写图书价格"
              />
            </el-form-item>
          </div>
          <div>
            <el-form-item
              label="图书图片:"
              prop="image"
              class="uploadImg"
            >
            <el-upload 
            class="avatar-uploader"
              action="http://localhost:8086/book/student/upload"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :on-change="onChange"
              ref="uploadRef">
              <img v-if="imageUrl" :src="imageUrl" class="avatar" /> 
            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
            </el-form-item>
          </div>
          <div class="address">
            <el-form-item
              label="图书描述:"
              prop="region"
            >
              <el-input
                v-model="form.description"
                type="textarea"
                :rows="3"
                autosize
                placeholder="图书描述，最长200字"
                maxlength="200"
              />
            </el-form-item>
          </div>
          <div class="subBox address" style="padding: 0 400px;">
            <el-form-item>
              <el-button
                type="info"
                @click="submitForm('ruleForm')"
                round
              >
                保存
              </el-button>
              <el-button 
              @click="goBack()"
              round
              >
                取消
              </el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import { ElMessage } from "element-plus";
  import axios from "axios";
  import { Plus } from "@element-plus/icons-vue";
  import { useRouter, useRoute } from "vue-router";
  const router = useRouter();
  const route = useRoute();
  
  const did = ref(route.query.did);
  const comm = ref(route.query.comms);
  const imageUrl = ref("");
  const dishList = ref([]);
  const dishFlavors = ref([]);
  const indexs = ref(0);
  const inputStyle = { flex: 1 };
  const uploadRef = ref();
  
  const ruleForm=ref(null)
  //表单数据
  const form = ref({
    id:"",
    name: "",
    price: "",
    image: '',
    description: "",
    sortid: "",
    author: "",
    language: "",
    version: "",
  });
  
  //表单验证
  const rules = ref({
    name: [{ required: true, message: "请填写书籍名称", trigger: "blur" }],
    sortid: [
      { required: true, message: "请选择书籍分类", trigger: "change" },
    ],
    price: [
      {
        required: true,
        // 'message': '请填写菜品价格',
        validator: (rules, value, callback) => {
          if (!value) {
            callback(new Error("请填写书籍价格"));
          } else {
            const reg = /^\d+(\.\d{0,2})?$/;
            if (reg.test(value)) {
              if (value < 10000) {
                callback();
              } else {
                callback(new Error("书籍价格小于10000"));
              }
            } else {
              callback(new Error("书籍价格格式只能为数字,且最多保留两位小数"));
            }
          }
        },
        trigger: "blur",
      },
    ],
  });
//保存
const submitForm = () => {
  console.log(imageUrl.value)
  if (!ruleForm.value) return;
  ruleForm.value.validate((valid) => {
    if (valid) {
      //添加菜品
      if (comm.value == "add") {
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
          .post("http://localhost:8086/book/books/add", params)
          .then((reqs) => {
            if (reqs.data.code == 1003) {
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: "success",
              });
              return  router.push("/main/book");
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
      //修改菜品
      if(comm.value=="update"){
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
          .post("http://localhost:8086/book/books/update", params)
          .then((reqs) => {
            if (reqs.data.code == 10013) {
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: "success",
              });
              return router.push("/main/book");
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
const goBack = () => {
  router.push("/main/book");
};
//数据初始化
onMounted(() => {
  //初始化菜品分类
  initCategoryName();
  if (comm.value == "update") {
    initDish();
  }
});
//初始化菜品分类
const initCategoryName = () => {
  axios.post("http://localhost:8086/book/booksort/findAll")
    .then((reqs) => {
      dishList.value=reqs.data.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

//根据id查询菜品信息
const initDish = () => {
  axios
    .post("http://localhost:8086/book/books/query/" + did.value)
    .then((reqs) => {
      console.log(reqs);
      form.value = reqs.data.data;
      imageUrl.value = reqs.data.data.image;
    })
    .catch((error) => {
      console.log(error);
    });
};
  </script>
  
  <style>
  .selectInput {
        position: relative;
        width: 100%;
        min-width: 100px;
      }
      .selectInput .flavorSelect {
        position: absolute;
        width: 100%;
        padding: 0 10px;
        border-radius: 3px;
        border: solid 1px #FF903D;
        line-height: 30px;
        text-align: center;
        background: #fff;
        top: 50px;
        z-index: 99;
      }
      .selectInput .flavorSelect .items {
        cursor: pointer;
        display: inline-block;
        width: 100%;
        line-height: 35px;
        border-bottom: solid 1px #f4f4f4;
        color: #666;
      }
      .selectInput .flavorSelect .none {
        font-size: 14px;
      }
  
      #food-add-app .uploadImg .el-form-item__label::before{
            content: '*';
            color: #F56C6C;
            margin-right: 4px;
        }
        .dashboard-container {
    padding: 20px;
  }
  .dashboard-container .container {
    background: #fff;
    position: relative;
    z-index: 1;
    padding: 30px 28px;
    border-radius: 4px;
  }
  .dashboard-container .container .tableBar {
    display: flex;
    margin-bottom: 20px;
    justify-content: space-between;
  }
  .dashboard-container .container .tableBox {
    width: 100%;
    border: solid 2px #f3f4f7;
    border-radius: 2px;
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
  .addBrand-container .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .addBrand-container .avatar-uploader .el-upload:hover {
    border-color: #409eff;
  }
  .addBrand-container .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 160px;
    line-height: 160px;
    text-align: center;
  }
  .addBrand-container .avatar {
    width: 160px;
    height: 160px;
    display: block;
  }
  /* .addBrand-container .el-form--inline .el-form-item__content {
    width: 293px;
  } */
  .addBrand-container .el-input {
    width: 293px;
  }
  
  .addBrand-container {
    margin: 30px;
  }
  .addBrand-container .container {
    position: relative;
    z-index: 1;
    background: #fff;
    padding: 30px;
    border-radius: 4px;
    min-height: 500px;
  }
  .addBrand-container .container .subBox {
    padding-top: 30px;
    text-align: center;
    border-top: solid 1px #f3f4f7;
  }
  
  .flavorBox {
    width: 777px;
  }
  .flavorBox .addBut {
    background: #ffc200;
    display: inline-block;
    padding: 0px 20px;
    border-radius: 3px;
    line-height: 30px;
    cursor: pointer;
    border-radius: 4px;
    color: #333333;
    font-weight: 500;
  }
  .flavorBox .flavor {
    border: solid 1px #dfe2e8;
    border-radius: 3px;
    padding: 15px;
    background: #fafafb;
  }
  .flavorBox .flavor .title {
    color: #606168;
  }
  .flavorBox .flavor .cont .items {
    display: flex;
    margin: 10px 0;
  }
  .flavorBox .flavor .cont .items .itTit {
    width: 150px;
    margin-right: 15px;
  }
  .flavorBox .flavor .cont .items .itTit input {
    width: 100%;
    line-height: 40px;
    border-radius: 3px;
    padding: 0 10px;
  }
  .flavorBox .flavor .cont .items .labItems {
    flex: 1;
    display: flex;
    flex-wrap: wrap;
    border-radius: 3px;
    min-height: 39px;
    border: solid 1px #d8dde3;
    background: #fff;
    padding: 0 5px;
  }
  .flavorBox .flavor .cont .items .labItems span {
    display: inline-block;
    color: #f19c59;
    margin: 5px;
    line-height: 26px;
    height: 26px;
    padding: 0 10px;
    background: #fdf4eb;
    border-radius: 3px;
    border: solid 1px #fae2cd;
  }
  .flavorBox .flavor .cont .items .labItems span i {
    cursor: pointer;
    font-style: normal;
  }
  .flavorBox .flavor .cont .items .labItems .inputBox {
    display: inline-block;
    width: 100%;
    height: 36px;
    line-height: 36px;
    overflow: hidden;
  }
  .flavorBox .flavor .cont .items .delFlavor {
    display: inline-block;
    padding: 0 10px;
    color: #f19c59;
    cursor: pointer;
  }
  
  .addBrand-container .address .el-form-item__content {
    width: 777px;
  } 
  .el-textarea__inner {
    width: 40%;
}
  
  .el-button--text {
    font-weight: 400;
    font-size: 13px;
  }
  
  .el-table td {
    font-size: 13px;
  }
  
  .el-table .cell,
  .el-table th div,
  .el-table--border td:first-child .cell,
  .el-table--border th:first-child .cell {
    padding-left: 12px;
  }
  </style>
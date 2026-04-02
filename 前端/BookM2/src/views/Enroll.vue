<template>
  <div class="common-layout">
      <el-container>
        <el-aside width="100%">
          <div class="login">
          <div class="login-s">
           <el-form :model="form" class="demo-form-inline" label-width="90px" 
           :hide-required-asterisk="true"
           ref="ruleFormRef" 
           :rules="rules">
            <p style="text-align: center; padding-bottom: 10px ; font-size: 40px; font-weight:500; color: #080808;">
              注册</p>
            <el-form-item label="用户名"  prop="username">
            <el-input v-model="form.username" placeholder="username" />
            </el-form-item>
            <el-form-item label="密码"  prop="password">
             <el-input v-model="form.password" placeholder="password" show-password/>
            </el-form-item>
            <el-form-item label="真实姓名"  prop="name">
            <el-input v-model="form.name" placeholder="name" />
            </el-form-item>
            <el-form-item label="性别"  prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio label="男" />
                <el-radio label="女" />
            </el-radio-group>
            </el-form-item>
            <el-form-item label="电话"  prop="phone">
             <el-input v-model="form.phone" placeholder="phone"/>
            </el-form-item>
            <el-form-item label="地址"  prop="address">
            <el-input v-model="form.address" placeholder="address" />
            </el-form-item>
             <p style="text-align: center">
            <el-button color="#a0cfff" @click="submitForm(item)" v-for="item in list" :index="item.path" :key="item.path">&nbsp;&nbsp;&nbsp;&nbsp;{{ item.label }}&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
            </p>
           </el-form>
          </div>
          <el-footer class="enroll">
            <div class="right">
            <router-link to="/login">返回</router-link>
            <router-view />
            </div>
          </el-footer>
        </div>
        </el-aside>
    </el-container>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios';


const ruleFormRef = ref(null);
const form =  ref({
  id: "",
  username: "",
  password: "",
  name: "",
  sex: "",
  phone: "",
  address: "",
  status: "",
});
//表单验证
const rules = ref({
  username: [{ required: true, message: "用户名不能为空", trigger: "blur" },
            {min: 2, max: 10, message: "长度在2到10个字符", trigger: "blur"}],
  password: [{ required: true, message: "密码不能为空", trigger: "blur" },
            {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}],
  name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
  sex: [{ required: true, message: "性别不能为空", trigger: "blur" }],
  phone: [{ required: true, message: "电话不能为空", trigger: "blur" }],
  address: [{ required: true, message: "地址不能为空", trigger: "blur" }],
  status: [{ required: true, message: "不能为空", trigger: "blur" }],
});
  const router = useRouter();
      const list = [
              {
                    path: "/login",
                    name: "login",
                    label: "Enroll",
                    url: "/login"

                },
      ];

  const submitForm = (item) => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
		axios.post("http://localhost:8086/book/student/reggie", form.value)
    .then((reqs) => {
      console.log(reqs);
      if(reqs.data.code === 10017) {
        ElMessage({
          showClose: true,
          message: reqs.data.msg,
          type: 'warning',
        })
        return
      }
      else if(reqs.data.code === 10011) {
      ElMessage({
          showClose: true,
          message: reqs.data.msg,
          type: 'success',
        })
        return router.push({
          name: item.name,
      });
    }
    })
  }
});
};
</script>

<style scoped>
.common-layout {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/bg1.jpg);
  background-size: 100% 100%;
}
.el-container {
  height: 100%;
  background-image: url(../assets/img/bb.gif);
}
.el-aside {
  display: flex;
  justify-content: center;
  align-items: center; /*垂直居中*/
  overflow: hidden;
}
.el-main {
  display: flex;
  justify-content: center;
  align-items: center;
}
.login {
  justify-content: center;
  width: 430px;
  height: 450px;
  background-color: rgba(252, 252, 252, 0.5);
}

.login-s {
  width: 360px;
  height: 380px;
  margin: 0px 30px;
  padding-top: 20px;
  color: #9fa1a9;
  align-items: center;
}
    
a:visited {
  color: rgb(31, 126, 215);
}

a:hover {
  color:rgb(40, 67, 190);
}

el-button {
  padding-top: 20px;
  margin-left: 10px;
  margin-right: 30px;
}

.enroll {
 display: flex;
 font-size: small;
 height: 20px;
 padding-top: 15px;
 width: 430px;
}
.right {
  text-align: right;
  width: 100%;
}
</style>
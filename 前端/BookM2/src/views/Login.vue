<template>
  <div class="common-layout">
    <el-container>
      <el-main width="50%">
        <!-- 动画 -->
        <div class="box">
          <ul class="minbox">
            <li></li>
            <li>
              <span class="text"></span>
            </li>
            <li></li>
            <li></li>
            <li></li>
            <li></li>
          </ul>
          <ol class="maxbox">
            <li style="font-size: 43px">Welcome</li>
            <li style="font-size: 43px">
              <span class="text">Welcome</span>
            </li>
            <li style="font-size: 43px"></li>
            <li style="font-size: 43px"></li>
            <li style="font-size: 43px">Welcome</li>
            <li style="font-size: 43px">Welcome</li>
          </ol>
        </div>
      </el-main>
      <el-aside width="50%">
        <div class="login">
          <div class="login-s">
            <el-form
              :model="form"
              class="demo-form-inline"
              label-width="90px"
              :hide-required-asterisk="true"
              ref="ruleFormRef"
              :rules="rules"
            >
              <p
                style="
                  text-align: center;
                  padding-bottom: 10px;
                  font-size: 40px;
                  font-weight: 500;
                  color: #080808;
                "
              >
                Book
              </p>
              <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" placeholder="username" />
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input
                  v-model="form.password"
                  placeholder="password"
                  show-password
                />
              </el-form-item>
              <!-- 验证码 -->
            <el-form-item prop="passwords">
            <el-input style="width: 150px; padding-right: 10px;"
            placeholder="验证码"
            v-model="form.passwords"
          ></el-input>
          <canvas ref="verify" class="img-verify" :width="state.width" :height="state.height" @click="handleDraw"></canvas>
        </el-form-item>

              <p style="text-align: center">
                <el-button
                  color="#a0cfff"
                  @click="submitForm(item)"
                  v-for="item in list"
                  :index="item.path"
                  :key="item.path"
                  >&nbsp;&nbsp;&nbsp;&nbsp;{{
                    item.label
                  }}&nbsp;&nbsp;&nbsp;&nbsp;</el-button
                >
              </p>
            </el-form>
          </div>
          <el-footer class="enroll">
            <div class="lm">管理员</div>
            <div class="left">
              <router-link to="/managerlogin">进入</router-link>
              <router-view />
            </div>
            <div class="rm">没有账号?马上</div>
            <div class="right">
              <router-link to="/enroll">注册</router-link>
              <router-view />
            </div>
          </el-footer>
        </div>
      </el-aside>
    </el-container>
  </div>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import axios from "axios";

const verify = ref(null)

const state = reactive({

  pool: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890', // 字符串

  width: 110,

  height: 35,

  imgCode: ''

})

defineExpose({ state })

onMounted(() => {

  // 初始化绘制图片验证码

  state.imgCode = draw()

})

// 点击图片重新绘制

const handleDraw = () => {

  state.imgCode = draw()

}

// 随机数

const randomNum = (min, max) => {

  return parseInt(Math.random() * (max - min) + min)

}

// 随机颜色

const randomColor = (min, max) => {

  const r = randomNum(min, max)

  const g = randomNum(min, max)

  const b = randomNum(min, max)

  return `rgb(${r},${g},${b})`

}

// 绘制图片

const draw = () => {

  // 3.填充背景颜色，背景颜色要浅一点

  const ctx = verify.value.getContext('2d')

  // 填充颜色

  ctx.fillStyle = randomColor(180, 230)

  // 填充的位置

  ctx.fillRect(0, 0, state.width, state.height)

  // 定义paramText

  let imgCode = ''

  // 4.随机产生字符串，并且随机旋转

  for (let i = 0; i < 4; i++) {

    // 随机的四个字

    const text = state.pool[randomNum(0, state.pool.length)]

    imgCode += text

    // 随机的字体大小

    const fontSize = randomNum(30, 30)

    // 字体随机的旋转角度

    const deg = randomNum(-30, 30)

    /*

      * 绘制文字并让四个文字在不同的位置显示的思路 :

      * 1、定义字体

      * 2、定义对齐方式

      * 3、填充不同的颜色

      * 4、保存当前的状态（以防止以上的状态受影响）

      * 5、平移translate()

      * 6、旋转 rotate()

      * 7、填充文字

      * 8、restore出栈

      * */

    ctx.font = fontSize + 'px Simhei'

    ctx.textBaseline = 'top'

    ctx.fillStyle = randomColor(80, 150)

    /*

      * save() 方法把当前状态的一份拷贝压入到一个保存图像状态的栈中。

      * 这就允许您临时地改变图像状态，

      * 然后，通过调用 restore() 来恢复以前的值。

      * save是入栈，restore是出栈。

      * 用来保存Canvas的状态。save之后，可以调用Canvas的平移、放缩、旋转、错切、裁剪等操作。 restore：用来恢复Canvas之前保存的状态。防止save后对Canvas执行的操作对后续的绘制有影响。

      *

      * */

    ctx.save()

    ctx.translate(30 * i + 15, 15)

    ctx.rotate((deg * Math.PI) / 180)

    // fillText() 方法在画布上绘制填色的文本。文本的默认颜色是黑色。

    // 请使用 font 属性来定义字体和字号，并使用 fillStyle 属性以另一种颜色/渐变来渲染文本。

    // context.fillText(text,x,y,maxWidth);

    ctx.fillText(text, -15 + 5, -15)

    ctx.restore()

  }

  // 5.随机产生5条干扰线,干扰线的颜色要浅一点

  for (let i = 0; i < 5; i++) {

    ctx.beginPath()

    ctx.moveTo(randomNum(0, state.width), randomNum(0, state.height))

    ctx.lineTo(randomNum(0, state.width), randomNum(0, state.height))

    ctx.strokeStyle = randomColor(180, 230)

    ctx.closePath()

    ctx.stroke()

  }

  // 6.随机产生40个干扰的小点

  for (let i = 0; i < 40; i++) {

    ctx.beginPath()

    ctx.arc(randomNum(0, state.width), randomNum(0, state.height), 1, 0, 2 * Math.PI)

    ctx.closePath()

    ctx.fillStyle = randomColor(150, 200)

    ctx.fill()

  }

  return imgCode

}

const ruleFormRef = ref(null);
const form = ref({
  id: "",
  username: "",
  password: "",
  passwords: "",
  status: "",
});

  //表单验证
const rules = ref({
  username: [{ required: true, message: "用户名不能为空", trigger: "blur" },
             {min: 2, max: 10, message: "长度在2到10个字符", trigger: "blur"},
            ],
  password: [{ required: true, message: "密码不能为空", trigger: "blur" },
             {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
],
  passwords: [{ required: true, message: "验证码不能为空", trigger: "blur" }],
});
const router = useRouter();
const list = [
  {
    path: "mainuser/Homeuser",
    name: "homeuser",
    label: "Login",
    url: "mainuser/homeuser",
  },
];

const submitForm = (item) => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8086/book/student/login", {
  username: form.value.username,
  password: form.value.password
})
        .then((reqs) => {
          console.log(reqs);
          if (reqs.data.code === 1) {
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "error",
            });
            return;
          }
          else if (reqs.data.code === 2) {
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "warning",
            });
            return;
          }
          else if (reqs.data.code === 3) {
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "warning",
            });
            return;
          }
          else if (reqs.data.code === 0) {
            sessionStorage.setItem("sid", reqs.data.data.id);
            sessionStorage.setItem("simage", reqs.data.data.image);
            console.log("**********" + reqs.data.data.image);
            console.log("************" + reqs.data.data.id);
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "success",
            });
            return router.push({
              name: item.name,
            });
          }
          else {
            ElMessage({
              showClose: true,
              message: "验证码输入错误",
              type: "warning",
            });
          }
        });
    }
  });
};

</script>

<style scoped>
.el-main {
  overflow: hidden;
}
li {
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 20px;
  font-weight: bolder;
  list-style: none;
}

.box {
  width: 200px;
  height: 200px;
  position: relative;
  margin: 300px auto;
  -webkit-transform-style: preserve-3d;
  -webkit-transform: rotateX(13deg);
  -webkit-animation: move 5s linear infinite;
}

.minbox {
  width: 100px;
  height: 100px;
  position: absolute;
  left: 50px;
  top: 50px;
  -webkit-transform-style: preserve-3d;
}

.minbox li {
  width: 100px;
  height: 100px;
  position: absolute;
  left: 0;
  top: 0;
}

.minbox li:nth-child(1) {
  /* background: url(../assets/img/bg_ii.png) no-repeat 0 0; */
  -webkit-transform: translateZ(50px);
}

.minbox li:nth-child(2) {
  /* background: url(../assets/img/bg_ii.png) no-repeat 0 0; */
  -webkit-transform: rotateX(180deg) translateZ(50px);
}

.text {
  display: inline-block;
  transform: rotate(-180deg);
}

.minbox li:nth-child(3) {
  /* background: url(../assets/img/bg_ii.png) no-repeat 0 0; */
  -webkit-transform: rotateX(-90deg) translateZ(50px);
}

.minbox li:nth-child(4) {
  /* background: url(../assets/img/bg_ii.png) no-repeat 0 0; */
  -webkit-transform: rotateX(90deg) translateZ(50px);
}

.minbox li:nth-child(5) {
  background: url(../assets/img/bg_ii.png) no-repeat 0 0;
  -webkit-transform: rotateY(-90deg) translateZ(0px);
}

.minbox li:nth-child(6) {
  background: url(../assets/img/bg_ii.png) no-repeat 0 0;
  -webkit-transform: rotateY(90deg) translateZ(0px);
}

.maxbox li:nth-child(1) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: translateZ(50px);
}

.maxbox li:nth-child(2) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: rotateX(180deg) translateZ(50px);
}

.maxbox li:nth-child(3) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: rotateX(-90deg) translateZ(50px);
}

.maxbox li:nth-child(4) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: rotateX(90deg) translateZ(50px);
}

.maxbox li:nth-child(5) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: rotateY(-90deg) translateZ(50px);
}

.maxbox li:nth-child(6) {
  background: url(../assets/img/bb.gif) no-repeat 0 0;
  -webkit-transform: rotateY(90deg) translateZ(50px);
}

.maxbox {
  width: 200px;
  height: 200px;
  position: absolute;
  left: 0;
  top: 0;
  -webkit-transform-style: preserve-3d;
}

.maxbox li {
  width: 200px;
  height: 200px;
  background: #fff;
  border: 1px solid #ccc;
  position: absolute;
  left: 0;
  top: 0;
  opacity: 0.2;
  /* -webkit-transition: all 1s ease; */
}

.maxbox li:nth-child(1) {
  -webkit-transform: translateZ(100px);
}

.maxbox li:nth-child(2) {
  -webkit-transform: rotateX(180deg) translateZ(100px);
}

.maxbox li:nth-child(3) {
  -webkit-transform: rotateX(-90deg) translateZ(100px);
}

.maxbox li:nth-child(4) {
  -webkit-transform: rotateX(90deg) translateZ(100px);
}

.maxbox li:nth-child(5) {
  -webkit-transform: rotateY(-90deg) translateZ(100px);
}

.maxbox li:nth-child(6) {
  -webkit-transform: rotateY(90deg) translateZ(100px);
}

.box:hover ol li:nth-child(1) {
  -webkit-transform: translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.6;
  left: -100px;
  top: -100px;
}

.box:hover ol li:nth-child(2) {
  -webkit-transform: rotateX(180deg) translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.8;
  left: -100px;
  top: -100px;
}

.box:hover ol li:nth-child(3) {
  -webkit-transform: rotateX(-90deg) translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.8;
  left: -100px;
  top: -100px;
}

.box:hover ol li:nth-child(4) {
  -webkit-transform: rotateX(90deg) translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.8;
  left: -100px;
  top: -100px;
}

.box:hover ol li:nth-child(5) {
  -webkit-transform: rotateY(-90deg) translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.8;
  left: -100px;
  top: -100px;
}

.box:hover ol li:nth-child(6) {
  -webkit-transform: rotateY(90deg) translateZ(300px);
  width: 400px;
  height: 400px;
  opacity: 0.8;
  left: -100px;
  top: -100px;
}

@keyframes move {
  0% {
    -webkit-transform: rotateX(13deg) rotateY(0deg);
  }

  100% {
    -webkit-transform: rotateX(13deg) rotateY(360deg);
  }
}

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
  height: 310px;
  background-color: rgba(252, 252, 252, 0.5);
}

.login-s {
  width: 360px;
  height: 220px;
  margin: 0px 30px;
  padding-top: 50px;
  color: #9fa1a9;
  align-items: center;
}

a:visited {
  color: rgb(31, 126, 215);
}

a:hover {
  color: rgb(40, 67, 190);
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

.lm {
  font-size: smaller;
  padding-top: 1.1px;
  text-align: right;
  height: 20px;
  width: 25%;
}

.left {
  text-align: left;
  width: 100%;
}

.rm {
  font-size: smaller;
  padding-top: 1.1px;
  text-align: right;
  height: 20px;
  width: 100%;
}
.right {
  text-align: left;
  width: 17%;
}

.img-verify {

cursor: pointer;

}
</style>
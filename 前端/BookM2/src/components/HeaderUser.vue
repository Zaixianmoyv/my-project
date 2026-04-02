<template>
  <el-menu
    text-color="#fff"
    active-text-color="#ffd04b">
    <el-header>
    <div class="l-content">
    <el-menu-item>
        <img class="title" src="../assets/img/title.jpg" alt="logo"/>
      <!-- <h3 class="title">Book</h3> -->
    </el-menu-item>
    </div>
      <el-menu-item v-for="item in list" :index="item.path" :key="item.path" @click="clickMenu(item)">
        <component class="icons" :is="item.icon"></component>
       <span>{{ item.label }}</span>
      </el-menu-item>
      <div class="r-content">
    <el-dropdown >
    <span class="el-dropdown-link" >
    <img class="user" :src="imageUrl" alt="" />
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item v-for="item in list1" :index="item.path" :key="item.path" @click="click1Menu(item)">
          <component class="icons" :is="item.icon"></component>
          <span>{{ item.label }}</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</div>
    </el-header>
  </el-menu>
</template>

<script setup>
import { useRouter, useRoute } from 'vue-router'
import { ref, onMounted  } from 'vue'
import { ElMessage, ElMessageBox } from "element-plus";
import axios  from 'axios'
const router = useRouter();

const imageUrl = ref("");
const sid = ref(sessionStorage.getItem("sid"));
const form = ref({
    id: "",
    image: '',
  });
let params = { ...form.value };
      const list = [
                {
                    path: "/mainuser/homeuser",
                    name: "homeuser",
                    label: "首页",
                    icon: "HomeFilled",
                    url: "/HomeUser"

                },
                {
                    path: "/mainuser/bookwuser",
                    name: "bookuser",
                    label: "图书搜索栏",
                    icon: "Search",
                    url: "/BookUser"

                },
                {
                    path: "/mainuser/messageuser",
                    name: "messageuser",
                    label: "公告栏",
                    icon: "BellFilled",
                    url: "/MessageUser"

                },
      ];
      const list1 = [
                {
                    path: "/mainuser/user",
                    name: "user",
                    label: "个人中心",
                    icon: "User",
                    url: "/User"
                },
                {
                    path: "/login",
                    name: "login",
                    label: "退出",
                    icon: "Place",
                    url: "/login"
                },

      ];
      const clickMenu = (item) => {
        router.push({
          name: item.name,
        });
      };

    const click1Menu = (item) => {
      if(item.path == "/login") {
        sessionStorage.clear();
        axios.post("http://localhost:8086/book/student/loginout")
            .then((reqs) => {
            if (reqs.data.code == 6) {
              ElMessage({
                showClose: true,
                message: reqs.data.msg,
                type: "success",
              });
            }
          })
        }
        router.push({
          name: item.name,
        });
      };

//数据初始化
onMounted(() => {
    initDish();
});

 //根据id查询管理员信息
const initDish = () => {
  axios
    .post("http://localhost:8086/book/student/query/" + sid.value)
    .then((reqs) => {
      console.log(reqs);
      form.value = reqs.data.data;
      imageUrl.value = reqs.data.data.image;
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
    justify-content:space-around;
    align-items: center;
    width:100%;
    padding: 0px 20%;
    border: none;
}

.el-menu {
  background-color: #545c60;
}
.icons {
    width: 16px;
    height: 16px;
    padding-right: 5px;
}

.el-dropdown-link {
  border: none;
  resize: none;
}

.user {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    cursor: pointer;
    resize: none;
}

.title {
  /* color: rgb(247, 247, 247);
  font-family:'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif; */
  /* font-size: larger; */
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
}


</style>

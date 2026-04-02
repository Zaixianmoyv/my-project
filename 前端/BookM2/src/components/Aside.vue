<template>
    <el-col>
      <el-menu>
        <el-menu-item v-for="item in list" :index="item.path" :key="item.path" @click="clickMenu(item)">
        <component class="icons" :is="item.icon"></component>
        <span>{{ item.label }}</span>
        </el-menu-item>
      </el-menu>
    </el-col> 
</template>

<script>
import { useRouter } from 'vue-router'

export default {
    setup() {
      const router = useRouter();
      const list = [
              {
                    path: "/main/manager",
                    name: "manager",
                    label: "管理员",
                    icon: "House",
                    url: "/Manager"

              },
              {
                    path: "/main/reader",
                    name: "reader",
                    label: "读者管理",
                    icon: "User",
                    url: "/Reader"

                },
                {
                    path: "/main/borrow",
                    name: "borrow",
                    label: "借阅管理",
                    icon: "EditPen",
                    url: "/Borrow"

                },
              {
                    path: "/main/book",
                    name: "book",
                    label: "图书管理",
                    icon: "Reading",
                    url: "/Book"

                },
                {
                    path: "/main/kind",
                    name: "kind",
                    label: "图书类型管理",
                    icon: "Guide",
                    url: "/Kind"

                },
                {
                    path: "/main/message",
                    name: "message",
                    label: "公告管理",
                    icon: "ChatDotRound",
                    url: "/Message"
                },
      ];
      const clickMenu = (item) => {
        router.push({
          name: item.name,
        });
      };
      return {
        list,
        clickMenu,
        };
    }
}
</script>

<style scoped>
.el-menu {
    background-color: #d0d2d5;
}
.icons {
    width: 16px;
    height: 16px;
    padding-right: 5px;
}

</style>
<template>
  <div class="affix-container">
    <el-affix target=".affix-container" :offset="80">
      <el-button type="info" @click="add('add')" round plain
        >+发布公告</el-button
      >
    </el-affix>
    <div class="loader">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column align="center" label="发布者" prop="mname" />
        <el-table-column align="center" label="已发公告" prop="content" />
        <el-table-column
          align="center"
          label="创建时间"
          prop="createtime"
          :formatter="dateFormat"
        />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
              round
              >撤回</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <el-row>
        <el-col>
          <el-pagination
            background
            layout="prev, pager, next"
            :page-size="limit"
            :total="totalCount"
            :current-page="currentPage"
            @current-change="pageChange"
          />
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
    align-center
  >
    <el-form ref="ruleFormRef" label-width="90px" :model="form" :rules="rules">
      <el-form-item label="公告:" required prop="content">
        <el-input type="textarea" v-model="form.content" autosize />
      </el-form-item>
      <el-form-item>
        <el-button
          style="background-color: #2fb2de; color: white"
          @click="submitForm(ruleFormRef)"
          round
        >
          保存
        </el-button>
        <el-button @click="dialogFormVisible = false" round>取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<script setup>
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { useRouter, useRoute } from "vue-router";
import moment from "moment";
import axios from "axios";
const route = useRoute();

const mid = ref(sessionStorage.getItem("userid"));
//时间格式
const dateFormat = (row, column) => {
  //时间格式化
  var date = row[column.property];
  if (date == undefined) {
    return "";
  }
  return moment(date).format("YYYY-MM-DD HH:mm:ss");
};
// 当前页
let currentPage = ref(1);
// 总条数
let totalCount = ref(10);
// 每页显示限制
let limit = ref(5);
const pageChange = (p) => {
  currentPage.value = p;
  console.log(p);
  // 发起接口请求数据 , 请求参数中使用 currentPage.value 作为查询当前页码
  axios
    .post(
      "http://localhost:8086/book/message/findall/" +
        currentPage.value +
        "/" +
        limit.value
    )
    .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum);
      tableData.value = reqs.data.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

const ruleFormRef = ref(null);
const form = ref({
  content: "",
  manegerid: "",
});

//表单验证
const rules = ref({
  content: [{ required: true, message: "不能为空", trigger: "blur" }],
});

const dialogFormVisible = ref(false);
const type = ref();
const title = ref("");

const add = (types) => {
  dialogFormVisible.value = true;
  console.log(type);
  if (types == "add") {
    title.value = "添加公告";
    type.value = types;
  }
};

const submitForm = () => {
  if (!ruleFormRef.value) return;
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      let params = { ...form.value };
      params.manegerid = mid.value;
      axios
        .post("http://localhost:8086/book/message/addMessage", params)
        .then((reqs) => {
          if (reqs.data.code === 1003) {
            dialogFormVisible.value = false;
            ElMessage({
              showClose: true,
              message: reqs.data.msg,
              type: "success",
            });
            return;
          }
          ElMessage({
            showClose: true,
            message: reqs.data.msg,
            type: "error",
          });
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          search();
        });
    }
  });
};

//搜索(刷新)
const search = () => {
  axios
    .post(
      "http://localhost:8086/book/message/findall/" +
        currentPage.value +
        "/" +
        limit.value
    )
    .then((reqs) => {
      console.log(reqs);
      totalCount.value = parseInt(reqs.data.totalNum);
      tableData.value = reqs.data.data;
    })
    .catch((error) => {
      console.log(error);
    });
};

const handleDelete = (index, row) => {
  ElMessageBox.confirm("确定删除此公告吗?", "警告", {
    cancelButtonText: "取消",
    confirmButtonText: "确定",
    type: "warning",
  })
    .then(() => {
      console.log(index + "    " + row.id);
      tableData.value.splice(index, 1);
      axios
        .post("http://localhost:8086/book/message/deleteMessage/" + row.id)
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
const tableData = ref([]);
//异步发送请求到后端
axios
  .post(
    "http://localhost:8086/book/message/findall/" +
      currentPage.value +
      "/" +
      limit.value
  )
  .then((reqs) => {
    console.log(reqs);
    totalCount.value = parseInt(reqs.data.totalNum);
    tableData.value = reqs.data.data;
  })
  .catch((error) => {
    console.log(error);
  });
</script>

<style scoped>
.loader {
  padding: 20px 100px 0px;
  height: 100%;
  background: var(--el-color-info-light-9);
  overflow: hidden;
}

.affix-container {
  text-align: center;
  height: 100%;
  border-radius: 4px;
  background: var(--el-color-info-light-9);
}
</style>
<template>
  <div class="mcontaner">
    <Header></Header>
    <div class="block">
      <el-timeline>
        <el-timeline-item :timestamp="blog.created" placement="top" v-for="(blog,key) in blogs" :key=key>
          <el-card>

            <h4>
              <router-link :to="{  name: 'BlogDetail', params: {blogId: blog.id}}">{{blog.title}}</router-link>
            </h4>

            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>

      </el-timeline>

      <el-pagination class="mage" background layout="prev, pager, next" :current-page="currentPage"
        :page-size="pageSize" :total="total" @current-change=page>
      </el-pagination>
    </div>
  </div>
</template>

<script>
//引入header组件
import Header from "../components/Header";

export default {
  data () {
    return {
      blogs: {},
      currentPage: 1,
      total: 0,
      pageSize: 5
    }
  },
  name: "Blogs",
  components: { Header },

  methods: {
    page (currentPage) {
      const _this = this
      _this.$axios.get("/blogs?currentPage=" + currentPage).then(res => {
        var data = res.data.data
        _this.blogs = data.records
        _this.currentPage = data.current
        _this.pageSize = data.size
        _this.total = data.total

      })
    }
  },
  created () {
    this.page(1)
  }
}
</script>

<style scoped>
.block {
  margin: 20px;
}

.mage {
  margin: 0 auto;
}
</style>

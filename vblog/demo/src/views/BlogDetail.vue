<template>
  <div>
    <Header></Header>
    <div class="blog">
      <h2>{{blog.title}}</h2>
      <el-link v-if="ownblog" class="linklist">
        <router-link :to="{name:'BlogEdit',params:{blogid:blog.id}}">
          <el-button type="primary" icon="el-icon-edit">编辑</el-button>
        </router-link>
      </el-link>
      <el-link  v-if="ownblog" class="linklist">
        <el-button type="danger" icon="el-icon-delete" round @click="delblog">删除</el-button>
      </el-link>
      <el-divider></el-divider>
      <div class="markdown-body" v-html="blog.content"></div>
    </div>
  </div>
</template>

<script>
import 'github-markdown-css'
import Header from "../components/Header";
export default {
  name: "BlogDetail",
  components: { Header },
  data () {
    return {
      blog: {
        id: '',
        title: '',
        content: '',
        description: ''

      },
      ownblog: false
    }
  },
  methods: {
    delblog () {
      const blogId = this.$route.params.blogId
      const _this = this
      if (blogId) {
        this.$confirm('此操作将永久删除该文章, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          _this.$axios.post(`/blogdel/${blogId}`, null, {
            headers: {
              "Authorization": localStorage.getItem("token")
            }
          }).then(res => {
            this.$message({
              type: 'success',
              message: res.data.data
            });
            _this.$router.push("/blogs")
          })

        }).catch(() => {

          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });


      }
    }
  },
  created () {
    //获取动态路由的 blogId
    const blogId = this.$route.params.blogId
    const _this = this
    if (blogId) {
      this.$axios.get("/blog/" + blogId).then(res => {
        const blog = res.data.data
        _this.blog.id = blog.id
        _this.blog.title = blog.title
        _this.blog.description = blog.description

        //MardownIt 渲染
        var MardownIt = require("markdown-it")
        var md = new MardownIt();
        var result = md.render(blog.content)
        _this.blog.content = result

        //查看是否是登录人 是则可以编辑和删除
        _this.ownblog = (blog.userId === _this.$store.getters.getUser.id)
      })
    }

  }
}
</script>

<style scoped>
.blog {
  margin-top: 10px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  padding: 10px;
}
.linklist {
  margin: 5px;
}
</style>

// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
//基于Vue的markdown编辑器mavon-editor
import mavonEditor from 'mavon-editor'
//路由
import router from './router'
//存储
import store from './store'
// 引入element-ui依赖
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import 'mavon-editor/dist/css/index.css'
// 引入axios依赖
import axios from 'axios'
import "./axios.js"
import "./permission.js"


//引用全局
Vue.prototype.$axios = axios 
Vue.use(Element)
Vue.config.productionTip = false
Vue.use(mavonEditor)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  //定义全局参数 其他页面可以直接获取state里面的内容
  state: {
    token: '', //方法一 localStorage.getItem("token") 
    //反序列化获取session会话中的 userInfo对象
    userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    //相当于实体类的set
    SET_TOKEN:(state,token)=>{
      state.token=token//将传入的token赋值 给state的token
      //同时可以存入浏览器的localStorage里面
      localStorage.setItem("token",token)
    },
    SET_USERINFO:(state,userInfo)=>{
      state.userInfo=userInfo//将传入的tuserInfo赋值 给state的userInfo
      //同时可以存入会话的sessionStorage里面 sessionStorage中只能存字符串 不能存入对象所以我们存入序列化 jons串
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
    },
    //删除token及userInfo
    REMOVE_INFO:(state)=>{
      state.token = '';
      state.userInfo = {};
      localStorage.setItem("token",'')
      sessionStorage.setItem("userInfo",JSON.stringify(''))
    }
  },
  getters: {
    //相当于get
    //配置一个getUser可以直接获取已经反序列化对象的一个userInfo
   getUser: state=>{
     return state.userInfo;
   },getToken: state=>{
    return state.token;
  }
  },
  actions: {
    
  },
  modules: {
    
  }
})

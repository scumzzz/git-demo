// 引入axios依赖
import axios from 'axios'
// 引入element-ui依赖
import Element from 'element-ui'
import store from './store';
// 引入路由
import router from './router'

var s=window.location.toString();   
		    var s1=s.substr(7,s.length);
		    var s2=s1.indexOf("/");
		    s=s.substr(0,8+s2);
	var a = "http://localhost:8081/";//获取连接前缀相当于 http://localhost:8081/

  //配置默认前缀
axios.defaults.baseURL= a

//配置前置拦截
axios.interceptors.request.use(config => { 
  return config
})

//配置后置拦截
axios.interceptors.response.use(response=>{
    let res = response.data; 
    if(res.code == 200){
      return response
    }else{ 
        Element.Message.error("操作错了哦",{duration : 2*1000}) 
        //返回一个异常提示就不会继续往下走了 不+的话 res=>的里面 还是会继续走的
        return Promise.reject(response.data.msg)
    }
     // 捕获并处理后台异常信息
  },error=>{
     // 使得异常信息更加友好
    console.log(error) 
    if (error.response.data) { //data不为空时
      error.message = error.response.data.msg
      console.log("-------------------------")
      console.log(error.message)
      console.log("-------------------------")
  }
    if(error.response.status == 401){
      store.commit('REMOVE_INFO')//清空token userinfo
      router.push("/login")  //跳转登录页面
    } 
    Element.Message.error(error.message)
    
    return Promise.reject(error)
  }

)

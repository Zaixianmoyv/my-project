const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8086/book/',    //需要跨域的url
        ws: true,                                    //代理webSocket
        changeOrigin: true,                        //允许跨域
        pathRewrite: {
          '/api': ''                         //重写路径
        } 
      }
    }
  }
})

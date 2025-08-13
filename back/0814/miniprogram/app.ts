// app.ts
App<IAppOption>({
    globalData: {
        token: '',
        baseUrl: 'http://172.0.0.1:8080'
    },
    onLaunch() {
        // 展示本地存储能力
        const logs = wx.getStorageSync('logs') || []
        logs.unshift(Date.now())
        wx.setStorageSync('logs', logs)

        // 登录
        wx.login({
            success: res => {
                console.log("用户临时code： " + res.code)
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                wx.request({
                    url: getApp().globalData.baseUrl + '/api/wechat/login?code=' + res.code,
                    method: 'POST',
                    success: (loginRes) => {
                        console.log('获取到token:', loginRes.data)
                        // 将token存储到全局数据中
                        // if (loginRes.data && typeof loginRes.data === 'string') {
                        //   this.globalData.token = loginRes.data
                        //   // 打印token到控制台
                        //   console.log('Token已存储到cookie:', loginRes.data)
                        // } else {
                        //   console.warn('获取到的token不是字符串类型:', loginRes.data)
                        // }
                    },
                    fail: (err) => {
                        console.error('获取token失败:', err)
                    }
                })
            },
        })
    },
})
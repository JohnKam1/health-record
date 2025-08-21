// index.ts
// 获取应用实例
const app = getApp<IAppOption>()
const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'

// 引入封装的请求函数
import { request } from '../../utils/request'

Component({
  data: {
    motto: 'Hello World',
    userInfo: {
      avatarUrl: defaultAvatarUrl,
      nickName: '',
    },
    hasUserInfo: false,
    canIUseGetUserProfile: wx.canIUse('getUserProfile'),
    canIUseNicknameComp: wx.canIUse('input.type.nickname'),
  },
  methods: {
    // 事件处理函数
    bindViewTap() {
      wx.navigateTo({
        url: '../logs/logs',
      })
    },
    onChooseAvatar(e: any) {
      const { avatarUrl } = e.detail
      const { nickName } = this.data.userInfo
      this.setData({
        "userInfo.avatarUrl": avatarUrl,
        hasUserInfo: nickName && avatarUrl && avatarUrl !== defaultAvatarUrl,
      })
    },
    onInputChange(e: any) {
      const nickName = e.detail.value
      const { avatarUrl } = this.data.userInfo
      this.setData({
        "userInfo.nickName": nickName,
        hasUserInfo: nickName && avatarUrl && avatarUrl !== defaultAvatarUrl,
      })
    },
    getUserProfile() {
      // 推荐使用wx.getUserProfile获取用户信息，开发者每次通过该接口获取用户个人信息均需用户确认，开发者妥善保管用户快速填写的头像昵称，避免重复弹窗
      wx.getUserProfile({
        desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
        success: (res) => {
          console.log(res)
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    },
    // 添加进入系统按钮的点击处理函数
    enterSystem() {
      // 当用户点击进入系统按钮时，向后端发送头像和昵称
      const { avatarUrl, nickName } = this.data.userInfo
      
      // 发起异步请求将头像和昵称发送到后端
      request({
        url: app.globalData.baseUrl + '/api/user/avatar-nickname',
        method: 'POST',
        data: {
          avatar: avatarUrl,
          nickname: nickName
        },
        success: (res) => {
          console.log('头像和昵称已发送到后端:', res)
          // 跳转到home页面
          wx.navigateTo({
            url: '../home/home',
          })
        },
        fail: (err) => {
          console.error('发送头像和昵称失败:', err)
          // 即使发送失败，也跳转到home页面
          wx.navigateTo({
            url: '../home/home',
          })
        }
      })
    },
  },
})
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
    // 上传头像到后端服务器
    uploadAvatar(avatarUrl: string): Promise<string> {
      return new Promise((resolve, reject) => {
        // 如果是临时文件路径（以tmp开头），需要上传到服务器
        if (avatarUrl.startsWith('http://tmp') || avatarUrl.startsWith('https://tmp')) {
          // 上传文件到后端
          wx.uploadFile({
            url: app.globalData.baseUrl + '/api/file/upload',
            filePath: avatarUrl,
            name: 'file',
            header: {
              'Authorization': app.globalData.token
            },
            success: (res) => {
              if (res.statusCode === 200) {
                const data = JSON.parse(res.data);
                if (data.code === 200) {
                  // 返回上传后的文件访问URL
                  resolve(data.data);
                } else {
                  reject(new Error('上传失败: ' + data.message));
                }
              } else {
                reject(new Error('上传失败，状态码: ' + res.statusCode));
              }
            },
            fail: (err) => {
              reject(err);
            }
          });
        } else {
          // 如果是网络图片（包括已上传的图片），直接返回
          resolve(avatarUrl);
        }
      });
    },
    // 添加进入系统按钮的点击处理函数
    enterSystem() {
      // 当用户点击进入系统按钮时，先上传头像再发送头像和昵称到后端
      const { avatarUrl, nickName } = this.data.userInfo;
      // 获取应用实例
      const app = getApp<IAppOption>();
      
      // 上传头像并发送用户信息到后端
      this.uploadAvatar(avatarUrl)
        .then((uploadedAvatarUrl) => {
          // 发起异步请求将头像和昵称发送到后端
          request({
            url: app.globalData.baseUrl + '/api/user/avatar-nickname',
            method: 'POST',
            data: {
              avatar: uploadedAvatarUrl,
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
          }, app)
        })
        .catch((err) => {
          console.error('上传头像失败:', err)
          // 即使上传失败，也跳转到home页面
          wx.navigateTo({
            url: '../home/home',
          })
        });
    },
  },
})
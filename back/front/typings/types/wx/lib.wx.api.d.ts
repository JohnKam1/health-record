// typings/types/wx/lib.wx.api.d.ts

interface Wx {
  // 添加 uploadFile 的 Promise 版本定义
  uploadFile(option: WechatMiniprogram.UploadFileOption): WechatMiniprogram.UploadTask;
}
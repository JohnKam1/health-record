package com.Hootsybit.pojo.qo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户头像和昵称请求对象")
public class AvatarNicknameQo {
    
    @Schema(description = "用户头像URL")
    private String avatar;
    
    @Schema(description = "用户昵称")
    private String nickname;
}
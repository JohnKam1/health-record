package com.Hootsybit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.Hootsybit.pojo.entity.FileInfo;
import com.Hootsybit.mapper.FileInfoMapper;
import com.Hootsybit.service.FileInfoService;
import org.springframework.stereotype.Service;

@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {
}
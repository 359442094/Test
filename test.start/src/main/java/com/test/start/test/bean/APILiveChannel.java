package com.test.start.test.bean;

import com.test.common.videoApi.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 直播频道实体
 * @author CJ
 * @date 2019/12/3
 */
@ToString
@Getter
@Setter
public class APILiveChannel extends BaseResponse implements Serializable {

    private static final long serialVersionUID = -8859081210262175081L;

    @ApiModelProperty(value = "直播频道ID",notes = "直播频道ID")
    private String channelId;
    @ApiModelProperty(value = "直播用户ID",notes = "直播用户ID")
    private String userId;
    @ApiModelProperty(value = "直播频道名称",notes = "直播频道名称")
    private String name;
    @ApiModelProperty(value = "直播频道描述",notes = "直播频道描述")
    private String description;
    @ApiModelProperty(value = "直播推流地址",notes = "直播推流地址")
    private String url;
    @ApiModelProperty(value = "直播流名称",notes = "直播流名称")
    private String stream;
    @ApiModelProperty(value = "播放器logo",notes = "播放器logo")
    private String logoImage;
    @ApiModelProperty(value = "Logo不透明度，1表示完全不透明",notes = "Logo不透明度，1表示完全不透明")
    private String logoOpacity;
    @ApiModelProperty(value = "Logo位置",notes = "Logo位置")
    private String logoPosition;
    @ApiModelProperty(value = "Logo的跳转链接",notes = "Logo的跳转链接")
    private String logoHref;
    @ApiModelProperty(value = "播放前显示的封面图",notes = "播放前显示的封面图")
    private String coverImage;
    @ApiModelProperty(value = "封面图的跳转链接",notes = "封面图的跳转链接")
    private String coverHref;
    @ApiModelProperty(value = "等待推流时的显示图片",notes = "等待推流时的显示图片")
    private String waitImage;
    @ApiModelProperty(value = "等待推流时显示图片的跳转链接",notes = "等待推流时显示图片的跳转链接")
    private String waitHref;
    @ApiModelProperty(value = "切断流时的显示图片",notes = "切断流时的显示图片")
    private String cutoffImage;
    @ApiModelProperty(value = "切断流时显示图片的跳转链接",notes = "切断流时显示图片的跳转链接")
    private String cutoffHref;
    @ApiModelProperty(value = "广告类型",notes = "广告类型")
    private String advertType;
    @ApiModelProperty(value = "广告时长",notes = "广告时长")
    private String advertDuration;
    @ApiModelProperty(value = "广告区域宽度",notes = "广告区域宽度")
    private String advertWidth;
    @ApiModelProperty(value = "广告区域高度",notes = "广告区域高度")
    private String advertHeight;
    @ApiModelProperty(value = "图片广告",notes = "图片广告")
    private String advertImage;
    @ApiModelProperty(value = "广告的跳转链接",notes = "广告的跳转链接")
    private String advertHref;
    @ApiModelProperty(value = "视频广告ID",notes = "视频广告ID")
    private String advertFlvVid;
    @ApiModelProperty(value = "视频广告链接",notes = "视频广告链接")
    private String advertFlvUrl;
    @ApiModelProperty(value = "播放器控制栏颜色",notes = "播放器控制栏颜色")
    private String playerColor;
    @ApiModelProperty(value = "自动播放",notes = "自动播放")
    private String autoPlay;
    @ApiModelProperty(value = "一开始的暖场视频",notes = "一开始的暖场视频")
    private String warmUpFlv;
    @ApiModelProperty(value = "观看密码限制",notes = "需要输入观看密码才能播放流")
    private String passwdRestrict;
    @ApiModelProperty(value = "观看密码加密后的密文",notes = "观看密码加密后的密文")
    private String passwdEncrypted;
    @ApiModelProperty(value = "仅推音频流",notes = "仅推音频流")
    private String isOnlyAudio;
    @ApiModelProperty(value = "低延迟",notes = "低延迟")
    private String isLowLatency;
    @ApiModelProperty(value = "直播拉流（播放）m3u8地址",notes = "直播拉流（播放）m3u8地址")
    private String m3u8Url;
    @ApiModelProperty(value = "直播拉流（播放）m3u8地址1",notes = "直播拉流（播放）m3u8地址1")
    private String m3u8Url1;
    @ApiModelProperty(value = "直播拉流（播放）m3u8地址2",notes = "直播拉流（播放）m3u8地址2")
    private String m3u8Url2;
    @ApiModelProperty(value = "直播拉流（播放）m3u8地址3",notes = "直播拉流（播放）m3u8地址3")
    private String m3u8Url3;

}

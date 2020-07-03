package com.test.start.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author CJ
 * @date 2020/6/24
 */
@ToString
@Getter
@Setter
public class RecordCallbackRequest implements Serializable {

    private static final long serialVersionUID = 8924178899482436038L;

    /**
     * Cmd	String	类型为字符串，'Record'
     * VUrl	String	视频文件的链接地址
     * VST	Int64	视频开始时间
     * VET	Int64	视频结束时间
     * Duration	Int64	视频时长
     * FileId	String	文件Id
     * Size	Int64	文件大小
     */

    private int CourseID;

    private int ClassID;

    private int ActionTime;

    private int SID;

    private int TimeStamp;

    private int VST;

    private int VET;

    private String Cmd;

    private String VUrl;

    private int Duration;

    private String FileId;

    private int Size;

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int courseID) {
        CourseID = courseID;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public int getActionTime() {
        return ActionTime;
    }

    public void setActionTime(int actionTime) {
        ActionTime = actionTime;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public int getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        TimeStamp = timeStamp;
    }

    public int getVST() {
        return VST;
    }

    public void setVST(int VST) {
        this.VST = VST;
    }

    public int getVET() {
        return VET;
    }

    public void setVET(int VET) {
        this.VET = VET;
    }

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String cmd) {
        Cmd = cmd;
    }

    public String getVUrl() {
        return VUrl;
    }

    public void setVUrl(String VUrl) {
        this.VUrl = VUrl;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

}

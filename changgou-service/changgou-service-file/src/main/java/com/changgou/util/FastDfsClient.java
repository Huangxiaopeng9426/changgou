package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.*;


public class FastDfsClient {
    static {
        //获取tracker的配置文件fdfs_client.conf的位置
        String path = new ClassPathResource("fdfs_client.conf").getPath();
        try {
            //加载配置文件
            ClientGlobal.init(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //上传文件
    public static String[] upload(FastDFSFile file){
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //参数1 字节数组
            //参数2 扩展名(不带点)
            //参数3 元数据( 文件的大小,文件的作者,文件的创建时间戳)
            NameValuePair[] mata_list = new NameValuePair[]{new NameValuePair(file.getAuthor()),new NameValuePair(file.getName())};
            StorageClient storageClient = new StorageClient(trackerServer,null);
            String[] jpgs = storageClient.upload_appender_file(file.getContent(), file.getExt(), mata_list);
            return jpgs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //文件下载
    public static InputStream download(String groupName,String remoteFileName){
        ByteArrayInputStream byteArrayInputStream= null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);
            byteArrayInputStream = new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //删除文件
    public static void deleteFile(String groupName,String remoteFileName){
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            int group1 = storageClient.delete_file(groupName, remoteFileName);
            if (group1==0) {
                System.out.println("删除成功");
            }else{
                System.out.println("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//根据组名获取组的信息
    public static StorageServer getGroupInfo(String groupName){
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            //参数1 指定trackerClient 对象
            //参数2 指定组名
            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer, groupName);
            return storeStorage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //根据文件名和组名 获取组信息的数组信息
    public static ServerInfo[] getServerInfo(String groupName,String remoteFileName){
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            ServerInfo[] group1s = trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
            return group1s;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    //获取Tracker服务地址
    public static String getTrackerUrl(){
        try {
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            String hostString = trackerServer.getInetSocketAddress().getHostString();
            int g_tracker_http_port = ClientGlobal.getG_tracker_http_port();
            return "http://"+hostString+":"+g_tracker_http_port;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

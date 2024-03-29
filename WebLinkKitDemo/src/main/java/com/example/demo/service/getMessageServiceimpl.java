package com.example.demo.service;

import com.aliyun.openservices.iot.api.Profile;
import com.aliyun.openservices.iot.api.message.MessageClientFactory;
import com.aliyun.openservices.iot.api.message.api.MessageClient;
import com.aliyun.openservices.iot.api.message.callback.ConnectionCallback;
import com.aliyun.openservices.iot.api.message.callback.MessageCallback;
import com.aliyun.openservices.iot.api.message.entity.Message;
import com.aliyun.openservices.iot.api.message.entity.MessageToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class getMessageServiceimpl implements  getMessageService {

    private MessageClient messageClient;

    @Autowired
    public void initmessageClient()
    {

        MessageCallback messageCallback = new MessageCallback() {
            @Override
            public Action consume(MessageToken messageToken) {
                Message m = messageToken.getMessage();
                System.out.println((Arrays.toString(messageToken.getMessage().getPayload())));
                return MessageCallback.Action.CommitSuccess;
            }
        };
        String accessKey = "LTAI4FnMKaHrkx4mhaCW4Qi4";
        String accessSecret = "cO3tIVl8H6pF2jGHaTNWDoMulqh9e4";
        String regionId = "cn-shanghai";
        String uid = "1591662154888233";
        String endPoint = "https://" + uid + ".iot-as-http2." + regionId + ".aliyuncs.com";
        Profile profile = Profile.getAccessKeyProfile(endPoint, regionId, accessKey, accessSecret);
        messageClient = MessageClientFactory.messageClient(profile);
        messageClient.connect(messageToken -> {
            Message m = messageToken.getMessage();
            System.out.println("receive message from " + m);
            return MessageCallback.Action.CommitSuccess;
        });
        messageClient.setConnectionCallback(new ConnectionCallback() {
            @Override
            public void onConnectionLost() {
                messageClient.connect(messageToken -> {
                    Message m = messageToken.getMessage();
                    System.out.println("receive message from " + m);
                    return MessageCallback.Action.CommitSuccess;
                });
            }

            @Override
            public void onConnected(boolean isReconnected) {
                System.out.println("连接成功，是否为重连: " + isReconnected);

            }
        });
        messageClient.setMessageListener("/a1UJLmxdlhK/#",messageCallback);
    }


    @Override
    public int getStatus() {
        return 0;
    }
}

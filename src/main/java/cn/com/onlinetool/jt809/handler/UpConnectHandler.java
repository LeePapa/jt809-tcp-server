package cn.com.onlinetool.jt809.handler;

import cn.com.onlinetool.jt809.bean.BasePacket;
import cn.com.onlinetool.jt809.bean.UpConnectReq;
import cn.com.onlinetool.jt809.manage.TcpChannelMsgManage;
import cn.com.onlinetool.jt809.util.ByteArrayUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author choice
 * 主链路登陆逻辑处理
 * @date 2019-06-28 14:35
 *
 */
@Slf4j
@Component
public class UpConnectHandler implements CommonHandler {
//    @Autowired
//    KafkaTemplate<String,Object> kafkaTemplate;
//    @Autowired
//    TcpChannelMsgManage tcpChannelMsgManage;

    @Override
    public void handler(ChannelHandlerContext ctx, Object msg) {
        BasePacket basePacket = (BasePacket) msg;
        int userId = ByteArrayUtil.bytes2int(ByteArrayUtil.subBytes(basePacket.getMsgBody(),0,4));
        String password = ByteArrayUtil.bytes2string(ByteArrayUtil.subBytes(basePacket.getMsgBody(),4,8));
        String downLinkIp = ByteArrayUtil.bytes2string(ByteArrayUtil.subBytes(basePacket.getMsgBody(),12,32));
        String downLinkPort = ByteArrayUtil.bytes2string(ByteArrayUtil.subBytes(basePacket.getMsgBody(),44,2));

        UpConnectReq req = new UpConnectReq();
        req.setUserId(userId);
        req.setPassword(password);
        req.setDownLinkIp(downLinkIp);
        req.setDownLinkPort(downLinkPort);
        loginHandler(req);

    }

    /**
     * 登陆逻辑处理
     * @param msg
     */
    private void loginHandler(UpConnectReq msg){
        log.info("登陆请求信息：" + JSONObject.toJSONString(msg));

    }
}
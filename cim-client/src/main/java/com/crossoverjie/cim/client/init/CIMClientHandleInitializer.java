package com.crossoverjie.cim.client.init;

import com.crossoverjie.cim.client.handle.CIMClientHandle;
import com.crossoverjie.cim.common.protocol.CIMResponseProto;
import com.crossoverjie.cim.common.ssl.SecureChatSslContextFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

import javax.net.ssl.SSLEngine;

/**
 * Function:
 *
 * @author xuliang
 * Date: 23/02/2018 22:47
 * @since JDK 1.8
 */
public class CIMClientHandleInitializer extends ChannelInitializer<Channel> {

    private final CIMClientHandle cimClientHandle = new CIMClientHandle();


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast("ssl", getSslHandler())
                //10 秒没发送消息 将IdleStateHandler 添加到 ChannelPipeline 中
                .addLast(new IdleStateHandler(0, 10, 0))

                //心跳解码
                //.addLast(new HeartbeatEncode())

                // google Protobuf 编解码
                //拆包解码
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(CIMResponseProto.CIMResProtocol.getDefaultInstance()))
                //
                //拆包编码
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
//                .addLast(new NettySocketClientSSLHandler())
                .addLast(cimClientHandle)

        ;
    }

    public SslHandler getSslHandler() {
        SslHandler sslHandlerClient = null;
        String sChatPath = ("/Users/xuliang/Documents/chat/cim/certfile/cChat.jks");
        SSLEngine engine = SecureChatSslContextFactory.getClientContext(sChatPath, sChatPath).createSSLEngine();
        engine.setUseClientMode(true);//设置服务端模式
        sslHandlerClient = new SslHandler(engine);
        return sslHandlerClient;
    }


}

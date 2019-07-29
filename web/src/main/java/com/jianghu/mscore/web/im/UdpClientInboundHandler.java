package com.jianghu.mscore.web.im;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于处理客户端的UDP连接、通信等的ChannelInboundHandler实现类。
 * <p>
 * 本类的行为表现让Netty的UDP达到类似于TCP这样的“连接”效果（跟MINA里
 * 的Session管理类似），从而简化基于Netty UDP的编程难度（Netty最新版框架
 * 对于UDP的支持回归到了UDP的本质，也就是没做过多封装和支持，使得应用层
 * 并没有从Netty框架上获得更多的易用性，而这只能MobileIMSDK框架netty版
 * 自行实现和解决了）。
 * <p>
 * 从某种意义上讲，Netty4中的UDP支持相对于MINA2来说，既是进步（没有做过多
 * 封装，回归到了协议的本质），也是退步（让UDP裸奔，显然易用性上就没那么好
 * 了）。
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.03.02
 */
public class UdpClientInboundHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private static Logger logger = LoggerFactory.getLogger(UdpClientInboundHandler.class);

//    private ServerCoreHandler serverCoreHandler;

    /**
     * The type Udp client inbound handler.
     * Description
     *
     * @author hujiang.
     * @version 1.0
     * @since 2019.03.02
     */
    public UdpClientInboundHandler() {

    }

//    public UdpClientInboundHandler(ServerCoreHandler serverCoreHandler) {
//        this.serverCoreHandler = serverCoreHandler;
//    }

    /**
     * UDP“会话”处理过程中出现异步时Netty会调用本方法。
     * <p>
     * <font color="#ff0000">因UDP是无连接协议，此处的“会话”只是逻辑层的扩展（类似于MINA中的
     * IoSession概念），并非真正的TCP中的“连接”或“会话”。</font>
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        try {
//            serverCoreHandler.exceptionCaught(ctx.channel(), e);
        } catch (Exception e2) {
            logger.warn(e2.getMessage(), e);
        }
    }

    /**
     * 客户端与服务端建立“连接”时Netty会调用本方法。
     * <p>
     * <font color="#ff0000">因UDP是无连接协议，此处的“连接”只是逻辑层的扩展（类似于MINA中的
     * IoSession概念），并非真正的TCP中的“连接”或“会话”。</font>
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        serverCoreHandler.sessionCreated(ctx.channel());
    }

    /**
     * 客户端与服务端断开“连接”时Netty会调用本方法。
     * <p>
     * <b>“断开”的原因有可能多种可能性：</b>
     * <ul>
     * <li>1）客户端因网络原因掉线而导致它的“会话”超时被服务端close掉；</li>
     * <li>2）客户端自行退出了“会话”，比如退出APP等；</li>
     * <li>3）客户端无故退出（比如APP崩溃）而致服务端“会话”超时；</li>
     * <li>4）服务端因某种原因主动关闭了此“会话”等。</li>
     * </ul>
     * <p>
     * <font color="#ff0000">因UDP是无连接协议，此处的“连接”只是逻辑层的扩展（类似于MINA中的
     * IoSession概念），并非真正的TCP中的“连接”或“会话”。</font>
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
//        serverCoreHandler.sessionClosed(ctx.channel());
    }

    /**
     * 客户端向服务端发送数据时Netty会调用本方法。
     * <p>
     * 本方法是"客户端 to 服务端"这个方向的数据通信唯一处理方法。
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf bytebuf) throws Exception {
//        serverCoreHandler.messageReceived(ctx.channel(), bytebuf);
    }
}

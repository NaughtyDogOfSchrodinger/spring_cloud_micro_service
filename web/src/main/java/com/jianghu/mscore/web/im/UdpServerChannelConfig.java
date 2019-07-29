package com.jianghu.mscore.web.im;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.socket.ServerSocketChannelConfig;

import java.net.SocketException;
import java.nio.channels.DatagramChannel;

/**
 * 仿照TCP的“形”实现了一个UDP的服务端Channel配置类。
 * <p>
 * 因为Netty4中，对于ServerBootstrap来说为了灵活应对各种场景，
 * 有很多参数可供设置，所以从设计模式的角度讲，就把这部分配置
 * 独立成了遵从Builder设计模式的封装类，让ServerBootstrap的
 * API显的更优雅。
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.03.02
 */
public class UdpServerChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig {

    private final DatagramChannel datagramChannel;

    /**
     * The type Udp server channel config.
     * Description
     *
     * @author hujiang.
     * @since 2019.03.02
     */
    UdpServerChannelConfig(Channel channel, DatagramChannel datagramChannel) {
        super(channel);
        this.datagramChannel = datagramChannel;

        // 使用Netty官方DefaultDatagramChannelConfig中相同的配置值即可
        setRecvByteBufAllocator(new FixedRecvByteBufAllocator(2048));
    }

    /**
     * backlog参数主要用于TCP场景下，是内核为此套接口
     * 排队的最大连接个数，UDP时给1就行了，因为没意义。
     *
     * @return 默认永远返回1
     */
    @Override
    public int getBacklog() {
        return 1;
    }

    /**
     * backlog参数对于UDP来说无意义，它主要用于TCP场景下，是内核为此套接口
     * 排队的最大连接个数，因而本方法只是个空方法。
     */
    @Override
    public ServerSocketChannelConfig setBacklog(int backlog) {
        return this;
    }

    /**
     * connectTimeoutMillis对于UDP来说无意义，因而本方法只是个空方法。
     */
    @Override
    public ServerSocketChannelConfig setConnectTimeoutMillis(int timeout) {
        return this;
    }

    /**
     * 本方法对于UDP来说无意义，因而本方法只是个空方法。
     */
    @Override
    public ServerSocketChannelConfig setPerformancePreferences(int arg0, int arg1, int arg2) {
        return this;
    }

    @Override
    public ServerSocketChannelConfig setAllocator(ByteBufAllocator alloc) {
        super.setAllocator(alloc);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator alloc) {
        super.setRecvByteBufAllocator(alloc);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setAutoRead(boolean autoread) {
        super.setAutoRead(true);
        return this;
    }

    @Override
    @Deprecated
    public ServerSocketChannelConfig setMaxMessagesPerRead(int n) {
        super.setMaxMessagesPerRead(n);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator est) {
        super.setMessageSizeEstimator(est);
        return this;
    }

    @Override
    public ServerSocketChannelConfig setWriteSpinCount(int spincount) {
        super.setWriteSpinCount(spincount);
        return this;
    }

    public ServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        return (ServerSocketChannelConfig) super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
    }

    public ServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        return (ServerSocketChannelConfig) super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    }

    public ServerSocketChannelConfig setWriteBufferWaterMark(WriteBufferWaterMark writeBufferWaterMark) {
        return (ServerSocketChannelConfig) super.setWriteBufferWaterMark(writeBufferWaterMark);
    }

    @Override
    public int getReceiveBufferSize() {
        try {
            return datagramChannel.socket().getReceiveBufferSize();
        } catch (SocketException ex) {
            throw new ChannelException(ex);
        }
    }

    @Override
    public ServerSocketChannelConfig setReceiveBufferSize(int size) {
        try {
            datagramChannel.socket().setReceiveBufferSize(size);
        } catch (SocketException ex) {
            throw new ChannelException(ex);
        }
        return this;
    }

    @Override
    public boolean isReuseAddress() {
        try {
            return datagramChannel.socket().getReuseAddress();
        } catch (SocketException ex) {
            throw new ChannelException(ex);
        }
    }

    @Override
    public ServerSocketChannelConfig setReuseAddress(boolean reuseaddr) {
        try {
            datagramChannel.socket().setReuseAddress(true);
        } catch (SocketException ex) {
            throw new ChannelException(ex);
        }
        return this;
    }
}

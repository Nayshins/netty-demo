package me.jakenations.nettydemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class PrimeFactorsServer {

    public void start() throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(8080))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new PrimeFactorsHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            System.out.println(PrimeFactorsServer.class.getName() + " started and listen on " + channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}

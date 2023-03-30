package com.xgl.study.socket.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

public class FileSendServer {

	private static Charset charset = Charset.forName("UTF-8");
	private static ByteBuffer buffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) throws IOException {
		int stage = 0;
		String fileName = "";
		FileChannel fileChannel = null;
		int num;

		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.bind(new InetSocketAddress(9000));
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (selector.select() > 0) {
			Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				if (key.isValid()) {
					if (key.isConnectable()) {
						System.out.println("111111111111");
						ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
						serverChannel.register(selector, SelectionKey.OP_ACCEPT);
					}
					if (key.isAcceptable()) {
						System.out.println("22222");
						ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
						SocketChannel socketChannel = serverChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
					}
					if (key.isReadable()) {
						System.out.println("33333");
						SocketChannel socketChannel = (SocketChannel) key.channel();
						buffer.clear();
						while ((num = socketChannel.read(buffer)) != -1) {
							buffer.flip();
							if (stage == 0) {
								fileName = charset.decode(buffer).toString();
								System.out.println(fileName);
								FileOutputStream fis = new FileOutputStream("K" + fileName);
								fileChannel = fis.getChannel();
								stage++;
							} else if ((stage == 1)) {
								System.out.println(buffer.getLong());
								stage++;
							} else {
								System.out.println("开始写入文件");
								fileChannel.write(buffer);
								buffer.clear();
							}
						}
						buffer.clear();
						if(num==-1) {
							key.cancel();
						}
					}
					if (key.isWritable()) {

					}
				}
			}

		}

	}

}

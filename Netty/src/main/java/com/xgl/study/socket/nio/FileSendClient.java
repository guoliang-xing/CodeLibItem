package com.xgl.study.socket.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class FileSendClient {

	private static Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) throws IOException {

		File file = new File("doc//README2.txt");
		FileInputStream fio = new FileInputStream(file);
		System.out.println(file.getName());
		FileChannel fileChannel = fio.getChannel();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("localhost", 9000));
		while (!socketChannel.finishConnect()) {
			System.out.println("服务器连接中.......*********");
		}

		System.out.println("*****服务器连接成功*********");
		System.out.println("发送文件名......");
		ByteBuffer byteBuffer = charset.encode(file.getName());
		socketChannel.write(byteBuffer);
		System.out.println("发送文件长度......");
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.putLong(file.length());
		buffer.flip();
		socketChannel.write(buffer);
		buffer.clear();
		System.out.println("发送文件开始......");

		while (fileChannel.read(buffer) != -1) {
			buffer.flip();
			socketChannel.write(buffer);
			buffer.clear();
		}
		fileChannel.close();
		fio.close();
		socketChannel.shutdownOutput();
		socketChannel.close();
	}

}

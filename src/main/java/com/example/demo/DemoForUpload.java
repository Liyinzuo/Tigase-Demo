package com.example.demo;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.*;
import org.jivesoftware.smackx.si.packet.StreamInitiation;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.IOException;

/**
 * @author: lyz
 * @date: 2021/9/14 15:35
 */
public class DemoForUpload {

    private static final Logger logger = LoggerFactory.getLogger(DemoForUpload.class);
    private static final String DomainName = "aiorsoft.cn";
    private static final Integer port = 5222;

    public static void main(String[] args) throws Exception{

        XMPPTCPConnection xmppConn;
        xmppConn = new XMPPTCPConnection(getConnection()); // Create the connection

        xmppConn.connect();
        xmppConn.login();

        //获取文件传输对象
        FileTransferManager instanceFor = FileTransferManager.getInstanceFor(xmppConn);
        instanceFor.addFileTransferListener(new FileTransferListener() {
            @Override
            public void fileTransferRequest(FileTransferRequest fileTransferRequest) {
                System.err.println("进入监听");
                IncomingFileTransfer transfer = fileTransferRequest.accept();
                try {
                    String description = fileTransferRequest.getDescription();
                    //在目录fileDir目录下新建一个名字为request.getFileName()的文件
                    File file = new File("D:\\" ,fileTransferRequest.getFileName());
                    //开始接收文件(将传输过来的文件内容输出到file中)
                    transfer.receiveFile(file);
                    //此处执行文件传输监听
                } catch (SmackException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        OutgoingFileTransfer transfer = instanceFor.createOutgoingFileTransfer(JidCreate.entityFullFrom("ten2@aiorsoft.cn/DESKTOP-DQ2NL1Q"));
        //发送文件
//        File file = new File("D:\\40.png");
//        transfer.sendFile(file, "String description");

        while (true);
    }


    public static XMPPTCPConnectionConfiguration getConnection() throws XmppStringprepException {
        //构建连接参数
        final XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();

        //domain
        config.setXmppDomain(DomainName);
        //host地址/domain
        config.setHost(DomainName);
        //端口 默认5222
        config.setPort(port);
        //校验规则
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        //用户名 密码
        config.setUsernameAndPassword("ten3", "123456");
        //禁用主机名验证
        config.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        //来源 ten1@aiorsoft.cn/SMACK JID显示
        Resourcepart mResourcepart = Resourcepart.fromOrThrowUnchecked("SMACK");
        config.setResource(mResourcepart);
        return config.build();
    }
}

package com.example.demo;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @author: lyz
 * @date: 2021/9/14 18:04
 */
public class DemoForSub {

    private static final Logger logger = LoggerFactory.getLogger(DemoForSub.class);
    private static final String MUC_DomainName = "aiorsoft.cn";
    //    private static final String DomainName = "aiorsoft.cn";
    private static final Integer port = 5222;

    public static void main(String[] args) throws Exception{
        XMPPTCPConnection xmppConn;
        xmppConn = new XMPPTCPConnection(getConnection()); // Create the connection

        xmppConn.connect();
        xmppConn.login();

        PubSubManager pubSubManager = PubSubManager.getInstance(xmppConn);
        Node node = pubSubManager.getNode("ten3-is-nodeName");
        node.addItemEventListener(new ItemEventListener() {
            @Override
            public void handlePublishedItems(ItemPublishEvent itemPublishEvent) {
                for (Object o : itemPublishEvent.getItems()) {
                    PayloadItem item = (PayloadItem) o;
                    System.err.println("--payload=" + item.getPayload().toString());
                }
            }
        });
        node.subscribe(xmppConn.getUser().toString());
        while (true);
    }

    public static XMPPTCPConnectionConfiguration getConnection() throws XmppStringprepException {
        //构建连接参数
        final XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();

        //domain
        config.setXmppDomain(MUC_DomainName);
        //host地址/domain
        config.setHost("106.54.170.229");
        //端口 默认5222
        config.setPort(port);
        //校验规则
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        //用户名 密码
        config.setUsernameAndPassword("ten2", "123456");
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

package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.NickNameIQ;
import com.example.demo.util.Vacard4IQ;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.filter.StanzaIdFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.iqregister.packet.Registration;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author: lyz
 * @date: 2021/8/28 13:37
 */
public class DemoForChat {

    private static final Logger logger = LoggerFactory.getLogger(DemoForChat.class);
    private static final String DomainName = "aiorsoft.cn";
    private static final Integer port = 5222;

    public static void main(String[] args) throws Exception{
        XMPPTCPConnection xmppConn;
        xmppConn = new XMPPTCPConnection(getConnection()); // Create the connection
        xmppConn.addConnectionListener(new MyConnectListener());

        //Connect
        xmppConn.connect();
        //判断是否连接
        if (xmppConn.isConnected()) {
            System.err.println("conn success...");
            //登录操作
            xmppConn.login("admin", "laosha123");


            /**
             * 修改密码
             */
            AccountManager manager=AccountManager.getInstance(xmppConn);
//            manager.sensitiveOperationOverInsecureConnection(true);
//            manager.changePassword("123456");
            /**
             * 用户注销
             */
//            manager.deleteAccount();


            //查询用户
            /**
             * search.aiorsoft.cn服务不存在, 需要引入组件后才能使用
             */
//            UserSearchManager userSearchManager = new UserSearchManager(xmppConn);
//            Form searchForm = userSearchManager.getSearchForm(JidCreate.domainBareFrom("search.aiorsoft.cn"));
//            Form answerForm = searchForm.createAnswerForm();
//            answerForm.setAnswer("Username", true);
//            answerForm.setAnswer("search", "ten3");
//            ReportedData data = userSearchManager.getSearchResults(answerForm, JidCreate.domainBareFrom("search."+DomainName));
//            List<ReportedData.Row> rows = data.getRows();


            //修改在线状态
            /**
             * available
             * unavailable
             */
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Going fishing");
            xmppConn.sendStanza(presence);

            //增加(请求)监听?
//            xmppConn.addSyncStanzaListener(new StanzaListener() {
//                @Override
//                public void processStanza(Stanza stanza) throws SmackException.NotConnectedException, InterruptedException, SmackException.NotLoggedInException {
//
//                }
//            }, new StanzaFilter() {
//                @Override
//                public boolean accept(Stanza stanza) {
//                    return false;
//                }
//            });

            //添加好友
            Roster roster = Roster.getInstanceFor(xmppConn);

//            try {
//                Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
//
//                if (!roster.contains(JidCreate.entityBareFrom("ten2"+ "@aiorsoft.cn/Smack"))) {
//                    roster.createEntry(JidCreate.entityBareFrom("ten2" + "@aiorsoft.cn/Smack"), "ten2", null);
//                } else {
//                    logger.error("Contacto ya existente en la libreta de direcciones");
//                }
//
//            } catch (SmackException.NotLoggedInException e) {
//                e.printStackTrace();
//            } catch (SmackException.NoResponseException e) {
//                e.printStackTrace();
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            } catch (XMPPException.XMPPErrorException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //需要验证 并监听presence Roster.SubscriptionMode.reject_all拒绝所有 Roster.SubscriptionMode.accept_all 同意所有
//            roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
            //监听好友状态
            roster.addRosterListener(new MyRosterListener());


            /**
             * 修改好友备注
             * 通过xmpp协议修改
             */
            /**
             * <iq xmlns="jabber:client" id="5CWTg-97" type="set">
             *    query:childElementName  jabber:iq:roster:xmlsNamespace
             *   <query xmlns="jabber:iq:roster">
             *     <item jid="ten2@aiorsoft.cn" name="ten22222333333" subscription="both"/>
             *   </query>
             * </iq>
             */
//            NickNameIQ nickNameIQ = new NickNameIQ("query","jabber:iq:roster","ten2@"+DomainName,"ten22222");
//            nickNameIQ.setType(IQ.Type.set);
//            try {
//                xmppConn.sendStanza(nickNameIQ);
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            }

            //根据xmppConn通道获取监听聊天模块
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConn);
            //新建本地的监听
            chatManager.addIncomingListener(new IncomingChatMessageListener() {
                public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                    // 监听
                    System.err.println("Received message: " + message.getBody());
                    System.err.println("Received from:" + from.toString());
                }
            });

            //聊天
            //建立与某用户的聊天通道
            Chat chat = chatManager.chatWith(JidCreate.entityBareFrom("ten2@aiorsoft.cn"));
            System.err.println("Chat with :" + chat.getXmppAddressOfChatPartner().toString());
            //内部封装好了协议 参数就是xml中的body
            chat.send("json");

        }

        /**
         * 获取好友单个或多个详细个人信息
         */
        Roster roster2 = Roster.getInstanceFor(xmppConn);
        Collection<RosterEntry> entries = roster2.getEntries();
        System.err.println("Roster Size: >>>>" + entries.size());
        for (RosterEntry entry : entries) {
//              用户好友关系
//              none('⊥') 无关系, to('←') 等待验证, from('→') 等待我验证, both('↔') 两者好友, remove('⚡') 已删除;
            System.err.println("用户好友关系 " + entry + " " + entry.getType().toString());
            System.err.println(entry.getName());
        }

        VCardManager vCardManager = VCardManager.getInstanceFor(xmppConn);
        VCard vCard;
        vCard = vCardManager.loadVCard();
        System.err.println(JSONObject.toJSONString(vCard));

        vCard = vCardManager.loadVCard(JidCreate.entityBareFrom("ten2@aiorsoft.cn"));

//        System.err.println(JSONObject.toJSONString(vCard));
//        boolean isSupported = vCardManager.isSupported(JidCreate.entityBareFrom("ten2@"+DomainName));
//        if (isSupported) {
//            // return true
//            vCard = vCardManager.loadVCard(JidCreate.entityBareFrom("ten2@"+DomainName));
//            System.err.println(JSONObject.toJSONString(vCard));
//        }

        //修改用户信息
//        vCard.setNickName("Liyinzuo");
//        vCard.setPhoneHome("mobile","888888888");
//        vCardManager.saveVCard(vCard);

        while (true);
        //关闭tigase连接
//        xmppConn.disconnect();
    }

    private static void reg(XMPPTCPConnection conn, String username, String password)  {
        try {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("username", username);
            attributes.put("password", password);
            Registration reg = new Registration(attributes);
            reg.setType(IQ.Type.set);
            reg.setTo(conn.getXMPPServiceDomain());
            Stanza nextResultOrThrow = conn.createStanzaCollectorAndSend(new StanzaIdFilter(reg.getStanzaId()),reg).nextResultOrThrow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyRosterListener implements RosterListener {

        @Override
        public void entriesAdded(Collection<Jid> collection) {
            //新增用户成功用户的jid
            for (Jid jid : collection) {
                System.err.println("entriesAdded >>>" + jid.toString());
            }
        }

        @Override
        public void entriesUpdated(Collection<Jid> collection) {
            //修改用户成功用户的jid
            for (Jid jid:collection) {
                System.err.println("entriesUpdated >>>" + jid.toString());
            }
        }

        @Override
        public void entriesDeleted(Collection<Jid> collection) {
            //删除用户成功用户的jid
            System.err.println("entriesDeleted >>>");
        }

        //即时用户修改消息
        @Override
        public void presenceChanged(Presence p) {
            if (p.getType()==Presence.Type.subscribe) {
                System.err.println("收到"+ p.getFrom().toString() +"下线消息");
            } else if(p.getType()==Presence.Type.available){
                System.err.println("收到"+ p.getFrom().toString() +"上线消息");
            } else if(p.getType()==Presence.Type.unavailable){
                System.err.println("收到"+ p.getFrom().toString() +"下线消息");
            }
            System.out.println("Presence changed: " + p.getFrom() + " " + p);
        }
    }

    public static XMPPTCPConnectionConfiguration getConnection() throws Exception{
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
        Resourcepart mResourcepart = Resourcepart.fromOrThrowUnchecked("SMACK2");
        config.setResource(mResourcepart);
        return config.build();
    }

    public static class MyConnectListener implements ConnectionListener {

        @Override
        public void connected(XMPPConnection xmppConnection) {
            System.err.println("connected");
        }

        @Override
        public void authenticated(XMPPConnection xmppConnection, boolean b) {
            System.err.println("authenticated");
        }

        @Override
        public void connectionClosed() {
            System.err.println("connectionClosed");
        }

        @Override
        public void connectionClosedOnError(Exception e) {
            System.err.println("connectionClosedOnError");
        }
    }

}

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
        //??????????????????
        if (xmppConn.isConnected()) {
            System.err.println("conn success...");
            //????????????
            xmppConn.login("admin", "laosha123");


            /**
             * ????????????
             */
            AccountManager manager=AccountManager.getInstance(xmppConn);
//            manager.sensitiveOperationOverInsecureConnection(true);
//            manager.changePassword("123456");
            /**
             * ????????????
             */
//            manager.deleteAccount();


            //????????????
            /**
             * search.aiorsoft.cn???????????????, ?????????????????????????????????
             */
//            UserSearchManager userSearchManager = new UserSearchManager(xmppConn);
//            Form searchForm = userSearchManager.getSearchForm(JidCreate.domainBareFrom("search.aiorsoft.cn"));
//            Form answerForm = searchForm.createAnswerForm();
//            answerForm.setAnswer("Username", true);
//            answerForm.setAnswer("search", "ten3");
//            ReportedData data = userSearchManager.getSearchResults(answerForm, JidCreate.domainBareFrom("search."+DomainName));
//            List<ReportedData.Row> rows = data.getRows();


            //??????????????????
            /**
             * available
             * unavailable
             */
            Presence presence = new Presence(Presence.Type.available);
            presence.setStatus("Going fishing");
            xmppConn.sendStanza(presence);

            //??????(??????)???????
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

            //????????????
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
            //???????????? ?????????presence Roster.SubscriptionMode.reject_all???????????? Roster.SubscriptionMode.accept_all ????????????
//            roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
            //??????????????????
            roster.addRosterListener(new MyRosterListener());


            /**
             * ??????????????????
             * ??????xmpp????????????
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

            //??????xmppConn??????????????????????????????
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConn);
            //?????????????????????
            chatManager.addIncomingListener(new IncomingChatMessageListener() {
                public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                    // ??????
                    System.err.println("Received message: " + message.getBody());
                    System.err.println("Received from:" + from.toString());
                }
            });

            //??????
            //?????????????????????????????????
            Chat chat = chatManager.chatWith(JidCreate.entityBareFrom("ten2@aiorsoft.cn"));
            System.err.println("Chat with :" + chat.getXmppAddressOfChatPartner().toString());
            //???????????????????????? ????????????xml??????body
            chat.send("json");

        }

        /**
         * ?????????????????????????????????????????????
         */
        Roster roster2 = Roster.getInstanceFor(xmppConn);
        Collection<RosterEntry> entries = roster2.getEntries();
        System.err.println("Roster Size: >>>>" + entries.size());
        for (RosterEntry entry : entries) {
//              ??????????????????
//              none('???') ?????????, to('???') ????????????, from('???') ???????????????, both('???') ????????????, remove('???') ?????????;
            System.err.println("?????????????????? " + entry + " " + entry.getType().toString());
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

        //??????????????????
//        vCard.setNickName("Liyinzuo");
//        vCard.setPhoneHome("mobile","888888888");
//        vCardManager.saveVCard(vCard);

        while (true);
        //??????tigase??????
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
            //???????????????????????????jid
            for (Jid jid : collection) {
                System.err.println("entriesAdded >>>" + jid.toString());
            }
        }

        @Override
        public void entriesUpdated(Collection<Jid> collection) {
            //???????????????????????????jid
            for (Jid jid:collection) {
                System.err.println("entriesUpdated >>>" + jid.toString());
            }
        }

        @Override
        public void entriesDeleted(Collection<Jid> collection) {
            //???????????????????????????jid
            System.err.println("entriesDeleted >>>");
        }

        //????????????????????????
        @Override
        public void presenceChanged(Presence p) {
            if (p.getType()==Presence.Type.subscribe) {
                System.err.println("??????"+ p.getFrom().toString() +"????????????");
            } else if(p.getType()==Presence.Type.available){
                System.err.println("??????"+ p.getFrom().toString() +"????????????");
            } else if(p.getType()==Presence.Type.unavailable){
                System.err.println("??????"+ p.getFrom().toString() +"????????????");
            }
            System.out.println("Presence changed: " + p.getFrom() + " " + p);
        }
    }

    public static XMPPTCPConnectionConfiguration getConnection() throws Exception{
        //??????????????????
        final XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();


        //domain
        config.setXmppDomain(DomainName);
        //host??????/domain
        config.setHost(DomainName);
        //?????? ??????5222
        config.setPort(port);
        //????????????
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.ifpossible);
        //????????? ??????
        config.setUsernameAndPassword("ten3", "123456");
        //?????????????????????
        config.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        //?????? ten1@aiorsoft.cn/SMACK JID??????
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

package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lyz
 * @date: 2021/9/14 14:03
 */
public class DemoForMuc {
    private static final Logger logger = LoggerFactory.getLogger(DemoForMuc.class);
    private static final String MUC_DomainName = "aiorsoft.cn";
//    private static final String DomainName = "aiorsoft.cn";
    private static final Integer port = 5222;

    public static void main(String[] args) throws Exception {
        XMPPTCPConnection xmppConn;
        xmppConn = new XMPPTCPConnection(getConnection()); // Create the connection


        //Connect
        xmppConn.connect();
        if (xmppConn.isConnected()) {

            System.err.println("connect success...");
            xmppConn.login();

            //创建永久50人的群组
//            MultiUserChat chatRoom = MucUtil.createChatRoom("lyzgroup2", "xiaoli", "123456", xmppConn);
            MucUtil.join("lyzgroup2@muc.aiorsoft.cn", "admin", "", xmppConn);
            MucUtil.join("lyzgroup@muc.aiorsoft.cn", "admin", "", xmppConn);
            //查询所有群组
            List<HostedRoom> hostedRoom = MucUtil.getHostedRoom(xmppConn);
            for (HostedRoom r : hostedRoom) {
                System.err.println(r.getJid().toString() + "   " + r.getName().toString());
                //群组持久化的问题 如果断线第二次未重连的话 将退出群组
//                MucUtil.join(r.getJid().toString(), "ten333", "", xmppConn);
//                MucUtil.join(r.getJid().toString(), "test222", "", xmppConn2);
            }

            MultiUserChat multiUserChat = MultiUserChatManager.getInstanceFor(xmppConn).getMultiUserChat(JidCreate.entityBareFrom("lyzgroup@muc.aiorsoft.cn"));
            multiUserChat.addMessageListener(new MessageListener() {
                @Override
                public void processMessage(final Message message) {
                    //当消息返回为空的时候，表示用户正在聊天窗口编辑信息并未发出消息
                    if (!StringUtils.isBlank(message.getBody()))
                    System.err.println("admin: -=-lyzgroup2@muc.aiorsoft.cn" + "接收消息： " + message.getBody());
                }
            });

            while (true);
        }

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
        config.setUsernameAndPassword("admin", "laosha123");
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


    public static class MucUtil {

        /**
         * 获取服务器上的所有群组
         */
        private static List<HostedRoom> getHostedRoom(XMPPTCPConnection connection) {
            MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
            try {
                //serviceNames->conference.106.14.20.176
                Map<EntityBareJid, HostedRoom> roomsHostedBy = manager.getRoomsHostedBy(JidCreate.domainBareFrom("muc." + MUC_DomainName));
                List<HostedRoom> result = new ArrayList<>();
                for (Map.Entry<EntityBareJid, HostedRoom> entry : roomsHostedBy.entrySet()) {
                    EntityBareJid key = entry.getKey();
                    HostedRoom value = entry.getValue();
                    result.add(value);
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

            //解散群组
//            MultiUserChatManager muChatManager=MultiUserChatManager.getInstanceFor(conn);
//            roomJid= roomJid + getMucChatServiceName(conn);
//            MultiUserChat muc = muChatManager.getMultiUserChat(JidCreate.entityBareFrom(roomJid));
//            muc.destroy("解散群组", JidCreate.entityBareFrom(conn.getUser()));
        }

        /**
         * 加入一个群聊聊天室
         *
         * @param jid 聊天室ip 格式为>>群组名称@conference.ip
         * @param nickName 用户在聊天室中的昵称
         * @param password 聊天室密码 没有密码则传""
         * @return
         */
        public static MultiUserChat join(String jid, String nickName, String password, XMPPTCPConnection connection) {
            try {
                // 使用XMPPConnection创建一个MultiUserChat窗口
                MultiUserChat muc = MultiUserChatManager.getInstanceFor(connection).getMultiUserChat(JidCreate.entityBareFrom(jid));
                // 聊天室服务将会决定要接受的历史记录数量
//                DiscussionHistory history = new DiscussionHistory();
//                history.setMaxChars(0);
                // 用户加入聊天室
                muc.join(Resourcepart.from(nickName), password);
                return muc;
            } catch (XMPPException | SmackException | XmppStringprepException | InterruptedException e) {
                e.printStackTrace();
                if ("XMPPError: not-authorized - auth".equals(e.getMessage())) {
                    //需要密码加入
                }
                return null;
            }
        }

        /**
         * 创建群聊聊天室
         *
         * @param roomName 聊天室名字
         * @param nickName 创建者在聊天室中的昵称
         * @param password 聊天室密码
         * @return
         */
        public static MultiUserChat createChatRoom(String roomName, String nickName, String password, XMPPTCPConnection connection) {
            MultiUserChat muc;
            try {
                // 创建一个MultiUserChat
                MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
                // 创建聊天室
                muc = manager.getMultiUserChat(JidCreate.entityBareFrom(roomName +"@muc.aiorsoft.cn"));
                muc.create(Resourcepart.from(roomName));
                //获取表单配置
                Form form = muc.getConfigurationForm();
                Form answerForm = form.createAnswerForm();

                for(FormField field : form.getFields()){
                    if(!FormField.Type.hidden.name().equals(field.getType()) && field.getVariable() != null) {
                        answerForm.setDefaultAnswer(field.getVariable());
                    }
                }

                //muc#
                //房间名称
//                answerForm.setAnswer(FormField.FORM_TYPE, "http://jabber.org/protocol/muc#roomconfig");
                //设置房间名称
                answerForm.setAnswer("muc#roomconfig_roomname", roomName);
                //设置房间描述
                answerForm.setAnswer("muc#roomconfig_roomdesc", "desc miaoshu");
                //是否允许修改主题
                answerForm.setAnswer("muc#roomconfig_changesubject", true);

                //设置房间最大用户数
                List<String> maxusers = new ArrayList<String>();
                maxusers.add("50");
                answerForm.setAnswer("muc#roomconfig_maxusers", maxusers);


                List<String> cast_values = new ArrayList<String>();
                cast_values.add("moderator");
                cast_values.add("participant");
                cast_values.add("visitor");
//                answerForm.setAnswer("muc#roomconfig_presencebroadcast", cast_values);
                //设置为公共房间
                answerForm.setAnswer("muc#roomconfig_publicroom", true);
                //设置为永久房间
                answerForm.setAnswer("muc#roomconfig_persistentroom", true);
                //允许修改昵称
//                answerForm.setAnswer("x-muc#roomconfig_canchangenick", true);
                //允许用户登录注册房间
//                answerForm.setAnswer("x-muc#roomconfig_registration", true);


                muc.sendConfigurationForm(answerForm);
                muc.join(Resourcepart.from(nickName));
            } catch (XMPPException | SmackException | XmppStringprepException | InterruptedException e) {
                e.printStackTrace();
                return null;
            }
            return muc;
        }
    }
}

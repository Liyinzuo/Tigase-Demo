package com.example.demo;

import com.example.demo.util.NickNameIQ;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Nonza;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.id.StanzaIdUtil;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.muc.MucConfigFormManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.util.List;

/**
 * @author: lyz
 * @date: 2021/9/15 11:49
 */
public class DemoForAdmin {

    private static final Logger logger = LoggerFactory.getLogger(DemoForAdmin.class);
    private static final String DomainName = "aiorsoft.cn";
    private static final Integer port = 5222;

    public static void main(String[] args) throws Exception{
        XMPPTCPConnection xmppConn;
        xmppConn = new XMPPTCPConnection(getConnection()); // Create the connection
        xmppConn.connect();
        xmppConn.login();

        /**
         * <iq xmlns="jabber:client" to="ten2@aiorsoft.cn" id="5CWTg-104" type="get">
         *   <vCard xmlns="vcard-temp"/>
         * </iq>
         */
        /**
         * AdHocCommandData data = new AdHocCommandData();
         *         data.addNote(new AdHocCommandNote(AdHocCommandNote.Type.info, ""));
         */


            NickNameIQ nickNameIQ = new NickNameIQ("query","jabber:iq:roster","ten2@"+DomainName,"ten22222");
            nickNameIQ.setType(IQ.Type.set);
            try {
                xmppConn.sendStanza(nickNameIQ);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
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
        config.setUsernameAndPassword("admin", "123456");
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

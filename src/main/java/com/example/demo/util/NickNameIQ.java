package com.example.demo.util;

import org.jivesoftware.smack.packet.IQ;

/**
 * @author: lyz
 * @date: 2021/9/13 17:44
 *
 * <iq xmlns="jabber:client" id="OKGE7-97" type="set">
 *   <query xmlns="jabber:iq:roster">
 *     <item jid="ten2@aiorsoft.cn" name="ten22222" subscription="both"/>
 *   </query>
 * </iq>
 */
public class NickNameIQ extends IQ {

    private String jid;
    private String nickName;

    //query  jabber:iq:roster
    public NickNameIQ(String childElementName, String childElementNamespace, String jid,String nickName) {
        super(childElementName, childElementNamespace);
        this.jid = jid;
        this.nickName = nickName;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder iqChildElementXmlStringBuilder) {
        iqChildElementXmlStringBuilder.rightAngleBracket();
        iqChildElementXmlStringBuilder.append("<item jid=\'"+jid+"\' name=\'"+nickName+"\'>"+"</item>");
        return iqChildElementXmlStringBuilder;
    }
}

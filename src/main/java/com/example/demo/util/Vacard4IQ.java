package com.example.demo.util;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.commands.packet.AdHocCommandData;

/**
 * @author: lyz
 * @date: 2021/9/15 13:31
 * <iq from='bard@shakespeare.lit/globe'
 *     id='get-active-users-num-1'
 *     to='shakespeare.lit'
 *     type='set'
 *     xml:lang='en'>
 *   <command xmlns='http://jabber.org/protocol/commands'
 *            action='execute'
 *            node='http://jabber.org/protocol/admin#get-active-users-num'/>
 * </iq>
 */
public class Vacard4IQ extends AdHocCommandData {


}

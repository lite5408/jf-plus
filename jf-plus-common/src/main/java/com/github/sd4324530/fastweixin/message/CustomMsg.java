package com.github.sd4324530.fastweixin.message;

import com.github.sd4324530.fastweixin.message.util.MessageBuilder;

public class CustomMsg extends BaseMsg {

    public CustomMsg() {
    }

    @Override
    public String toXml() {
        MessageBuilder mb = new MessageBuilder(super.toXml());
        mb.addData("MsgType", RespType.KF);
        mb.surroundWith("xml");
        return mb.toString();
    }

}

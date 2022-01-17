package simplechat.ejb;

import simplechat.web.Message;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class MessageManager implements MessageManagerLocal{
    
    private final List messages = Collections.synchronizedList(new LinkedList());

    @Override
    public void sendMessage(Message msg) {
        messages.add(msg);
        msg.setDateSent(new Date());
    }

    @Override
    public Message getFirstAfter(Date after) {
        if(messages.isEmpty()){
            return null;
        }
        if(after == null){
            return (Message) messages.get(0);
        }
        for (Iterator it = messages.iterator(); it.hasNext();) {
            Message m = (Message) it.next();
            if(m.getDateSent().after(after)){
                return m;
            }
        }
        return null;
    }
    
}

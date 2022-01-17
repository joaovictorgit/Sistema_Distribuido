
package simplechat.web;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import simplechat.ejb.MessageManagerLocal;

@ManagedBean
@ViewScoped
public class MessageBean implements Serializable{
    
    @EJB
    MessageManagerLocal messageManagerLocal;
    
    private final List messages;
    private Date lastUpdate;
    private Message message;
    
    public MessageBean(){
        messages = Collections.synchronizedList(new LinkedList());
        lastUpdate = new Date();
        message = new Message();
    }
    public Date getLastUpdate() {
        return lastUpdate;
    }
 
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
 
    public Message getMessage() {
        return message;
    }
 
    public void setMessage(Message message) {
        this.message = message;
    }
 
    public void sendMessage(ActionEvent evt) {
        messageManagerLocal.sendMessage(message);
    }
 
    public void firstUnreadMessage(ActionEvent evt) {
       RequestContext ctx = RequestContext.getCurrentInstance();
 
       Message msg = messageManagerLocal.getFirstAfter(lastUpdate);
 
       ctx.addCallbackParam("ok", msg!=null);
       if(msg==null)
           return;
 
       lastUpdate = msg.getDateSent();
 
       ctx.addCallbackParam("user", msg.getUser());
       ctx.addCallbackParam("dateSent", msg.getDateSent().toString());
       ctx.addCallbackParam("text", msg.getMessage());
 
    }
}


package simplechat.ejb;
    

import java.util.Date;
import simplechat.web.Message;


public interface MessageManagerLocal {
    void sendMessage(Message msg);
    Message getFirstAfter(Date after);
}

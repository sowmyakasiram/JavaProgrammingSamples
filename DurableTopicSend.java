import java.io.*;
import java.util.*;
import javax.transaction.*;
import javax.naming.*;
import javax.jms.*;
import javax.rmi.*;

public class DurableTopicSend
{
    public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

    // Defines the JMS connection factory JNDI name.
    public final static String CONN_FACTORY="com.mentor.beacon.cf";

    // Defines the Topic JNDI name.
    public final static String TOPIC="com.mentor.jms.test.topic";

    protected TopicConnectionFactory dutconFactory;
    protected TopicConnection dutcon;
    protected TopicSession dutsession;
    protected TopicPublisher dutpublisher;
    protected Topic dutopic;
    protected TextMessage msg;

    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java DurableTopicSend WebLogicURL");
            return;
        }
        InitialContext ic = getInitialContext(args[0]);
        DurableTopicSend duts = new DurableTopicSend();
        duts.init(ic, TOPIC);
        readAndSendMsg(duts);
        duts.close();
    }

    public void init(Context ctx, String topicName)throws NamingException, JMSException
    {
        dutconFactory = (TopicConnectionFactory)PortableRemoteObject.narrow(ctx.lookup(CONN_FACTORY),TopicConnectionFactory.class);
        dutcon = dutconFactory.createTopicConnection();
        dutsession = dutcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        dutopic = (Topic) PortableRemoteObject.narrow(ctx.lookup(topicName), Topic.class);
        dutpublisher = dutsession.createPublisher(dutopic);
        msg = dutsession.createTextMessage();
        dutcon.start();
    }

    protected static InitialContext getInitialContext(String url)throws NamingException
    {
        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_PRINCIPAL, "system");
        env.put(Context.SECURITY_CREDENTIALS, "mgcweb12");
        env.put("weblogic.jndi.createIntermediateContexts", "true");
        return new InitialContext(env);
    }

    public void sendmsg(String message) throws JMSException
    {
        msg.setText(message);
        dutpublisher.publish(msg);
    }

    protected static void readAndSendMsg(DurableTopicSend duts)throws IOException, JMSException
    {
        BufferedReader msgStream = new BufferedReader (new InputStreamReader(System.in));
        String line=null;
        do
        {
            System.out.print("Enter your message (\"quit\" to quit): n");
            line = msgStream.readLine();
            if (line != null && line.trim().length() != 0)
            {
                duts.sendmsg(line);
                System.out.println("Sent JMS Message: "+line+"n");
            }
        }
        while (line != null && ! line.equalsIgnoreCase("quit"));
    }

    public void close() throws JMSException
    {
        dutpublisher.close();
        dutsession.close();
        dutcon.close();
    }
}
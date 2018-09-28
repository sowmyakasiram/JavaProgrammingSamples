import java.io.*;
import java.util.*;
import javax.transaction.*;
import javax.naming.*;
import javax.jms.*;
import javax.rmi.*;

public class DurableTopicReceive implements MessageListener
{
    public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";

    // Defines the JMS connection factory JNDI name.
    public final static String CONN_FACTORY="com.mentor.beacon.cf";

    // Defines the Topic JNDI name.
    public final static String TOPIC="com.mentor.jms.test.topic";

    private TopicConnectionFactory dutconFactory;
    private TopicConnection dutcon;
    private TopicSession dutsession;
    private TopicSubscriber dutsubscriber;
    private Topic dutopic;
    private boolean quit = false;

    public void onMessage(Message msg)
    {
        try
        {
            String msgText;
            if (msg instanceof TextMessage)
            {
                msgText = ((TextMessage)msg).getText();
            }
            else
            {
                msgText = msg.toString();
            }

            System.out.println("Received JMS Message: "+ msgText );

            if (msgText.equalsIgnoreCase("quit"))
            {
                synchronized(this)
                {
                    quit = true;
                    this.notifyAll();
                }
            }
        }
        catch (JMSException jmse)
        {
            System.err.println("an exception has occurred: "+jmse.getMessage());
        }
    }

	public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java DurableTopicReceive WebLogicURL");
            return;
        }
        InitialContext ic = getInitialContext(args[0]);
        DurableTopicReceive tr = new DurableTopicReceive();
        tr.init(ic, TOPIC);

        System.out.println("JMS is now ready to receive messages (to quit, send a \"quit\" message).");

        synchronized(tr)
        {
            while (!tr.quit)
            {
                try
                {
                    tr.wait();
                }
                catch (InterruptedException ie)
                {}
            }
        }
        tr.close();
    }

    private static InitialContext getInitialContext(String url)throws NamingException
    {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_PRINCIPAL, "system");
        env.put(Context.SECURITY_CREDENTIALS, "mgcweb12");
        env.put("weblogic.jndi.createIntermediateContexts", "true");
        return new InitialContext(env);
    }

    public void close() throws JMSException
    {
        dutsubscriber.close();
        dutsession.close();
        dutcon.close();
    }

    /**
    *
    * Below are the code which makes it a Durable Subscriber by giving the Client ID and Subscription Name
    *
    */
    public void init(Context ctx, String topicName)throws NamingException, JMSException
    {
        dutconFactory = (TopicConnectionFactory)PortableRemoteObject.narrow(ctx.lookup(CONN_FACTORY),TopicConnectionFactory.class);
        dutcon = dutconFactory.createTopicConnection();

        // ############## Below the Clinet ID is been given which is "guorong2" #####################
        dutcon.setClientID("guorong2");
        dutsession = dutcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        dutopic = (Topic)PortableRemoteObject.narrow(ctx.lookup(topicName),Topic.class);

        // ############## Below the Subscription Name is been given which is "Beacon" #####################
        dutsubscriber = dutsession.createDurableSubscriber(dutopic, "Beacon");
        dutsubscriber.setMessageListener(this);
        dutcon.start();
    }
}
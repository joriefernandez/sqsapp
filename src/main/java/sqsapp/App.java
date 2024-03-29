/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package sqsapp;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.List;

public class App
{
    private static final String QUEUE_A = "https://sqs.us-east-1.amazonaws.com/923037608857/QueueA";
    private static final String QUEUE_B = "https://sqs.us-east-1.amazonaws.com/923037608857/QueueB";
    private static final String QUEUE_C = "https://sqs.us-east-1.amazonaws.com/923037608857/QueueC";

    public static void main(String[] args)
    {
        sendReceive("Hello World for A", QUEUE_A);
        sendReceive("Hello World for B", QUEUE_B);
        sendReceive("Hello World for C", QUEUE_C);
    }


    public static void sendReceive(String msg, String queue){
        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();


        String queueUrl = queue;

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(msg)
                .withDelaySeconds(5);

        sqs.sendMessage(send_msg_request);



        // receive messages from the queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        while(messages.size() > 0) {

            // delete messages from the queue
            for (Message m : messages) {
                System.out.println(m.getBody());
                sqs.deleteMessage(queueUrl, m.getReceiptHandle());
            }

            messages = sqs.receiveMessage(queueUrl).getMessages();
        }

    }


}
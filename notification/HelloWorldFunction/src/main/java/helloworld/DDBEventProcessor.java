package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import datamodel.Board;
import datamodel.Course;
import service.BoardService;
import service.CourseService;


public class DDBEventProcessor implements
        RequestHandler<DynamodbEvent, String> {
    BoardService boardService;
    CourseService courseService;

    public DDBEventProcessor() {
        boardService=new BoardService();
        courseService=new CourseService();
    }

    @Override
    public String handleRequest(DynamodbEvent ddbEvent, Context context) {
        String message = null;
        String topicArn=null;
        for (DynamodbEvent.DynamodbStreamRecord record: ddbEvent.getRecords()){

            String boardId=record.getDynamodb().getNewImage().get("boardId").getS();
            topicArn=getTopic(boardId);
            message=record.getDynamodb().getNewImage().get("announcementText").getS();
        }
        AmazonSNS sns = AmazonSNSClientBuilder.defaultClient();
        sns.publish(topicArn, message);
        return "Successfully processed " + ddbEvent.getRecords().size() + " records.";
    }

    public String getTopic(String boardId){
        Board board=boardService.getBoard(boardId);
        String courseId=board.getCourseId();
        Course course=courseService.getCourse(courseId);
        return course.getNotificationTopic();


    }
}

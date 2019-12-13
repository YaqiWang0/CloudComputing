package service;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import datamodel.Course;
import datamodel.DynamoDbConnector;

import java.util.List;


public class CourseService {
    static DynamoDbConnector dynamoDb;
    DynamoDBMapper mapper;

    public CourseService() {
        dynamoDb = new DynamoDbConnector();
        dynamoDb.init();
        mapper = new DynamoDBMapper(dynamoDb.getClient());
    }

    public List<Course> getAllCourses() {
        List<Course> list = mapper.scan(Course.class, new DynamoDBScanExpression());
        return list ;
    }

    public Course addCourse(Course cour) {
        mapper.save(cour);
        return cour;
    }

    public Course getCourse(String courId) {

        //Simple HashKey Load

        Course cour2 = mapper.load(Course.class,courId);
        System.out.println("Item retrieved:");
        System.out.println(cour2.toString());

        return cour2;
    }

    public Course deleteCourse(String courId) {
        Course deletedCourDetails = mapper.load(Course.class,courId);
        mapper.delete(deletedCourDetails);
        return deletedCourDetails;
    }

    public Course updateCourseInformation(String courId, Course cour) {
        Course updatedCourDetails=mapper.load(Course.class,courId);

        courId =updatedCourDetails.getId();
        cour.setId(courId);
        // Publishing New Values
        mapper.save(cour);

        return cour;
    }



}
